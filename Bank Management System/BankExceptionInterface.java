/**
 * Interface for defining common behaviors of bank related exceptions.
 * This interface ensures that all custom exceptions in the banking system provide
 * standardized methods for retrieving error messages and detailed error information.
 */
public interface BankExceptionInterface {
    /**
     * Retrieves the error message associated with the exception.
     * This method provides a concise summary of the error.
     * @return A string containing the error message.
     */
    String getErrorMessage();

    /**
     * Provides detailed information about the error.
     * @return A string containing detailed error information.
     */

    String getErrorDetails();
}
