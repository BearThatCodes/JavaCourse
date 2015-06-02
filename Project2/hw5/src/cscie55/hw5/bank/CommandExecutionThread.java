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

    public CommandExecutionThread(Bank bank, Queue<Command> commandQueue, boolean executeCommandInsideMonitor) {
        this.bank = bank;
        this.commandQueue = commandQueue;
        this.executeCommandInsideMonitor = executeCommandInsideMonitor;
    }

    /**
     *
     */
    @Override
    public void run() {
        Command commandToRun;

        while (true) {
            synchronized (commandQueue) {
                while (commandQueue.size() == 0) {
                    try {
                        commandQueue.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                commandToRun = commandQueue.remove();

                if (!commandToRun.isStop() && executeCommandInsideMonitor) {
                    try {
                        commandToRun.execute(bank);
                    } catch (InsufficientFundsException e) {
                        System.out.println(e.getMessage());
                    }
                }

                if (commandToRun.isStop()) {
                    return;
                }
            }

            if (!executeCommandInsideMonitor && !commandToRun.isStop()) {
                try {
                    commandToRun.execute(bank);
                } catch (InsufficientFundsException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
    }
}

