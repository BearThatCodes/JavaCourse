package cscie55.hw6.bank;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

/**
 * Created by Isaac on 5/11/2015.
 */
public class BankServer {
    private final static int PORT_NUM = 9090;

    public static void main(String[] args) {
        BankImpl bank = new BankImpl();
        boolean stop = false;
        ServerSocket listener = null;

        System.out.println("Created new BankImpl.");

        try {
            listener = new ServerSocket(PORT_NUM);
            System.out.println("Server now listening on port " + PORT_NUM);
        } catch (IOException e) {
            System.out.println("Could not create server socket on port " + PORT_NUM);
            return;
        }

        while (!stop) {
            try {
                Socket clientSocket = listener.accept();

                System.out.println("Connection established with client at " + clientSocket.getInetAddress() + " on port " + clientSocket.getPort());

                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

                //out.println("Connected to server on port " + PORT_NUM);

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
