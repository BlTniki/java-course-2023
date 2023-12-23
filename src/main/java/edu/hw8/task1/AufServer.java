package edu.hw8.task1;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;
import org.jetbrains.annotations.Range;

@SuppressWarnings("checkstyle:LineLength")
public class AufServer  {
    private final static int ACCEPT_TIMEOUT_MS = 100;
    private final static Map<String, String> AUF_LINES = new ConcurrentHashMap<>();
    private final static String UNKNOWN_KEY_MESSAGE = "Если в дверь не постучаться, ее никогда не откроют";

    static {
        AUF_LINES.put("личности", "Не переходи на личности там, где их нет");
        AUF_LINES.put("оскорбления", "Если твои противники перешли на личные оскорбления, будь уверена — твоя победа не за горами");
        AUF_LINES.put("глупый", "А я тебе говорил, что ты глупый? Так вот, я забираю свои слова обратно... Ты просто бог идиотизма.");
        AUF_LINES.put("интеллект", "Чем ниже интеллект, тем громче оскорбления");
    }

    private final ServerSocket serverSocket;
    private final ExecutorService executor;
    @SuppressWarnings("FieldCanBeLocal")
    private final Thread mainThread;
    private final AtomicBoolean isAlive;

    public AufServer(
            @Range(from = 1, to = 65535) int port,
            @Range(from = 1, to = Integer.MAX_VALUE) int poolSize,
            @Range(from = 1, to = Integer.MAX_VALUE) int backlog
            ) throws IOException {
        this.serverSocket = new ServerSocket(port, backlog);
        this.serverSocket.setSoTimeout(ACCEPT_TIMEOUT_MS);

        this.executor = Executors.newFixedThreadPool(poolSize);

        this.isAlive = new AtomicBoolean(true);

        this.mainThread = new Thread(() -> {
            while (isAlive.get()) {
                try {
                    final Socket client = serverSocket.accept();
                    executor.submit(() -> handleConnection(client));
                } catch (SocketTimeoutException | SocketException e) {
                    // pass
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        this.mainThread.start();
    }

    private static void handleConnection(final Socket client) {
        try {
            try (client;
                 BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
                 BufferedWriter out = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()))
            ) {
                final StringBuilder builder = new StringBuilder();

                builder.append(in.readLine());
                while (in.ready()) {
                    builder.append(in.readLine());
                }

                final String key = builder.toString();

                final String answer = AUF_LINES.getOrDefault(key, UNKNOWN_KEY_MESSAGE);
                out.write(answer);
                out.newLine();
                out.flush();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void kill() {
        isAlive.set(false);
        try {
            executor.close();
            serverSocket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
