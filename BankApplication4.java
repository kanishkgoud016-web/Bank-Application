import java.util.Scanner;

// Abstract Base Class
abstract class Account {
    protected String accNo;
    protected String holderName;
    protected double balance;

    public Account(String accNo, String holderName, double balance) {
        this.accNo = accNo;
        this.holderName = holderName;
        this.balance = balance;
    }

    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            System.out.println("--> Deposited: $" + amount + " | New Balance: $" + balance);
        } else {
            System.out.println("--> Invalid deposit amount.");
        }
    }

    // Abstract method to be overwritten differently by subclasses
    public abstract void withdraw(double amount);

    // Method to check which account type it is
    public void checkAccountType() {
        System.out.println("--> Account Type: " + this.getClass().getSimpleName());
    }

    public void displayBalance() {
        System.out.println("\n----------------------------------");
        System.out.println("Acc No      : " + accNo);
        System.out.println("Holder Name : " + holderName);
        System.out.println("Type        : " + this.getClass().getSimpleName());
        System.out.println("Balance     : $" + balance);
        System.out.println("----------------------------------");
    }
}

// Savings Account Subclass ($500 Minimum Balance)
class SavingsAccount extends Account {
    private static final double MIN_BALANCE = 500.0;

    public SavingsAccount(String accNo, String holderName, double balance) {
        super(accNo, holderName, balance);
    }

    @Override
    public void withdraw(double amount) {
        if (balance - amount >= MIN_BALANCE) {
            balance -= amount;
            System.out.println("--> Savings Withdraw: $" + amount + " | Remaining Balance: $" + balance);
        } else {
            System.out.println("--> Savings Withdraw Failed: Must maintain minimum balance of $" + MIN_BALANCE);
        }
    }
}

// Current Account Subclass ($1000 Overdraft Limit)
class CurrentAccount extends Account {
    private static final double OVERDRAFT_LIMIT = 1000.0;

    public CurrentAccount(String accNo, String holderName, double balance) {
        super(accNo, holderName, balance);
    }

    @Override
    public void withdraw(double amount) {
        if (balance + OVERDRAFT_LIMIT >= amount) {
            balance -= amount;
            System.out.println("--> Current Withdraw: $" + amount + " | Remaining Balance: $" + balance);
        } else {
            System.out.println("--> Current Withdraw Failed: Exceeds overdraft limit of $" + OVERDRAFT_LIMIT);
        }
    }
}

// Main Driver Class
public class BankApplication4 {

    // Helper method to gather details and create a new Account object
    public static Account createAccount(Scanner sc) {
        System.out.println("\n=== CREATE BANK ACCOUNT ===");
        System.out.println("1. Savings Account");
        System.out.println("2. Current Account");
        System.out.print("Choose option (1 or 2): ");
        int typeChoice = sc.nextInt();
        sc.nextLine(); // Consume newline

        System.out.print("Enter Account Number: ");
        String accNo = sc.nextLine();

        System.out.print("Enter Holder Name: ");
        String holderName = sc.nextLine();

        System.out.print("Enter Initial Balance: ");
        double initialBalance = sc.nextDouble();

        Account newAcc;
        if (typeChoice == 1) {
            newAcc = new SavingsAccount(accNo, holderName, initialBalance);
            System.out.println("--> Savings Account Created Successfully!");
        } else {
            newAcc = new CurrentAccount(accNo, holderName, initialBalance);
            System.out.println("--> Current Account Created Successfully!");
        }
        return newAcc;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Create the initial account
        Account account = createAccount(sc);

        int choice;
        do {
            System.out.println("\n========== MENU ==========");
            System.out.println("1. Deposit");
            System.out.println("2. Withdraw");
            System.out.println("3. Display Balance");
            System.out.println("4. Check Account Type");
            System.out.println("5. Create Another Account");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");
            choice = sc.nextInt();

            switch (choice) {
                case 1:
                    System.out.print("Enter amount to deposit: ");
                    double depAmt = sc.nextDouble();
                    account.deposit(depAmt);
                    break;

                case 2:
                    System.out.print("Enter amount to withdraw: ");
                    double wdAmt = sc.nextDouble();
                    account.withdraw(wdAmt);
                    break;

                case 3:
                    account.displayBalance();
                    break;

                case 4:
                    account.checkAccountType();
                    break;

                case 5:
                    // Resets and creates a brand-new account
                    account = createAccount(sc);
                    break;

                case 6:
                    System.out.println("Exiting Bank Application. Goodbye!");
                    break;

                default:
                    System.out.println("Invalid option! Please enter a number from 1 to 6.");
            }
        } while (choice != 6);

        sc.close();
    }
}