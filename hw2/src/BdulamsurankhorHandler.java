// Batkhishig Dulamsurankhor
// bdulamsurankhor@hawk.iit.edu
// A20543498

package src;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class BdulamsurankhorHandler extends Thread {

    private DataInputStream in = null;
    private DataOutputStream out = null;
    private Socket socket;
    private int lives = 10;
    private boolean[] flags = new boolean[26];
    private List<String> words = null;
    private String word = null;
    private String guessedWord = null;

    public BdulamsurankhorHandler(Socket socket, DataInputStream in, DataOutputStream out, List<String> words) {
        this.socket = socket;
        this.in = in;
        this.out = out;
        this.words = words;
    }

    @Override
    public void run() {
        try {
            // as soon as a client connects, start a new game
            createNewGame();
            out.writeUTF(String.format("\n \"%s\" \n You have %d lives left.", guessedWord, lives));

            while (true) {
                String resp = in.readUTF();

                // if response's length is not 1, respond with error
                if (resp.length() != 1) {
                    out.writeUTF("Invalid input!");
                    continue;
                }

                char c = resp.charAt(0);

                // check if the response is letter
                if (!Character.isLetter(c)) {
                    out.writeUTF("Invalid character!");
                    continue;
                }

                // make the character lower case
                c = Character.toLowerCase(c);

                int index = c - 'a';

                // respond with error if the char is already chosen before
                if (flags[index]) {
                    out.writeUTF(String.format("You have already chosen letter %c.", c));
                    continue;
                }

                flags[index] = true;

                boolean found = checkCharacter(c);

                if (found) {
                    // if the client wins the game
                    if (word.equals(guessedWord)) {
                        System.out.println("Client won the game!");
                        out.writeUTF(
                                String.format(
                                        "Congratulations! The correct word was indeed \"%s\"\nWant to play again? y or n.",
                                        word));

                        resp = in.readUTF();
                        c = resp.charAt(0);

                        if (Character.toLowerCase(c) == 'y') {
                            createNewGame();
                        } else {
                            break;
                        }
                    }
                } else
                    lives -= 1;

                // when game ends
                if (lives == 0) {
                    System.out.println("Client lost the game!");
                    out.writeUTF(
                            String.format(
                                    "Sorry, you have used up all 10 of your lives. The correct word was \"%s\".\nDo you want to play again? y or n.",
                                    word));

                    resp = in.readUTF();
                    c = resp.charAt(0);

                    if (Character.toLowerCase(c) == 'y') {
                        createNewGame();
                    } else {
                        break;
                    }
                }

                out.writeUTF(String.format("\n \"%s\" \n You have %d lives left.", guessedWord, lives));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnections();
        }
    }

    private void closeConnections() {
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
        System.out.println("Resources closed, thread terminating gracefully...");
    }

    private void getRandomWord() {
        Random random = new Random();
        int randomIndex = random.nextInt(words.size());
        this.word = words.get(randomIndex);
    }

    private void createNewGame() {
        getRandomWord();

        // create a guess word
        char[] chars = new char[word.length()];
        Arrays.fill(chars, '-');
        guessedWord = new String(chars);

        lives = 10;
        flags = new boolean[26];
        System.out.println(String.format("Created a new game: %s", word));
    }

    private boolean checkCharacter(char c) {
        boolean found = false;
        for (int i = 0; i < word.length(); i++) {
            if (word.charAt(i) == c) {
                found = true;
                StringBuilder sb = new StringBuilder(guessedWord);
                sb.setCharAt(i, c);
                guessedWord = sb.toString();
            }
        }
        return found;
    }
}
