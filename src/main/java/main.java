import services.TcpServer;
import services.TcpServerThread;

public class main {
    public static void main(String[] args) {
        TcpServer tcpServer = new TcpServer();
        tcpServer.service();
    }
}
