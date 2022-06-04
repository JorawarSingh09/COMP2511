package banking;

import java.util.List;
import java.util.ArrayList;

public class LoggedBankAccount extends BankAccount {
    private List<String> log;

    public LoggedBankAccount() {
        super();
        log = new ArrayList<String>();
    }

    /**
     * Precondition - balance is greater than or equal to 0
     * Postcondition - the amount given is added to the currentBalance and the deposit is recorded in the log
     * @param amount
     */
    @Override
    public void deposit(double amount) {
        super.deposit(amount);
        log.add(amount + " deposited");
    }

    /**
     * Precondition - If this is true the method will succeed and return true
     * Postcondition - If precondition is met then the amount given will be removed from the currentBalance, 
     * and the withdraw is recorded in the log
     * @param amount
     */
    @Override
    public boolean withdraw(double amount) {
        boolean success = super.withdraw(amount);
        if (success) {
            log.add(amount + " withdrawn");
        }
        return success;
    }
}
