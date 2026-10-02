public class CurrentAccount extends Account {
    private static final double OVERDRAFT_LIMIT = 5000;

    public CurrentAccount(String accountNumber, String holderName, String pin, double openingBalance) {
        super(accountNumber, holderName, pin, openingBalance);
    }

    @Override
    protected double getMinimumAllowedBalance() {
        return -OVERDRAFT_LIMIT;
    }

    @Override
    public String getAccountType() {
        return "Current";
    }
}