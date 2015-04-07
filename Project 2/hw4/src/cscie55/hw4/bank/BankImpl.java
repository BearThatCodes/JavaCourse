package cscie55.hw4.bank;

/**
 * Created by Isaac on 4/6/2015.
 */
public class BankImpl implements Bank{
    @Override
    public void addAccount(Account account) throws DuplicateAccountException {

    }

    @Override
    public void transferWithoutLocking(int fromId, int toId, long amount) throws InsufficientFundsException {

    }

    @Override
    public void transferLockingBank(int fromId, int toId, long amount) throws InsufficientFundsException {

    }

    @Override
    public void transferLockingAccounts(int fromId, int toId, long amount) throws InsufficientFundsException {

    }

    @Override
    public long totalBalances() {
        return 0;
    }

    @Override
    public int numberOfAccounts() {
        return 0;
    }
}
