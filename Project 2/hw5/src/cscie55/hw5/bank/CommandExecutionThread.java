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
                System.out.println("Running thread " + getId());
                while (commandQueue.size() == 0) {
                    try {
                        System.out.println("No commands for thread " + getId() + " so we'll wait");
                        commandQueue.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                commandToRun = commandQueue.remove();
                //System.out.println("Getting command " + commandToRun + " for thread " + getId());
                //System.out.println("Thread is " + this.getState() + " and command is " + commandQueue.peek());

                if (!commandToRun.isStop() && executeCommandInsideMonitor) {
                    System.out.println("Executing " + commandToRun.getClass() + " command inside sync for thread " + getId());
                    try {
                        commandToRun.execute(bank);
                    } catch (InsufficientFundsException e) {
                        //System.out.println(e);
                    }
                }

                if (commandToRun.isStop()) {
                    System.out.println("Command is stop, try to return for thread " + getId());
                    return;
                }
            }

            //System.out.println("We got there");

            if (!executeCommandInsideMonitor) {
                System.out.println("Executing " + commandToRun.getClass() + " command outside sync for thread " + getId());
                try {
                    commandToRun.execute(bank);
                } catch (InsufficientFundsException e) {
                    //System.out.println(e);
                }
            }
        }
    }
}

