package edu.temple.cis.c3238.banksim;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Cay Horstmann
 * @author Modified by Paul Wolfgang
 * @author Modified by Charles Wang
 * @author Modified by Alexa Delacenserie
 * @author Modified by Tarek Elseify
 */
public class Bank {

    public static final int NTEST = 10;
    private final Account[] accounts;
    private long numTransactions = 0;
    private final int initialBalance;
    private final int numAccounts;
    private boolean startTest;
    private int semaphoreCounter;
    private boolean isOpen;
    private boolean ShouldTest = false;

    public Bank(int numAccounts, int initialBalance) {
        this.initialBalance = initialBalance;
        this.numAccounts = numAccounts;
        accounts = new Account[numAccounts];
        for (int i = 0; i < accounts.length; i++) {
            accounts[i] = new Account(i, initialBalance, this);
        }
        numTransactions = 0;
        semaphoreCounter = 9;
        this.isOpen = true;
        this.ShouldTest = false;
    }

    // I'm pretty sure this is wrong.  A thread can be interrupted between calling waitFor and withdraw
    // we haven't actually solved waiting it's just less likely.  I think the check should be within transfer
    
    public void transfer(int from, int to, int amount) {
        //accounts[from].waitForAvailableFunds(amount);
        if (accounts[from].withdraw(amount)) {
            accounts[to].deposit(amount);
        }
        
        // Uncomment line when race condition in test() is fixed.
        if (shouldTest() || startTest) {
        	startTest = true;
        	test();
        }
    }

    public synchronized void test() {
    	if (semaphoreCounter-- != 0)
			try {
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		else {
	    	int totalBalance = 0;
	        for (Account account : accounts) {
	            System.out.printf("%-30s %s%n", 
	                    Thread.currentThread().toString(), account.toString());
	            totalBalance += account.getBalance();
	        }
	        System.out.printf("%-30s Total balance: %d\n", Thread.currentThread().toString(), totalBalance);
	        if (totalBalance != numAccounts * initialBalance) {
	            System.out.printf("%-30s Total balance changed!\n", Thread.currentThread().toString());
	            System.exit(0);
	        } else {
	            System.out.printf("%-30s Total balance unchanged.\n", Thread.currentThread().toString());
	        }
	        semaphoreCounter = 9;
	        notifyAll();
    	}
    }
/*
        if (!this.isOpen) {
            return;
        }
        // accounts[from].waitForAvailableFunds(amount);
        if (accounts[from].withdraw(amount)) {
            accounts[to].deposit(amount);
        }

    }
*/
/*    public void test() {

        synchronized (this) {

            int totalBalance = 0;
            for (Account account : accounts) {
                System.out.printf("%-30s %s%n",
                        Thread.currentThread().toString(), account.toString());
                totalBalance += account.getBalance();
            }
            System.out.printf("%-30s Total balance: %d\n", Thread.currentThread().toString(), totalBalance);
            if (totalBalance != numAccounts * initialBalance) {
                System.out.printf("%-30s Total balance changed!\n", Thread.currentThread().toString());
                System.exit(0);
            } else {
                System.out.printf("%-30s Total balance unchanged.\n", Thread.currentThread().toString());
            }
            notifyAll();

        } // end synchronized

        // notifyAll();
    } // end test
*/
    public int getNumAccounts() {
        return numAccounts;
    }
    
    
    //This isn't actually accurate but doesn't really affect anything

    public boolean getIsOpen() {
        return isOpen;
    }

    public int size() {
        return accounts.length;
    }

    public void closeBank() {

        this.isOpen = false;
    }

    public boolean GetShouldTest() {

        return this.ShouldTest;
    }

    public boolean shouldTest() {
        if (numTransactions % NTEST == 0) {
            this.ShouldTest = true;
        } else {
            this.ShouldTest = false;
        }
        return ++numTransactions % NTEST == 0;
    }

}
