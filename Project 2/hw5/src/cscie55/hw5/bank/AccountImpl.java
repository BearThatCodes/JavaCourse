/**
 * Implementation of Account class.
 */
package cscie55.hw5.bank;

import java.security.InvalidParameterException;

public class AccountImpl implements Account{
    private int id;
    private long balance;

    public AccountImpl(int id){
        this.id = id;
        balance = 0;
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
        if(amount <= 0){
            throw new InvalidParameterException("Must deposit a value greater than 0.");
        }
        else {
            balance += amount;
        }
    }

    /**
     * If there are sufficient funds in the account, removes the specified amount from the Account, otherwise throws an InsufficientFundsException error
     * @param amount the long amount to be removed from the Account
     * @throws InsufficientFundsException
     */
    @Override
    public synchronized void withdraw(long amount) throws InsufficientFundsException {
        if(amount > balance){
            throw new InsufficientFundsException(this,amount);
        }
        else if(amount <= 0){
            throw new InvalidParameterException("Must withdraw a value greater than 0.");
        }
        else{
            balance -= amount;
        }
    }
}
