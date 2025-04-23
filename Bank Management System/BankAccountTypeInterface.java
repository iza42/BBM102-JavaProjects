/**
 * The BankAccountTypeInterface represents a generic bank account type.
 * It provides methods for common operations such as deposit, withdraw, and risk evaluation or displaying the details about the account.
 * Also it allows setting and getting account details like balance and interest calculation.
 */
public interface BankAccountTypeInterface {

        /**
         * Deposits a specified amount into the receiver's account.
         * @param receiver The account where the amount will be deposited.
         * @param amount   The amount that deposited.
         */
        void deposit(BankAccountTypeInterface receiver, double amount) ;

        /**
         * Withdraws a specified amount from the receiver's account.
         * @param receiver The account from which the amount will be withdrawn.
         * @param amount   The amount that withdrawn.
         * @return true if the withdrawal is successful, otherwise return false.
         */
        boolean withdraw(BankAccountTypeInterface receiver,double amount) ;

        /**
         * Evaluates the risk level of the account based on some criterias.
         * The implementation take into account  factors such as account balance and transaction history etc.
         */
        void evaluateRisk();

        /**
         * Displays the details of the account.
         * This may include information like account ID, current balance, account type and other attributes.
         */
        void displayAccountDetails();

        /**
         * Checks if the account is considered as a high-value account.
         * @return true if the account meets the criterias for being high value account otherwise returns false.
         */
        boolean isHighValueAccount();

        /**
         * Retrieves the account ID for the account.
         * @return The account ID as a String.
         */
        String getAccountID();

        /**
         * Calculates the interest for the account based on the current balance and interest rate if the account have any.
         * @return The calculated interest amount as a double value.
         */
        double Interest_calculation();

        /**
         * Updates the current balance of the account.
         * @param currentBalance The new balance to set for the account.
         */
        void setCurrentBalance(double currentBalance);

        /**
         * Retrieves the current balance of the account.
         * @return The current balance as a double.
         */
        double getCurrentBalance();
    }




