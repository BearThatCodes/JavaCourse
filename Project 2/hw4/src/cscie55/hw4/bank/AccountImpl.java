/**
 * Created by Isaac on 4/6/2015.
 */
package cscie55.hw4.bank;

public class AccountImpl implements Account{
    private int id;
    private long balance;

    public AccountImpl(int id, long balance) {
        this.id = id;
        this.balance = balance;
    }

    /**
     * Returns the accounts integer ID
     * @return id the integer id of this account
     */
    @Override
    public int id() {
        return id;
    }

    /**
     * Returns this account's balance
     * @return balance the long balance of this Account
     */
    @Override
    public long balance() {
        return balance;
    }

    /**
     * Adds the specified amount to this Account's balance
     * @param amount the long amount to be added to the balance
     */
    @Override
    public void deposit(long amount) {
        balance += amount;
    }

    /**
     * If there are sufficient funds in the account, removes the specified amount from the Account, otherwise throws an InsufficientFundsException error
     * @param amount the long amount to be removed from the Account
     * @throws InsufficientFundsException
     */
    @Override
    public void withdraw(long amount) throws InsufficientFundsException {
        if(amount > balance){
            throw new InsufficientFundsException(this,amount);
        }
    }
}
