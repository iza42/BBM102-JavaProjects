import java.util.UUID;

/**
 * Represents a transaction within the banking system.
 * This class encapsulates details about a transaction, including the transaction ID, sender ID,
 * receiver ID and the transaction amount. Each transaction is assigned a unique ID upon creation.
 */
public class Transaction {
    private final String transactionId;   // Unique identifier for the transaction.
    private final String senderId;       // ID of the account initiating the transaction.
    private final String receiverId;     //  ID of the account receiving the transaction.
    private final double amount;         // The amount involved in the transaction.


    /**
     * Constructs a new Transaction with the specified sender, receiver and amount.
     * A unique transaction ID is automatically generated.
     *
     * @param senderId    The ID of the account initiating the transaction.
     * @param receiverId  The ID of the account receiving the transaction.
     * @param amount      The amount of the transaction.
     */
    public Transaction( String senderId, String receiverId, double amount) {
        this.transactionId = UUID.randomUUID().toString();
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.amount = amount;

    }

    //Did not write any getter or setter method because there were no use of it.

    /**
     * Returns a string representation of the transaction including transaction details.
     * @return A formatted string with transaction details.
     */
    @Override
    public String toString() {
        String message = "------------------------------------\n";
        message = message + String.format("Transaction UD: %s  \nSender: %s \nReceiver: %s \nAmount: %.1f \n",
                transactionId, senderId, receiverId,amount);
        message = message + "------------------------------------";
        return message;

    }
}
