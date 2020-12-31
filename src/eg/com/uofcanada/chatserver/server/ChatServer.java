package eg.com.uofcanada.chatserver.server;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ChatServer {
    final static int    PORT      = 8081;

    public static void main(String[] args) {
        try(ServerSocket server = new ServerSocket( PORT)){

            while(true)
            {
                Socket socket = server.accept();
                ClientThread clientThread = new ClientThread(socket);
                Thread thread = new Thread(clientThread);
                thread.start();
            }


        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }
}
