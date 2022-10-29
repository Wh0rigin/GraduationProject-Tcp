package services;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

public class TcpServerThread implements Runnable{
    private Socket client;
    private TcpProcessListener processListener;

    public TcpServerThread(Socket client,TcpProcessListener processListener) {
        this.client = client;
        this.processListener = processListener;
    }


    @Override
    public void run() {
        while(true){
            processListener.run(this.client);
            if(this.client.isClosed()){
                break;
            }
        }
    }
}