/**
 * Exception thrown when an invalid amount is provided for a transaction.
 * This exception includes details about the account type and the invalid amount attempted to deposit or withdraw.
 * It provides methods to retrieve a user-friendly error message and detailed error information.
 */
public class InvalidAmountException extends Exception implements BankExceptionInterface {
    private final String accountType;
    private final double invalidAmount;

    /**
     * Constructs an InvalidAmountException with the specified details.
     *
     * @param accountType   The type of account where the error occurred.
     * @param message       The error message.
     * @param invalidAmount The invalid amount that caused the exception.
     */
    public InvalidAmountException(String accountType, String message, double invalidAmount) {
        super(message);
        this.accountType = accountType;
        this.invalidAmount = invalidAmount;
    }

    /**
     * Returns a detailed error message summarizing the exception.
     * Includes the account type, error message, and invalid amount.
     *
     * @return A formatted error message string.
     */
    @Override
    public String getMessage() {
        return String.format("%s: %s Amount: %.1f", accountType, super.getMessage(), invalidAmount);
    }

    /**
     * Provides a user-friendly summary of the error.
     * @return A concise error message for end-users.
     */
    @Override
    public String getErrorMessage() {
        return String.format("Error in %s account: Invalid transaction amount %.1f.", accountType, invalidAmount);
    }

    /**
     * Provides detailed information about the exception, including all key attributes by using the getMessage() method.
     * @return A comprehensive error message.
     */
    @Override
    public String getErrorDetails() {
        return getMessage();
    }
}
