import java.util.HashMap;
import java.util.Map;

public class Bank {
    private final Map<String, Account> accounts = new HashMap<>();
    private int nextNumber = 1001;

    public Account openSavings(String name, String pin, double deposit) throws InvalidAmountException {
        if (deposit < 500) {
            throw new InvalidAmountException("Savings account needs a minimum opening deposit of Rs.500.");
        }
        Account acc = new SavingsAccount(String.valueOf(nextNumber++), name, pin, deposit);
        accounts.put(acc.getAccountNumber(), acc);
        return acc;
    }

    public Account openCurrent(String name, String pin, double deposit) throws InvalidAmountException {
        if (deposit < 0) {
            throw new InvalidAmountException("Opening deposit cannot be negative.");
        }
        Account acc = new CurrentAccount(String.valueOf(nextNumber++), name, pin, deposit);
        accounts.put(acc.getAccountNumber(), acc);
        return acc;
    }

    public Account login(String accountNumber, String pin) throws InvalidPinException {
        Account acc = accounts.get(accountNumber);
        if (acc == null || !acc.checkPin(pin)) {
            throw new InvalidPinException("Invalid account number or PIN.");
        }
        return acc;
    }

    public void transfer(Account from, String toAccountNumber, double amount)
            throws InvalidAmountException, InsufficientFundsException {
        Account to = accounts.get(toAccountNumber);
        if (to == null) {
            throw new InvalidAmountException("Receiver account " + toAccountNumber + " does not exist.");
        }
        if (to == from) {
            throw new InvalidAmountException("You cannot transfer to the same account.");
        }
        from.withdraw(amount);
        to.deposit(amount);
        from.addTransaction("Transferred Rs." + amount + " to " + toAccountNumber);
        to.addTransaction("Received Rs." + amount + " from " + from.getAccountNumber());
    }
}