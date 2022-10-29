package services;

import java.net.Socket;

public interface TcpProcessListener {
    public void run(Socket client);
}
