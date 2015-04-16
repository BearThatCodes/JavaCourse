package cscie55.hw5.bank;

import java.util.HashMap;

/**
 * Implementation of the Bank class.
 */
public class BankImpl implements Bank{
    HashMap<Integer,Account> accounts;

    /**
     * Creates a new Bank with default parameters
     */
    public BankImpl() {
        accounts = new HashMap<Integer,Account>();
    }

    /**
     * Adds the Account to the Bank's collection of Accounts. The Bank may only have one instance of any given Account.
     * @param account the Account to be added
     * @throws DuplicateAccountException
     */
    @Override
    public void addAccount(Account account) throws DuplicateAccountException {
        synchronized (account) {
            if (accounts.containsValue(account)) {
                throw new DuplicateAccountException(account.id());
            }
            else {
                accounts.put(account.id(), account);
            }
        }
    }

    /**
     * Withdraws the specified amount from the specified Account and deposits it to the specified Account (synchronized on individual Accounts)
     * @param fromId the integer value of the Account ID from which to withdraw()
     * @param toId the integer value of the Account ID to which to deposit()
     * @param amount the long amount to withdraw() from one Account and deposit() to the other
     * @throws InsufficientFundsException
     */
    @Override
    public void transfer(int fromId, int toId, long amount) throws InsufficientFundsException {
        synchronized (accounts.get(fromId)){
            accounts.get(fromId).withdraw(amount);
        }

        synchronized (accounts.get(toId)){
            accounts.get(toId).deposit(amount);
        }
    }

    /**
     * Deposit the given amount to the Account identified by accountId.
     *
     * @param accountId Identifies the Account to which funds will be deposited.
     * @param amount    The amount to be deposited.
     */
    @Override
    public void deposit(int accountId, long amount) {
        if(!accounts.containsKey(accountId)){
            throw new IllegalArgumentException("Account " + accountId + " does not exist in this Bank.");
        }
        else{
            synchronized (accounts.get(accountId)){
                accounts.get(accountId).deposit(amount);
            }
        }
    }

    /**
     * Returns the long value representing the total balance of all the Accounts in the Bank.
     * @return total the long value representing the total balance of all Accounts in this Bank
     */
    @Override
    public long totalBalances() {
        long total = 0;
        for(Account account : accounts.values()){
            total += account.balance();
        }
        return total;
    }

    /**
     * Returns the number of Accounts in this Bank.
     * @return count the integer number of Accounts in this Bank
     */
//    TODO See if I actually need this method.
    public int numberOfAccounts() {
        return accounts.size();
    }
}
