import java.util.Scanner;

public class Main {
    private static final Scanner sc = new Scanner(System.in);
    private static final Bank bank = new Bank();

    public static void main(String[] args) {
        System.out.println("===== Welcome to Java Bank ATM =====");
        boolean running = true;
        while (running) {
            System.out.println("\n1. Open Savings Account");
            System.out.println("2. Open Current Account");
            System.out.println("3. Login");
            System.out.println("4. Exit");
            int choice = readInt("Choose: ");
            switch (choice) {
                case 1 -> openAccount(true);
                case 2 -> openAccount(false);
                case 3 -> loginFlow();
                case 4 -> {
                    System.out.println("Thank you for banking with us!");
                    running = false;
                }
                default -> System.out.println("Invalid choice.");
            }
        }
    }

    private static void openAccount(boolean savings) {
        try {
            System.out.print("Enter your name: ");
            String name = sc.nextLine().trim();
            System.out.print("Set a 4-digit PIN: ");
            String pin = sc.nextLine().trim();
            if (!pin.matches("\\d{4}")) {
                System.out.println("PIN must be exactly 4 digits.");
                return;
            }
            double deposit = readDouble("Opening deposit: ");
            Account acc = savings ? bank.openSavings(name, pin, deposit)
                    : bank.openCurrent(name, pin, deposit);
            System.out.println("Account created! Your account number is " + acc.getAccountNumber());
        } catch (InvalidAmountException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void loginFlow() {
        System.out.print("Account number: ");
        String accNo = sc.nextLine().trim();
        Account acc = null;
        for (int attempt = 1; attempt <= 3 && acc == null; attempt++) {
            System.out.print("PIN: ");
            String pin = sc.nextLine().trim();
            try {
                acc = bank.login(accNo, pin);
            } catch (InvalidPinException e) {
                System.out.println(e.getMessage() + " Attempts left: " + (3 - attempt));
            }
        }
        if (acc == null) {
            System.out.println("Too many failed attempts. Returning to main menu.");
            return;
        }
        System.out.println("Welcome, " + acc.getHolderName() + " (" + acc.getAccountType() + " account)");
        atmMenu(acc);
    }

    private static void atmMenu(Account acc) {
        boolean loggedIn = true;
        while (loggedIn) {
            System.out.println("\n1. Check Balance");
            System.out.println("2. Deposit");
            System.out.println("3. Withdraw");
            System.out.println("4. Transfer");
            System.out.println("5. Mini Statement");
            System.out.println("6. Logout");
            int choice = readInt("Choose: ");
            try {
                switch (choice) {
                    case 1 -> System.out.println("Balance: Rs." + acc.getBalance());
                    case 2 -> {
                        acc.deposit(readDouble("Amount to deposit: "));
                        System.out.println("Deposit successful. Balance: Rs." + acc.getBalance());
                    }
                    case 3 -> {
                        acc.withdraw(readDouble("Amount to withdraw: "));
                        System.out.println("Please collect your cash. Balance: Rs." + acc.getBalance());
                    }
                    case 4 -> {
                        System.out.print("Receiver account number: ");
                        String to = sc.nextLine().trim();
                        bank.transfer(acc, to, readDouble("Amount to transfer: "));
                        System.out.println("Transfer successful. Balance: Rs." + acc.getBalance());
                    }
                    case 5 -> acc.printMiniStatement();
                    case 6 -> {
                        System.out.println("Logged out.");
                        loggedIn = false;
                    }
                    default -> System.out.println("Invalid choice.");
                }
            } catch (InvalidAmountException | InsufficientFundsException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    private static int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid whole number.");
            }
        }
    }

    private static double readDouble(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Double.parseDouble(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid amount.");
            }
        }
    }
}