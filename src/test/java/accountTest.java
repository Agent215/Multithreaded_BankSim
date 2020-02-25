/*
These are unit tests for the bank class 
in the banking sim for lab 5 3296 at Temple university.
 */
package test.java;

import edu.temple.cis.c3238.banksim.Account;
import edu.temple.cis.c3238.banksim.Bank;
import org.junit.Before;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 *
 * @author brahm
 */
public class accountTest {

    private int initialBalance;
    private int numAccounts;

    private Bank bank;
    private Account accounts[];
    // TODO intialization for tests
    @Before
    public void init() {
        this.initialBalance = 10000;
        this.numAccounts = 10;

        this.bank = new Bank(this.numAccounts, this.initialBalance);
        this.accounts = this.bank.accounts;
    }
    
    // TODO test to withdraw some amount then check balance is correct

    @Test
    public void Withdraw2000ThenCheckBalance() {
        boolean shouldBeTrue = this.accounts[0].withdraw(2000);
        assertEquals("The withdraw function should return true.",
                shouldBeTrue,
                true);
        assertEquals("The account balance should be 9,000.",
                this.accounts[0].getBalance(),
                8000);
    }

    // TODO test to deposit some amount then check that balance is correct

    @Test public void ShouldDeposit2000ThenCheckBalance(){

        this.accounts[1].deposit(2000);
        int amount = this.accounts[1].getBalance();

        assertEquals("the balance should be 12000",
                this.accounts[1].getBalance(),
                12000);

    }

}
