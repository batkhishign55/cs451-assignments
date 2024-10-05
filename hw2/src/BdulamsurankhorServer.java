// Batkhishig Dulamsurankhor
// bdulamsurankhor@hawk.iit.edu
// A20543498

package src;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BdulamsurankhorServer {

    private ServerSocket server = null;
    private Properties prop = null;
    private List<String> words = null;

    public BdulamsurankhorServer() {
        try {
            prop = new Properties();
            FileInputStream fis = new FileInputStream("app.config");
            prop.load(fis);

            loadWords();

            server = new ServerSocket(Integer.parseInt(prop.getProperty("port")));

            ExecutorService executor = Executors.newFixedThreadPool(Integer.parseInt(prop.getProperty("thread")));

            System.out.println("Hangman server started!");
            while (true) {
                try {
                    Socket socket = server.accept();
                    System.out.println("[Server]: Client accepted");

                    DataInputStream in = new DataInputStream(socket.getInputStream());
                    DataOutputStream out = new DataOutputStream(socket.getOutputStream());

                    Runnable clientThread = new BdulamsurankhorHandler(socket, in, out, words);
                    executor.execute(clientThread);

                } catch (IOException i) {
                    System.out.println(i);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadWords() throws IOException {

        BufferedReader br = new BufferedReader(new FileReader("words.txt"));
        String line;
        words = new ArrayList<>();
        while ((line = br.readLine()) != null) {
            words.add(line.trim());
        }
        System.out.println("Loaded " + words.size() + " words into memory.");
        br.close();
    }

    public static void main(String args[]) {
        new BdulamsurankhorServer();
    }
}
