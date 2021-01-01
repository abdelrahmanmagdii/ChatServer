package eg.com.uofcanada.chatserver.client;

import javax.swing.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.util.Scanner;

public class ChatClient {
    final static String HOST_NAME = "localhost";
    final static int    PORT      = 8081;

    private String uid;
    private String name;

    public static void main(String[] args) {
        String uid = JOptionPane.showInputDialog("Please enter UID");
        if(uid == null) {
            JOptionPane.showMessageDialog(null, "UID cannot be empty");
        }
        ChatClient client = new ChatClient(uid);
    }

    public ChatClient(String uid)
    {
        this.uid = uid;
        if(checkForPassword()==false)
        {
            JOptionPane.showMessageDialog(null, "Wrong Password");
            return;
        }
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

    private boolean checkForPassword() {
        //https://dzone.com/articles/how-to-parse-json-data-from-a-rest-api-using-simpl
        try {
            String password = JOptionPane.showInputDialog("Please enter your password");
            if(password == null)
                return false;
            URL url = new URL("http://ec2-3-19-14-4.us-east-2.compute.amazonaws.com/user?uid="+uid+"&password="+password);
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();
            int responsecode = conn.getResponseCode();
            if(responsecode != 200)
                return false;
            BufferedReader data = new BufferedReader(new InputStreamReader(url.openStream()));
            String msg = data.readLine();
            System.out.println(msg);
            int start = msg.indexOf("name");
            start=start+7;
            int end = msg.indexOf("\"",start);
            name = msg.substring(start, end);
            JOptionPane.showMessageDialog(null, "Hello "+name );
            System.out.println("OKOK");


        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            return false;
        }

        return true;
    }
}
