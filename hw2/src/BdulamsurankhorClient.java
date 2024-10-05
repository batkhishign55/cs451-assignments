// Batkhishig Dulamsurankhor
// bdulamsurankhor@hawk.iit.edu
// A20543498

package src;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;

public class BdulamsurankhorClient {

    private Socket socket = null;
    private DataOutputStream out = null;
    private DataInputStream in = null;
    private Scanner scanner = null;
    List<Long> timeList = new ArrayList<>();
    private Properties prop = null;

    public BdulamsurankhorClient() {
        try {
            prop = new Properties();
            FileInputStream fis = new FileInputStream("app.config");
            prop.load(fis);
            socket = new Socket(prop.getProperty("ip"), Integer.parseInt(prop.getProperty("port")));
            System.out.println("Connected to the server!");

            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());
            scanner = new Scanner(System.in);

            while (true) {
                String resp = in.readUTF();
                System.out.println(resp);
                String guess = scanner.nextLine();

                out.writeUTF(guess);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnections();
        }
    }

    private void closeConnections() {
        if (scanner != null) {
            scanner.close();
        }
        try {
            if (in != null) {
                in.close();
            }
            if (out != null) {
                out.close();
            }
            if (socket != null) {
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Resources closed, exiting gracefully...");
    }

    public static void main(String args[]) {
        new BdulamsurankhorClient();
    }
}
