# 🏦 Bank Account Management & Exception Handling System

The **Bank Account Management & Exception Handling System** is a Java-based project that simulates a banking environment. It models different types of bank accounts (e.g., Savings, Current) and includes functionalities like account management, transaction handling, and custom exception handling. The system handles errors like insufficient funds, invalid transaction amounts, and penalties for overdrafts, while also supporting various account types with specific behaviors.

## ⚙️ How It Works

This system models bank accounts with specific attributes and supports transactions with built-in error handling. The key components are:

- **BankAccount Interface**: Defines common behaviors for all account types.
- **Account Classes**: Includes multiple account types such as Savings and Current accounts.
- **BankExceptionInterface**: An interface for handling common error messages related to bank operations.
- **Custom Exceptions**:
  - **InsufficientFundsException**: Thrown when a transaction exceeds available funds.
  - **InvalidAmountException**: Thrown when an invalid transaction amount is entered.
  - **PenaltyException**: Thrown when a penalty (due to overdrafts) exceeds the available balance.

Each account type has its own rules for handling deposits, withdrawals, and penalties, ensuring realistic behavior of the banking system.

## 🗂 Project Structure

BankAccountSystem/
├── BankAccount.java # Interface for all account types  
├── SavingsAccount.java # Class for Savings account management  
├── CurrentAccount.java # Class for Current account management  
├── FixedDepositAccount.java # Class for Fixed Deposit account management
├── BankExceptionInterface.java # Interface defining common exception methods  
├── InsufficientFundsException.java # Exception for insufficient balance  
├── InvalidAmountException.java # Exception for invalid transaction amounts  
├── PenaltyException.java # Exception for penalties exceeding the balance  
├── inputs/ # Folder containing input files(accounts.txt and transactions.txt)
├── outputs/ # Folder containing the expected output
└── README.md # Project description and usage guide

## ▶️ How to Use

1. **Compile the Java files**:
   javac BankAccount.java SavingsAccount.java CurrentAccount.java
   FixedDepositAccount.java BankExceptionInterface.java InsufficientFundsException.java InvalidAmountException.java PenaltyException.java
   java Main accounts.txt transactions.txt
