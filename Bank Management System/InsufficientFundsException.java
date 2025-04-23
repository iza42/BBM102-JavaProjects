/**
 * Exception thrown when an account has insufficient funds for a transaction.
 * This exception includes details about the account type, current balance, attempted transaction amount,
 * and any overdraft or permissible limits.
 * It provides methods to retrieve a user-friendly error message and detailed error information.
 */
public class InsufficientFundsException extends Exception implements BankExceptionInterface{
    private final String accountType;
    private final double currentBalance;
    private final double attemptedAmount;
    private final double limit;

    /**
     * Constructs an InsufficientFundsException with the specified details.
     *
     * @param accountType      The type of account where the error occurred.
     * @param message          The error message.
     * @param currentBalance   The current balance of the account.
     * @param attemptedAmount  The amount attempted to be withdrawn or used.
     * @param limit            The overdraft or permissible limit for the account.
     */
    public InsufficientFundsException(String accountType, String message, double currentBalance, double attemptedAmount, double limit) {
        super(message);
        this.accountType = accountType;
        this.currentBalance = currentBalance;
        this.attemptedAmount = attemptedAmount;
        this.limit = limit;
    }

    /**
     * Returns a detailed error message summarizing the exception.
     * Includes the account type, error message, attempted amount, current balance and limit.
     * @return A formatted error message string.
     */
    @Override
    public String getMessage() {
        return String.format("%s: %s Amount: %.1f Balance: %.1f Limit: %.1f",
                accountType, super.getMessage(), attemptedAmount, currentBalance, limit);
    }


    /**
     * Provides a user-friendly summary of the error.
     * @return A concise error message for end-users.
     */
    @Override
    public String getErrorMessage() {
        return String.format("Error in %s account: Attempted transaction amount %.1f exceeds available balance or limit.",
                accountType, attemptedAmount);
    }

    /**
     * Provides detailed information about the exception, including all key attributes by using getMessage() method.
     * @return A comprehensive error message.
     */
    @Override
    public String getErrorDetails() {
        return getMessage();
    }
}
