package eg.com.uofcanada.chatserver.server;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientThread implements Runnable {
    Socket socket;
    String name;
    public ClientThread(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            name = in.readLine();
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            while (!socket.isClosed()) {
                if (in.ready()) {
                    System.out.println(name+" ==> "+in.readLine());
                }
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
}
