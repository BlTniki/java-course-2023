package edu.hw8.task1;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Range;

public class AufClient {
    private final String endpoint;
    private final int port;

    public AufClient(@NotNull String endpoint, @Range(from = 1, to = 65535) int port) {
        this.endpoint = endpoint;
        this.port = port;
    }

    public @NotNull String getAufLineByKey(@NotNull String key) {
        try {
            try (
                var socket = new Socket(endpoint, port);
                var in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                var out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()))
            ) {
                out.write(key);
                out.newLine();
                out.flush();

                final StringBuilder builder = new StringBuilder();

                builder.append(in.readLine());
                while (in.ready()) {
                    builder.append(in.readLine());
                }

                return builder.toString();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
