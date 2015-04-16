package cscie55.hw5.bank;

import cscie55.hw5.bank.command.Command;

import java.util.Queue;

/**
 * Created by Isaac on 4/15/2015.
 */
public class CommandExecutionThread extends Thread {
    private Bank bank;
    private Queue<Command> commandQueue;
    private boolean executeCommandInsideMonitor;

    public CommandExecutionThread(Bank bank,Queue<Command> commandQueue,boolean executeCommandInsideMonitor){
        this.bank = bank;
        this.commandQueue = commandQueue;
        this.executeCommandInsideMonitor = executeCommandInsideMonitor;
    }

    /**
     *
     */
    @Override
    public void run() {
        /*
        This is to get around the fact that the compiler doesn't think we'll always reach
        the statement where we pull a command off the Queue and initialize commandToRun
         */
        Command commandToRun = null;

        if(commandQueue.size() > 0) {
            synchronized (commandQueue) {
                while (!commandQueue.peek().isStop()) {
                    while (commandQueue.size() <= 0) {
                        try {
                            this.wait();
                        } catch (InterruptedException e) {
                            System.out.println("Thread was interrupted.");
                        }
                    }

                    commandToRun = commandQueue.remove();

                    if (executeCommandInsideMonitor) {
                        try {
                            commandToRun.execute(bank);
                        } catch (InsufficientFundsException e) {
                            System.out.println(e);
                        }
                    }
                }

                if (commandQueue.peek().isStop()) {
                    commandQueue.remove();
                    return;
                }
            }

            if (!executeCommandInsideMonitor && commandToRun != null) {
                try {
                    commandToRun.execute(bank);
                } catch (InsufficientFundsException e) {
                    System.out.println(e);
                }
            }
        }
    }
}
