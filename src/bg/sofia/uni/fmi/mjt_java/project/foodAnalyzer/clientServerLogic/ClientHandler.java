package bg.sofia.uni.fmi.mjt.foodAnalyzer.clientServerLogic;

import bg.sofia.uni.fmi.mjt.foodAnalyzer.command.CommandCreator;
import bg.sofia.uni.fmi.mjt.foodAnalyzer.command.CommandExecutor;
import bg.sofia.uni.fmi.mjt.foodAnalyzer.constants.Const;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.URISyntaxException;

//reference: https://www.geeksforgeeks.org/multithreaded-servers-in-java/
public class ClientHandler implements Runnable {
    private final Socket clientSocket;
    private final CommandExecutor commandExecutor;
    private static final String OUTPUT_SUFFIX =
            System.lineSeparator() + Const.STOP_READER;

    // Constructor
    public ClientHandler(Socket socket, CommandExecutor commandExecutor) {
        this.clientSocket = socket;
        this.commandExecutor = commandExecutor;
    }

    public void run() {
        try (PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(
                     clientSocket.getInputStream()))) {

            String line;
            while ((line = in.readLine()) != null) {
                String result = commandExecutor.execute(CommandCreator.newCommand(line)) + OUTPUT_SUFFIX;
                out.println(result);
            }
        } catch (IOException e) {
            throw new RuntimeException("Unable to connect to the server during a query from a client.");
        }
    }
}

