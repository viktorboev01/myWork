package bg.sofia.uni.fmi.mjt.foodAnalyzer.clientServerLogic;

import bg.sofia.uni.fmi.mjt.foodAnalyzer.cache.FoodAnalyzerCache;
import bg.sofia.uni.fmi.mjt.foodAnalyzer.command.CommandExecutor;
import bg.sofia.uni.fmi.mjt.foodAnalyzer.constants.Const;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


//reference: https://www.geeksforgeeks.org/multithreaded-servers-in-java/
class Server {
    public static void main(String[] args) {
        CommandExecutor commandExecutor = new CommandExecutor(new FoodAnalyzerCache());
        ExecutorService executor = Executors.newFixedThreadPool(50);
        try (ServerSocket server = new ServerSocket(Const.SERVER_PORT)) {

            server.setReuseAddress(true);

            while (true) {

                Socket client = server.accept();

                System.out.println("New client connected"
                        + client.getInetAddress()
                        .getHostAddress());

                ClientHandler clientSock
                        = new ClientHandler(client, commandExecutor);

                executor.execute(clientSock);
            }
        } catch (IOException e) {
            throw new RuntimeException("The server has been disconnected. " +
                    "Try to start it again or contact administrator");
        } finally {
            executor.shutdown();
        }
    }
}

