package banking;

public class BankAccount {
    // Class invariant - always true
    // balance >= 0 - this must be true
    // true when you create it, destroy it and update it

    private double currentBalance;

    public BankAccount() {
        currentBalance = 0;
    }

    /**
     * Precondition - balance is greater than or equal to 0
     * Postcondition - the amount given is added to the currentBalance
     * @param amount
     */
    public void deposit(double amount) {
        currentBalance += amount;
    }

    /**
     * Precondition - If this is true the method will succeed and return true
     * Postcondition - If precondition is met then the amount given will be removed from the currentBalance
     * @param amount
     */
    public boolean withdraw(double amount) {
        if (amount >= currentBalance) {
            currentBalance -= amount;
            return true;
        }
        return false;
    }

    public double getBalance() {
        return currentBalance;
    }

}