package bg.sofia.uni.fmi.mjt.foodAnalyzer.clientServerLogic;

import bg.sofia.uni.fmi.mjt.foodAnalyzer.constants.Const;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;


//reference: https://www.geeksforgeeks.org/multithreaded-servers-in-java/
class Client {

    public static void main(String[] args) {
        try (Socket socket = new Socket(Const.HOST, Const.SERVER_PORT);
             PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader reader = new BufferedReader(new InputStreamReader(
                     socket.getInputStream()));
             Scanner scanner = new Scanner(System.in)) {

            while (true) {

                System.out.print("Enter message: ");
                String message = scanner.nextLine();

                if ("quit".equals(message)) {
                    break;
                }

                System.out.println("Sending message <" + message + "> to the server." +
                        System.lineSeparator() + "Waiting for response...");
                writer.println(message);

                String line;
                StringBuffer reply = new StringBuffer();
                while (!(line = reader.readLine()).equals(Const.STOP_READER)) {
                    reply.append(line + System.lineSeparator());
                }
                System.out.print(reply);
            }

        } catch (IOException e) {
            throw new RuntimeException("Unable to connect to the server. " +
                    "Try again later or contact administrator");
        }
    }
}
