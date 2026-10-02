public class SavingsAccount extends Account {
    private static final double MIN_BALANCE = 500;
    private static final double INTEREST_RATE = 0.04;

    public SavingsAccount(String accountNumber, String holderName, String pin, double openingBalance) {
        super(accountNumber, holderName, pin, openingBalance);
    }

    @Override
    protected double getMinimumAllowedBalance() {
        return MIN_BALANCE;
    }

    @Override
    public String getAccountType() {
        return "Savings";
    }

    public void addYearlyInterest() {
        double interest = balance * INTEREST_RATE;
        balance += interest;
        addTransaction("Interest added Rs." + interest + " | Balance: Rs." + balance);
    }
}