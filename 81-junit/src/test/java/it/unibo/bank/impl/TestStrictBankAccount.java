package it.unibo.bank.impl;

import it.unibo.bank.api.AccountHolder;
import it.unibo.bank.api.BankAccount;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Assertions;

public class TestStrictBankAccount {

    private final static int INITIAL_AMOUNT = 100;
    private final static double MANAGEMENT_FEE = 5;
    public static final double TRANSACTION_FEE = 0.1;
    

    // Create a new AccountHolder and a StrictBankAccount for it each time tests are executed.
    private AccountHolder mRossi;
    private BankAccount bankAccount;

    /**
     * Prepare the tests.
     */
    @BeforeEach
    public void setUp() {
        this.mRossi = new AccountHolder("Mario", "Rossi", 1);
        this.bankAccount = new StrictBankAccount(mRossi, 0.0);
    }

    /**
     * Test the initial state of the StrictBankAccount.
     */
    @Test
    public void testInitialization() {
        assertEquals(0.0, bankAccount.getBalance());
        assertEquals(0, bankAccount.getTransactionsCount());
        assertEquals(mRossi, bankAccount.getAccountHolder());
    }

    /**
     * Perform a deposit of 100€, compute the management fees, and check that the balance is correctly reduced.
     */
    @Test
    public void testManagementFees() {
        int expectedValue = 0;
        double expectedBalance = 0.0;
        assertFalse(bankAccount.getTransactionsCount() > 0);
        expectedValue++;
        this.bankAccount.deposit(this.mRossi.getUserID(), INITIAL_AMOUNT);
        this.bankAccount.chargeManagementFees(this.mRossi.getUserID());
        expectedBalance = INITIAL_AMOUNT - MANAGEMENT_FEE - TRANSACTION_FEE*expectedValue;
        expectedValue = 0;
        assertEquals(expectedBalance, bankAccount.getBalance());
        assertFalse(bankAccount.getTransactionsCount() > 0);
    }

    /**
     * Test that withdrawing a negative amount causes a failure.
     */
    @Test
    public void testNegativeWithdraw() {
        int negativeWithdraw = -100;
        double expectedBalance = this.bankAccount.getBalance();
        assertFalse(bankAccount.getTransactionsCount() > 0);
        try {
            bankAccount.withdraw(this.mRossi.getUserID(), negativeWithdraw);
            Assertions.fail("The withdraw was successful but should throw an exception");
        } catch(IllegalArgumentException e){
            assertEquals("Cannot withdraw a negative amount", e.getMessage());
            assertEquals(this.bankAccount.getBalance(), expectedBalance);
            assertFalse(bankAccount.getTransactionsCount() > 0);
        }
    }

    /**
     * Test that withdrawing more money than it is in the account is not allowed.
     */
    @Test
    public void testWithdrawingTooMuch() {
        int amount = INITIAL_AMOUNT;
        double expectedBalance = this.bankAccount.getBalance();
        assertFalse(bankAccount.getTransactionsCount() > 0);
        try {
            bankAccount.withdraw(mRossi.getUserID(), amount);
            Assertions.fail("The withdraw was successful but should throw an exception");
        } catch (IllegalArgumentException e) {
            assertEquals("Insufficient balance", e.getMessage());
            assertEquals(this.bankAccount.getBalance(), expectedBalance);
            assertFalse(bankAccount.getTransactionsCount() > 0);
        }
    }
}
