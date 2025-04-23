import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * BankingSystem class for simulating the banking system.
 * This program reads account details and transactions from files, processes the transactions and displays account details with risk evaluations.
 */
public class BankingSystem {
    /**
     * Entry point for the banking system simulation.
     * @param args Command-line arguments(accounts file and transaction file)
     */
    public static void main(String[] args) {
      // checking for how many command line argument have entered
        if (args.length < 2) {
            System.out.println("Usage: java Main <accounts_file> <transactions_file>");
            return;
        }

        String accountsFile = args[0];// accounts file taken for first command line argument
        String transactionsFile = args[1];// transactions file taken for second command line argument

        Map<String, BankAccountTypeInterface> accounts = new HashMap<>();

        // Load accounts from the accounts file
        try (BufferedReader br = new BufferedReader(new FileReader(accountsFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                String accountID = parts[0];
                String accountType = parts[1];
                double balance = Double.parseDouble(parts[2]);

                switch (accountType) {
                    case "Current":
                        double overdraftLimit = Double.parseDouble(parts[3]);
                        accounts.put(accountID, new CurrentAccount(accountID, balance, overdraftLimit));
                        break;
                    case "Saving":
                        double interestRate = Double.parseDouble(parts[3]);
                        double minBalance = Double.parseDouble(parts[4]);
                        accounts.put(accountID, new SavingAccount(accountID, balance, interestRate, minBalance));
                        break;
                    case "Deposit":
                        double depositInterestRate = Double.parseDouble(parts[3]);
                        int termInMonths = Integer.parseInt(parts[4]);
                        double penaltyRate = Double.parseDouble(parts[5]);
                        if (parts[6] == null || parts[6].isEmpty()) {
                            System.out.println("Missing start_date for account: " + accountID);
                        }
                        accounts.put(accountID, new FixedDepositAccount(accountID, balance, depositInterestRate, termInMonths, penaltyRate, parts[6]));
                        break;
                    default:
                        System.out.println("Unknown account type: " + accountType);
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading accounts file: " + e.getMessage());
        }

        // Process transactions from the transactions file
        try (BufferedReader br = new BufferedReader(new FileReader(transactionsFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                String senderID = parts[0];
                double amount = Double.parseDouble(parts[1]);
                String receiverID = parts[2];

                BankAccountTypeInterface sender = accounts.get(senderID);
                BankAccountTypeInterface receiver = accounts.get(receiverID);

                if (sender != null && receiver != null) {
                    boolean transactionSuccessful = sender.withdraw(receiver, amount);  // Sender withdraws the amount
                    if (transactionSuccessful) {
                        receiver.deposit(sender, amount);  // Receiver deposits the amount
                    }
                } else {
                    System.out.println("Invalid transaction: Sender or Receiver not found.");
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading transactions file: " + e.getMessage());
        }

        // Display account details with risk evaluations
        for (BankAccountTypeInterface account : accounts.values()) {
            account.displayAccountDetails();
        }
    }
}
