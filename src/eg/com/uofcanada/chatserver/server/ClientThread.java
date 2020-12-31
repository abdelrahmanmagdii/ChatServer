package eg.com.uofcanada.chatserver.server;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientThread implements Runnable {
    private Socket socket;



    private String name;
    private ChatServer server;
    private PrintWriter writer;

    private String incomingMsg;

    public String getName() {
        return name;
    }

    public Socket getSocket() {
        return socket;
    }

    public PrintWriter getWriter() {
        return writer;
    }

    public ClientThread(ChatServer server, Socket socket) {
        this.socket = socket;
        this.server = server;
    }

    @Override
    public void run() {
        String msg;
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            name = in.readLine();
            writer = new PrintWriter(socket.getOutputStream(), true);
            while (!socket.isClosed()) {
                if(incomingMsg !=null)
                {
                    writer.println(incomingMsg);
                    incomingMsg = null;
                }
                if (in.ready()) {
                    msg = in.readLine();
                    System.out.println(name+" ==> "+msg);
                    server.broadcast(this, msg);
                }
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }

    public void receiveMsg(String s)
    {
        incomingMsg = s;
    }
}
