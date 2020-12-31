package eg.com.uofcanada.chatserver.client;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class ChatClient {
    final static String HOST_NAME = "localhost";
    final static int    PORT      = 8081;

    public static void main(String[] args) {
        try(Socket s = new Socket( HOST_NAME, PORT)){
            InputStream in = s.getInputStream();
            PrintWriter out = new PrintWriter( s.getOutputStream(), true);
            BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));
            while(!s.isClosed())
            {
                if(userInput.ready())
                {
                    out.println(userInput.readLine());
                }
            }


            s.close();

        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }
}
