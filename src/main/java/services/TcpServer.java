package services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;

public class TcpServer {
    private int port = 8600;
    private ExecutorService executorService;


    public TcpServer() {
    }

    public TcpServer(int port) {
        this.port = port;
    }

    public void service() {
        try {
                ServerSocket serverSocket = new ServerSocket(port);
                while(true){
                    Socket socket = serverSocket.accept();
                    System.out.println("Getting socket:" + socket);
                    TcpServerThread tcpServerThread = new TcpServerThread(socket, new TcpProcessListener() {
                        @Override
                        public void run(Socket client) {
                            doHandler(client);
                        }
                    });
                    new Thread(tcpServerThread).start();
                }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void doHandler(Socket client)  {

            BufferedReader br = null;
            StringBuffer res = new StringBuffer();
        try{
            br = new BufferedReader(new InputStreamReader(client.getInputStream()));
            System.out.println(br.readLine());
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (br!=null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            try {
                client.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}

