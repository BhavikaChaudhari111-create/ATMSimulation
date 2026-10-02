import java.util.ArrayList;
import java.util.List;

public abstract class Account {
    private final String accountNumber;
    private final String holderName;
    private final String pin;
    protected double balance;
    private final List<String> transactions = new ArrayList<>();

    public Account(String accountNumber, String holderName, String pin, double openingBalance) {
        this.accountNumber = accountNumber;
        this.holderName = holderName;
        this.pin = pin;
        this.balance = openingBalance;
        transactions.add("Account opened with Rs." + openingBalance);
    }

    protected abstract double getMinimumAllowedBalance();

    public abstract String getAccountType();

    public void deposit(double amount) throws InvalidAmountException {
        if (amount <= 0) {
            throw new InvalidAmountException("Deposit amount must be greater than zero.");
        }
        balance += amount;
        transactions.add("Deposited Rs." + amount + " | Balance: Rs." + balance);
    }

    public void withdraw(double amount) throws InvalidAmountException, InsufficientFundsException {
        if (amount <= 0) {
            throw new InvalidAmountException("Withdrawal amount must be greater than zero.");
        }
        if (balance - amount < getMinimumAllowedBalance()) {
            throw new InsufficientFundsException(
                    "Insufficient funds. Minimum allowed balance is Rs." + getMinimumAllowedBalance());
        }
        balance -= amount;
        transactions.add("Withdrew Rs." + amount + " | Balance: Rs." + balance);
    }

    public boolean checkPin(String enteredPin) {
        return pin.equals(enteredPin);
    }

    public void addTransaction(String text) {
        transactions.add(text);
    }

    public void printMiniStatement() {
        System.out.println("--- Mini Statement (last 5) ---");
        int start = Math.max(0, transactions.size() - 5);
        for (int i = start; i < transactions.size(); i++) {
            System.out.println(transactions.get(i));
        }
    }

    public String getAccountNumber() { return accountNumber; }
    public String getHolderName() { return holderName; }
    public double getBalance() { return balance; }
}