/**
 * Exception thrown when a penalty+amount_to_withdrawn exceeds the current balance. For giving specific message.
 * This exception includes details about the account type, penalty amount, current balance and the attempted transaction amount.
 * It provides methods to retrieve a user-friendly error message and detailed error information.
 */
public class PenaltyException extends Exception implements BankExceptionInterface {
    private final String accountType;
    private final double penaltyAmount;
    private final double currentBalance;
    private final double attemptedAmount;

    /**
     * Constructs a PenaltyException with the specified details.
     *
     * @param accountType      The type of account where the error occurred.
     * @param message          The error message.
     * @param penaltyAmount    The penalty applied to the transaction.
     * @param currentBalance   The current balance of the account.
     * @param attemptedAmount  The amount attempted to be withdrawn.
     */
    public PenaltyException(String accountType, String message, double penaltyAmount, double currentBalance, double attemptedAmount) {
        super(message);
        this.accountType = accountType;
        this.penaltyAmount = penaltyAmount;
        this.currentBalance = currentBalance;
        this.attemptedAmount = attemptedAmount;
    }

    /**
     * Returns a detailed error message summarizing the exception.
     * Includes the account type, error message, attempted amount, penalty amount, and current balance.
     *
     * @return A formatted error message string.
     */
    @Override
    public String getMessage() {
        return String.format("%s: %s Amount: %.1f Penalty: %.1f Balance: %.1f",
                accountType, super.getMessage(), attemptedAmount, penaltyAmount, currentBalance);
    }

    /**
     * Provides a user-friendly summary of the error.
     * @return A concise error message for end-users.
     */
    @Override
    public String getErrorMessage() {
        return String.format("Error in %s account: Attempted transaction amount %.1f incurred a penalty of %.1f.",
                accountType, attemptedAmount, penaltyAmount);
    }

    /**
     * Provides detailed information about the exception, including all key attributes by using the getMessage() method.
     *
     * @return A comprehensive error message.
     */
    @Override
    public String getErrorDetails() {
        return getMessage();
    }
}
