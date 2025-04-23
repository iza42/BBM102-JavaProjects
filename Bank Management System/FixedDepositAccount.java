import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

/**
 * Represents a Fixed Deposit Account in the banking system.
 * This account type has a fixed term, penalty rate for early withdrawal, maturity status and also supports interest calculation addition to other methods and attributes.
 * It supports deposits, withdrawals with penalties(if withdraw before the maturity date) and risk evaluations.
 */
public class FixedDepositAccount implements BankAccountTypeInterface{
    private final String accountID; // Unique identifier for the account.
    private final String account_type; //Type of the account. Always "Deposit" for this class.
    private double current_balance; //Current balance of the account.
    private final double interest_rate; //Interest rate for the account.
    private final int term_in_months; //Term of the account in months.
    private final double penalty_rate;// Penalty rate for early(before maturity date) withdrawals.
    private final String start_date; // Start date of the account.
    private String maturity_status; // Maturity status of the account ("Active" or "Matured").
    private List<Transaction> my_transaction_history; //History of transactions associated with this account.

    /**
     * Constructs a new FixedDepositAccount with the specified details.
     *
     * @param accountID       Unique identifier for the account.
     * @param current_balance Initial balance of the account.
     * @param interest_rate   Interest rate for the account.
     * @param term_in_months  Term duration of the account in months.
     * @param penalty_rate    Penalty rate for early withdrawals.
     * @param start_date      Start date of the account in "yyyy-MM-dd" format.
     */
    public FixedDepositAccount(String accountID, double current_balance, double interest_rate ,int term_in_months, double penalty_rate, String start_date){
        this.accountID=accountID;
        this.account_type="Deposit";
        this.current_balance=current_balance;
        this.interest_rate=interest_rate;
        this.term_in_months=term_in_months;
        this.penalty_rate=penalty_rate;
        this.start_date=start_date.trim();

        setMaturity_status();

        my_transaction_history=new ArrayList<>();
    }
    /**
     * Sets the maturity status of the account based on the current date ,term duration and start date.
     * If the term has ended the status is "Matured" otherwise the status is "Active".
     */
    public void setMaturity_status(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate startDate = LocalDate.parse(this.start_date.trim(), formatter);
        LocalDate maturityDate = startDate.plusMonths(this.term_in_months);
        LocalDate today = LocalDate.now();

        this.maturity_status = (today.isBefore(maturityDate)) ? "Active" : "Matured";
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
    public void deposit(BankAccountTypeInterface receiver, double amount) {
        try{
            if(amount<0){
                throw  new InvalidAmountException("Fixed Deposit Account","Invalid amount for deposit.", amount);
            }
            my_transaction_history.add(new Transaction(receiver.getAccountID(), this.accountID,amount));
            setCurrentBalance(getCurrentBalance()+amount);
        } catch (InvalidAmountException e) {
            System.out.println(e.getErrorDetails());
        }
    }
    /**
     * Withdraws a specified amount from the account.
     * Applies a penalty for early withdrawal if the account is still active(if the maturity date has not come yet.).
     *
     * @param receiver The account to which the amount is transferred.
     * @param amount   The amount to withdraw (must be positive).
     * @return true if the withdrawal is successful; false otherwise.
     * Note: If the amount is negative, it throws and catches an InvalidAmountException internally.
     *       If insufficient funds are available after applying the penalty, it throws and catches a PenaltyException internally.
     *       If insufficient funds are available for withdrawal without a penalty, it throws and catches an InsufficientFundsException internally.
     */
    @Override
    public boolean withdraw(BankAccountTypeInterface receiver, double amount) {
        try {
            if (amount < 0) {
                throw new InvalidAmountException("Fixed Deposit Account", "Invalid amount for withdraw.", amount);
            }

            if ("Active".equals(this.maturity_status)) {
                double penalty = amount * this.penalty_rate;
                if (penalty+amount > this.current_balance) {
                    throw new PenaltyException("Fixed Deposit Account", "Insufficient funds including penalty charges.", penalty, this.current_balance, amount);
                }
                this.current_balance -= (amount + penalty);
                my_transaction_history.add(new Transaction(this.accountID, receiver.getAccountID(), -amount-penalty));
            } else {
                if (amount > this.current_balance) {
                    throw new InsufficientFundsException("Fixed Deposit Account", "Insufficient funds.", this.current_balance, amount, 0); // 0 is for the limit
                }
                this.current_balance -= amount;
                my_transaction_history.add(new Transaction(this.accountID, receiver.getAccountID(), -amount));
            }
        } catch (InvalidAmountException e) {
            System.out.println(e.getErrorDetails());
            return false;
        } catch (PenaltyException e) {
            System.out.println(e.getErrorDetails());
            return false;
        } catch (InsufficientFundsException e) {
            System.out.println(e.getErrorDetails());
            return false;
        }
        return true;
    }


    /**
     * Evaluates the risk level of the account based on its maturity status and proximity to maturity date.
     * Prints the risk evaluation to the console  and called inside  the displayAccountDetails function.
     */
    @Override
    public void evaluateRisk() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate startDate = LocalDate.parse(this.start_date, formatter);
        LocalDate maturityDate = startDate.plusMonths(this.term_in_months);
        LocalDate today = LocalDate.now();

        String risk;
        if ("Matured".equals(this.maturity_status)) {
            risk = "High Risk: Matured but not withdrawn.";
        } else {
            long daysToMaturity = ChronoUnit.DAYS.between(today, maturityDate);
            if (daysToMaturity <= 30) {
                risk = "Medium Risk: Within 30 days of maturity.";
            } else {
                risk = "Low Risk: Account is in stable.";
            }
        }

        System.out.println(risk);

    }
    /**
     * Displays the details of the account, including transaction history, balance, interest rate, maturity date and risk evaluation.
     * This method internally calls `evaluateRisk` to determine the account's risk level.
     */
    @Override
    public void displayAccountDetails() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        System.out.println("****************** Summary for Account " + this.accountID + " ******************");
        for (Transaction t : my_transaction_history) {
            System.out.println(t);
        }
        System.out.println();
        System.out.println("Account Info");


        LocalDate startDate = LocalDate.parse(this.start_date, formatter);
        LocalDate maturityDate = startDate.plusMonths(this.term_in_months);
        String formattedMaturityDate = maturityDate.format(formatter);

        System.out.printf("Fixed Deposit Account - Account Number: %s%nBalance: $%.1f%nInterest Rate: %.1f%%%nMaturity Date: %s%nStatus: %s%n",
                this.accountID, this.current_balance, this.interest_rate * 100, formattedMaturityDate, this.maturity_status);
        System.out.println();
        System.out.println("Account Risk Evaluation");
        System.out.print("Fixed Deposit Account-");
        evaluateRisk();
        System.out.println("************************************************************************************************************");
    }

    /**
     * Checks if the account is a high value account.
     * A high value account has a balance of at least $50,000.
     *
     * @return true if the account is high value otherwise returns false.
     */
    @Override
    public boolean isHighValueAccount() {
        return this.current_balance >= 50000;
    }

    /**
     * Calculates the interest for the account based on its balance, interest rate, and term duration.
     * @return The calculated interest amount.
     */
    @Override
    public double Interest_calculation() {
        return this.current_balance * this.interest_rate * (this.term_in_months / 12.0);
    }


// These methods explained in the interface that Fixed Deposit Account class implements.
    @Override
    public String getAccountID() {
        return this.accountID;
    }
    @Override
    public void setCurrentBalance(double currentBalance) {
this.current_balance=currentBalance;
    }
    @Override
    public double getCurrentBalance() {
        return this.current_balance;
    }
}
