package cscie55.hw6.bank;

import cscie55.hw6.bank.command.Command;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.stream.Stream;

/**
 * Created by Isaac on 5/11/2015.
 */
public class CommandExecutionThread extends Thread {
    private BankImpl bank;
    private InputStream inputStream;
    private OutputStream outputStream;
    private BufferedReader in;
    private PrintWriter out;
    private Socket client;
    private static final int PORT_NUM = 1066;

    /**
     * Creates a new CommandExecutionThread
     * @param bank the bank with which this thread is associated
     * @param inputStream the stream via which this thread will get input
     * @param outputStream the stream via which this thread will communicate output
     */
    public CommandExecutionThread(BankImpl bank, InputStream inputStream, OutputStream outputStream) {
        this.bank = bank;
        this.inputStream = inputStream;
        this.outputStream = outputStream;
    }

    @Override
    public void run() {
        Command commandToRun;
        in = new BufferedReader(new InputStreamReader(inputStream));
        out = new PrintWriter(outputStream,true);

        while(true){
            try {
                String nextLine = in.readLine();
                if (nextLine != null) {
                    commandToRun = Command.parse(nextLine);
                    try {
                        synchronized (bank) {
                            String outputString;
                            outputString = commandToRun.execute(bank);
                            if (outputString != null) {
                                out.println(outputString);
                            }
                            else {
                                out.println("");
                            }
                        }
                    } catch (InsufficientFundsException e) {
                        out.println(e.getMessage());
                    } catch (DuplicateAccountException e) {
                        out.println(e.getMessage());
                    }
                }
            }catch(IOException e){
                break;
            }
        }
    }
}
