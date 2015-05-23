package cscie55.hw6.bank;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by Isaac on 5/04/2015.
 */
public class BankServer {
    private final static int PORT_NUM = 9090;

    /**
     * Runs the BankServer
     */
    public static void main(String[] args) {
        BankImpl bank = new BankImpl();
        ServerSocket listener;

        System.out.println("Created new BankImpl.");

        try {
            listener = new ServerSocket(PORT_NUM);
            System.out.println("Server now listening on port " + PORT_NUM);
        } catch (IOException e) {
            System.out.println("Could not create server socket on port " + PORT_NUM);
            return;
        }

        while (true) {
            try {
                Socket clientSocket = listener.accept();

                InputStream inputStream = clientSocket.getInputStream();
                OutputStream outputStream = clientSocket.getOutputStream();

                CommandExecutionThread commandExecutionThread = new CommandExecutionThread(bank, inputStream, outputStream);
                commandExecutionThread.start();

            } catch (IOException e) {
                System.out.println("Could not open port " + PORT_NUM);
            }
        }
    }
}
