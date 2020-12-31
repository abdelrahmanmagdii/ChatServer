package eg.com.uofcanada.chatserver.client;

import javax.swing.*;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class ChatClient {
    final static String HOST_NAME = "localhost";
    final static int    PORT      = 8081;

    public static void main(String[] args) {
        String name = JOptionPane.showInputDialog("Please enter your name");
        if(name == null)
            name="No Name";
        try(Socket s = new Socket( HOST_NAME, PORT)){
            BufferedReader serverInput = new BufferedReader(new InputStreamReader(s.getInputStream()));
            PrintWriter out = new PrintWriter( s.getOutputStream(), true);
            out.println(name);
            BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));
            while(!s.isClosed())
            {
                if(serverInput.ready())
                {
                    System.out.println(serverInput.readLine());
                }
                if(userInput.ready())
                {
                    out.println(userInput.readLine());
                }
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }
}
