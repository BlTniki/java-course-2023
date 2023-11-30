package edu.hw6.task6;

import java.io.IOException;
import java.net.BindException;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.util.HashMap;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@SuppressWarnings({"checkstyle:MagicNumber", "checkstyle:MultipleStringLiterals"})
public final class PortScannerUtils {
    private final static Logger LOGGER = LogManager.getLogger("portScanner");
    private final static int LAST_REGISTERED_PORT = 49151;

    private PortScannerUtils() {
    }

    @SuppressWarnings("checkstyle:UncommentedMain")
    public static void main(String[] args) {
        LOGGER.info("%-9s%-7s%s%n".formatted("Протокол", "Порт", "Сервис"));
        scanPorts();
    }

    static Map<Integer, String> portServiceMap;

    static {
        portServiceMap = new HashMap<>();
        portServiceMap.put(80, "HTTP");
        portServiceMap.put(21, "FTP");
        portServiceMap.put(25, "SMTP");
        portServiceMap.put(22, "SSH");
        portServiceMap.put(443, "HTTPS");
        portServiceMap.put(53, "DNS");
        portServiceMap.put(3306, "MySQL Database");
        portServiceMap.put(5432, "PostgreSQL Database");
        portServiceMap.put(3389, "Remote Desktop Protocol (RDP)");
        portServiceMap.put(27017, "MongoDB Database");
        portServiceMap.put(1521, "Oracle Database");
        portServiceMap.put(49152, "Windows RPC (Remote Procedure Call)");
        portServiceMap.put(5353, "mDNS (Multicast Domain Name System)");
        portServiceMap.put(5672, "AMQP (Advanced Message Queuing Protocol)");
        portServiceMap.put(5355, "LLMNR (Link-Local Multicast Name Resolution)");
        portServiceMap.put(49153, "Windows RPC (Remote Procedure Call)");
        portServiceMap.put(23, "Telnet");
        portServiceMap.put(110, "POP3");
        portServiceMap.put(143, "IMAP");
        portServiceMap.put(67, "DHCP");
        portServiceMap.put(68, "DHCP");
        portServiceMap.put(123, "NTP");
        portServiceMap.put(161, "SNMP");
        portServiceMap.put(162, "SNMP");
        portServiceMap.put(445, "SMB");
        portServiceMap.put(548, "AFP");
        portServiceMap.put(137, "NetBIOS");
        portServiceMap.put(138, "NetBIOS");
        portServiceMap.put(139, "NetBIOS");
        portServiceMap.put(8080, "HTTP Proxy");
        portServiceMap.put(1080, "SOCKS");
        portServiceMap.put(389, "LDAP");
        portServiceMap.put(636, "LDAP");
        portServiceMap.put(5722, "SMB2");
        portServiceMap.put(500, "IKE");
        portServiceMap.put(1701, "L2TP");
        portServiceMap.put(1723, "PPTP");
        portServiceMap.put(5060, "SIP");
        portServiceMap.put(5061, "SIP");
        portServiceMap.put(873, "Rsync");
        portServiceMap.put(2049, "NFS");
        portServiceMap.put(6379, "Redis");
    }

    private static void scanPorts() {
        for (int port = 0; port <= LAST_REGISTERED_PORT; port++) {
            try (ServerSocket serverSocket = new ServerSocket()) {
                serverSocket.setReuseAddress(false);
                serverSocket.bind(new InetSocketAddress(port), 100);
            } catch (BindException e) {
                LOGGER.info(getServiceName(port, "TCP"));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            try (DatagramSocket datagramSocket = new DatagramSocket(port)) {
                datagramSocket.setReuseAddress(false);
            } catch (BindException e) {
                LOGGER.info(getServiceName(port, "UDP"));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private static String getServiceName(int port, String protocol) {
        String serviceName = portServiceMap.getOrDefault(port, "UNKNOWN");
        return "%-9s%-7d%s%n".formatted(protocol, port, serviceName);
    }
}
