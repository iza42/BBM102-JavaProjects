import java.util.ArrayList;
import java.util.List;

/**
 * Represents the Current Account in the banking system.
 * This account type allows for overdraft up to a specified limit and maintains a history of transactions.
 * It supports deposit, withdrawal and risk evaluation and some other functionalities.
 */
public class CurrentAccount implements BankAccountTypeInterface{

    private final String accountType; //  Type of the account. Always "Current" for this class.
    private final String accountID; //Unique identifier for the account.
    private double current_balance; //Current balance of the account.
    private final double overdraft_limit; //Overdraft limit for the account.
    private List<Transaction> my_transaction_history; //History of transactions associated with this account.

    /**
     * Constructs a new CurrentAccount with the specified ID, balance, and overdraft limit.
     *
     * @param accountID        Unique identifier for the account.
     * @param current_balance  Initial balance of the account.
     * @param overdraft_limit  Maximum allowable overdraft.
     */
    public CurrentAccount(String accountID, double current_balance, double overdraft_limit){
        this.accountID=accountID;
        this.current_balance=current_balance;
        this.overdraft_limit=overdraft_limit;
        this.accountType="Current";
        this.my_transaction_history=new ArrayList<>();
    }

    // I also wrote only the ones that I do use from all possible getter and setter methods.

   // explained in the interface that Current account implements
    public void setCurrentBalance(double current_balance){
        this.current_balance=current_balance;
    }
    // explained in the interface that Current account implements
    public double getCurrentBalance(){
        return this.current_balance;
    }



    /**
     * Deposits the amount into the account.
     * Creates a transaction record for the deposit and adds it to my_transaction_history list.
     * @param receiver The account that  receiving the deposit.
     * @param amount   The amount to deposit (must be positive or InvalidAmountException will be thrown and handled.)
     * Note: If the amount is negative, it throws and catches an InvalidAmountException internally.
     */
    @Override
    public void deposit(BankAccountTypeInterface receiver,double amount) {
       try{
           if(amount<0){
               throw new InvalidAmountException("Current Account","Invalid amount for deposit.", amount);
           }
           my_transaction_history.add(new Transaction(receiver.getAccountID(), this.accountID,amount));
           setCurrentBalance(getCurrentBalance()+amount);
       }catch (InvalidAmountException e){
           System.out.println(e.getErrorDetails());
       }
    }

    /**
     * Withdraws a specified amount from the account.
     * Ensures that the withdrawal does not exceed the overdraft limit.
     *
     * @param receiver The account to which the amount is transferred.
     * @param amount   The amount to withdraw (must be positive or InvalidAmountException will be thrown and handled.).
     * Note: If the amount is negative, it throws and catches an InvalidAmountException internally.
     * If the amount exceeds the overdraft limit, it throws and catches an InsufficientFundsException internally.
     * @return true if the withdrawal is successful otherwise false.
     */
    @Override
    public boolean withdraw(BankAccountTypeInterface receiver,double amount) {
        try{
            if(amount<0){
                throw new InvalidAmountException("Current Account","Invalid amount for withdraw.", amount);
            }
            if(amount>current_balance+overdraft_limit){
                throw new InsufficientFundsException("Current Account", "Amount exceeds overdraft limit.", current_balance, amount, overdraft_limit);
            }
            my_transaction_history.add(new Transaction(this.accountID,receiver.getAccountID(),-amount));
            setCurrentBalance(getCurrentBalance()-amount);


        } catch (InvalidAmountException e) {
            System.out.println(e.getErrorDetails());
            return false;
        }catch (InsufficientFundsException e) {
            System.out.println(e.getErrorDetails());
            return false;
        }
        return true;
    }

    /**
     * Evaluates the risk level of the account based on its balance and overdraft limit usage.
     * Prints the risk evaluation to the console and called inside  the displayAccountDetails function.
     */
    @Override
    public void evaluateRisk() {
        String risk;
        if(current_balance<0 && Math.abs(current_balance)>overdraft_limit*(0.8)){ // If using >80% of overdraft limit.
            risk="High Risk: Account is both in overdraft and using more than 80 percent of the overdraft.";
        }
        else if(current_balance<0){ // If account is in overdraft but <80% used.
            risk="Medium Risk: Account is in overdraft.";
        }
        else if(current_balance>0){ // if account has positive balance
            risk="Low Risk: Account is in stable.";
        }
        else{ // nothing specified for this situation
            risk = "Medium Risk: Current account balance is zero.";
        }
        System.out.println(risk);
    }

    /**
     * Displays the details of the account, including transaction history,current balance,risk evaluation,account ID and overdraft limit.
     * This method internally calls `evaluateRisk` to determine the account's risk level.
     */
    @Override
    public void displayAccountDetails() {
        System.out.println("****************** Summary for Account "+this.accountID+" ******************");
        for (Transaction t: my_transaction_history){
            System.out.println(t);
            System.out.println();
        }
        System.out.println();
        System.out.println("Account Info");
        String message=  this.accountType+" Account - Account Number: "+this.accountID+"\n"+
                "Balance: $"+this.current_balance+"\n"+
                "Overdraft Limit: $"+ this.overdraft_limit;
        System.out.println(message);
        System.out.println();
        System.out.println("Account Risk Evaluation");
        System.out.print(this.accountType+" Account-");evaluateRisk();
        System.out.println("************************************************************************************************************");
    }

    /**
     * Checks if the account is a high value account.
     * A high value account has a balance above $5000 and an overdraft limit above $1000.
     *
     * @return true if the account is high value otherwise return false.
     */
    @Override
    public boolean isHighValueAccount() {
        //If the balance is above 5000 $, and over draft limit is above 1000 $ the account is considered as high value account.
        return ((current_balance>5000) && (overdraft_limit>1000));
    }

    // explained in the interface that Current account implements
    @Override
    public String getAccountID() {
        return this.accountID;
    }

    /**
     * For Current Accounts, this method always returns 0 as they do not earn any  interest.
     *
     * @return The interest amount, which is always 0 for Current Accounts.
     */
    @Override
    public double Interest_calculation() {
        return 0;
    }
}
