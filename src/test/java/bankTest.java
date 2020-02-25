/*
These are unit tests for the bank class 
in the banking sim for lab 5 3296 at Temple university.
 */
package test.java;

import static org.junit.Assert.assertEquals;

import edu.temple.cis.c3238.banksim.Bank;
import edu.temple.cis.c3238.banksim.Account;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author brahm
 */
public class bankTest {

    private int initialBalance;
    private int numAccounts;

    private Bank bank;
    private Account accounts[];

    @Before
    public void init() {
        this.initialBalance = 10000;
        this.numAccounts = 10;

        this.bank = new Bank(this.numAccounts, this.initialBalance);
        this.accounts = this.bank.accounts;
    }

    /*
    make sure that we are starting off corectly will the balances at 10000
     */
    @Test
    public void InitialBalancesShouldBe10000() {
        for (int i = 0; i < 10; i++) {
            assertEquals("This account balance should be " + this.initialBalance,
                    this.accounts[i].getBalance(),
                    this.initialBalance);
        }
    }

    /*
    test the transfer method on two arbitray acounts
     */
    @Test
    public void Transfer1000FromAccount0ToAccount9() {
        this.bank.transfer(1, 8, 1000);
        assertEquals("Account 1 should have 9000.",
                this.accounts[1].getBalance(),
                9000);
        assertEquals("Account 8 should have 11000.",
                this.accounts[8].getBalance(),
                11000);
    }

}
