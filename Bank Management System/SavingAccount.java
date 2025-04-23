import java.util.ArrayList;
import java.util.List;

/**
 * Represents the Saving Account in the banking system.
 * This account type maintains a minimum balance attribute and supports interest calculations.
 * It also includes functionalities for deposits, withdrawals, and risk evaluations and some other functionalities.
 */
public class SavingAccount implements BankAccountTypeInterface{
    private final String accountID; //Unique identifier for the account.
    private final String account_type; //Type of the account. Always "Saving" for this class.
    private double current_balance; //Current balance of the account.
    private final double interest_rate; //Interest rate for the account.
    private final double min_balance; //Minimum balance of the account.
    private List<Transaction> my_transaction_history; // History of transactions associated with this account.
    private static final double PENALTY_RATE = 0.05;  // Penalty rate for violating the minimum balance requirement.given as 0.05 in the assignment


    /**
     * Constructs a new SavingAccount with the specified ID, balance, interest rate, and minimum balance.
     *
     * @param accountID       Unique identifier for the account.
     * @param current_balance Initial balance of the account.
     * @param interest_rate   Interest rate for the account.
     * @param min_balance     Minimum balance of the account.
     */
    public SavingAccount(String accountID, double  current_balance, double interest_rate, double min_balance){
        this.account_type="Saving";
        this.accountID=accountID;
        this.current_balance=current_balance;
        this.interest_rate=interest_rate;
        this.min_balance=min_balance;
        this.my_transaction_history= new ArrayList<>();
    }


    // I also wrote only the ones that I do use from all possible getter and setter methods.
    // explained in the interface that Saving account implements
    public void setCurrentBalance(double current_balance){
        this.current_balance=current_balance;
    }
    public double getCurrentBalance(){
        return this.current_balance;
    }

    /**
     * Deposits the amount into the account.
     * Creates a transaction record for the deposit and adds it to the my_transaction_history list.
     *
     * @param receiver The account that receives the deposit.
     * @param amount   The amount to deposit (must be positive).
     * Note: If the amount is negative, it throws and catches an InvalidAmountException internally.
     */
    @Override
    public void deposit(BankAccountTypeInterface receiver, double amount)  {
        try{
            if(amount<0){
                throw  new InvalidAmountException("Saving Account","Invalid amount for deposit.", amount);
            }
            my_transaction_history.add(new Transaction(receiver.getAccountID(), this.accountID,amount));
            setCurrentBalance(getCurrentBalance()+amount);
        }catch (InvalidAmountException e){
            System.out.println(e.getErrorDetails());
        }

    }

    /**
     * Withdraws a specified amount from the account.
     * Ensures that the withdrawal does not be less than the minimum balance .
     * If the balance drops below the minimum, a penalty is applied.
     *
     * @param receiver The account to which the amount is transferred.
     * @param amount   The amount to withdraw (must be positive).
     * @return true if the withdrawal is successful otherwise returns false.
     * Note: If the amount is negative, it throws and catches an InvalidAmountException internally.
     * If the withdrawal exceeds the available balance, it throws and catches an InsufficientFundsException internally.
     */
    @Override
    public boolean withdraw(BankAccountTypeInterface receiver, double amount)  {
       try {
           if (amount < 0) {
               throw new InvalidAmountException("Saving Account", "Invalid amount for withdraw.", amount);
           } else if (amount > current_balance + min_balance) {
               throw new InsufficientFundsException("Saving Account", "Amount exceeds overdraft limit.", current_balance, amount, min_balance);
           }
       }catch (InvalidAmountException e) {
           System.out.println(e.getErrorDetails());
           return false;
       }catch (InsufficientFundsException e) {
           System.out.println(e.getErrorDetails());
           return false;
       }
           if (current_balance-amount<min_balance){ // penalty will be applied
               my_transaction_history.add(new Transaction(this.accountID,receiver.getAccountID(),-amount));
               current_balance-=amount;
               double penalty =(min_balance-current_balance)*PENALTY_RATE;
               my_transaction_history.add(new Transaction(this.accountID,receiver.getAccountID(),-penalty));
               current_balance-=penalty;

           }
           else{
               my_transaction_history.add(new Transaction(this.accountID,receiver.getAccountID(),-amount));
               setCurrentBalance(getCurrentBalance()-amount);

           }
           return true;
       }

    /**
     * Evaluates the risk level of the account based on its balance transaction frequency and minimum balance.
     * Prints the risk evaluation to the console and called inside  the displayAccountDetails function.
     */
    @Override
    public void evaluateRisk() {
        String risk;
        if(current_balance< (1.2)*min_balance){
            risk = "High Risk: Balance is close to the minimum requirement.";

        }
        else if(my_transaction_history.size()>10){
            risk="Medium Risk: Transaction frequency is over 10.";
        }
        else {
            risk="Low Risk: Account is in stable.";
        }
        System.out.println(risk);


    }
    /**
     * Displays the details of the account, including transaction history, current balance, interest rate and risk evaluation.
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
            String message= "Savings Account - Account Number: "+this.accountID+"\n"+
                    "Balance: $"+((this.current_balance*10.0)/10)+"\n"+
                    "Interest Rate: "+ (this.interest_rate*100)+"%";
            System.out.println(message);
            System.out.println();
            System.out.println("Account Risk Evaluation");
            System.out.print(this.account_type+" Account-");evaluateRisk();
            System.out.println("************************************************************************************************************");
        }


    /**
     * Checks if the account is a high value account.
     * A high value account has a balance greater than $10,000.
     *
     * @return true if the account is high value otherwise returns false.
     */
    @Override
    public boolean isHighValueAccount() {
        return current_balance>10000;
    }

    // explained in the interface that Saving account implements
    @Override
    public String getAccountID() {
        return this.accountID;
    }

    /**
     * Calculates the interest for the Saving account based on its current balance and interest rate.
     *
     * @return The calculated interest amount.
     */
    @Override
    public double Interest_calculation() {
        return current_balance*interest_rate;
    }
}

