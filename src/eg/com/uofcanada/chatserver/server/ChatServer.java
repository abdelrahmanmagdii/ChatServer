package eg.com.uofcanada.chatserver.server;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class ChatServer {
    final static int    PORT      = 8081;
    private static ArrayList<ClientThread> list = new ArrayList<>();

    public static void main(String[] args) {
        ChatServer server = new ChatServer();
    }

    ChatServer()
    {
        try(ServerSocket server = new ServerSocket( PORT)){
            while(true)
            {
                Socket socket = server.accept();
                ClientThread clientThread = new ClientThread(this, socket);
                Thread thread = new Thread(clientThread);
                list.add(clientThread);
                thread.start();
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    public void broadcast(ClientThread from, String msg) {
        for(ClientThread client: list)
        {
            if(client != from)
            {
                client.getWriter().println(from.getName()+" ==> "+msg);
            }
                //
        }
    }
}
