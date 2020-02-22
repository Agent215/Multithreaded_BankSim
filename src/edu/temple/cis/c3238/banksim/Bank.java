package edu.temple.cis.c3238.banksim;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

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
    private CyclicBarrier barrier;
    Runnable testingThread;
    private boolean open = true;
    AtomicInteger atom;

    public Bank(int numAccounts, int initialBalance) {
        this.initialBalance = initialBalance;
        this.numAccounts = numAccounts;
        accounts = new Account[numAccounts];
        for (int i = 0; i < accounts.length; i++) {
            accounts[i] = new Account(this, i, initialBalance);
        }
        numTransactions = 0;
    	testingThread = new TestingThread(accounts, numAccounts, initialBalance);
    	barrier = new CyclicBarrier(numAccounts, testingThread);
    	atom = new AtomicInteger(0);
    }

    public void transfer(int from, int to, int amount) {
    	
        accounts[from].waitForAvailableFunds(amount);
    	atom.incrementAndGet();
        if (accounts[from].withdraw(amount)) {
            accounts[to].deposit(amount);
        }
        atom.decrementAndGet();
        Thread.yield();
        // Uncomment line when race condition in test() is fixed.
        if (shouldTest()) test();
    }

    public synchronized void test() {
/*    	try {
			barrier.await();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BrokenBarrierException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
    	while ((atom.get() != 0) && isOpen()){
    		try {
				wait(10);
				//System.out.printf("QWERTY %d\n", atom.get());
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
		System.out.printf("atom is %d\n", atom.get());
		if (atom.get() == 0) {
			System.out.printf("atom is %d\n", atom.get());
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
		}
			//testingThread.run();
		notifyAll();
    }

    public int getNumAccounts() {
        return numAccounts;
    }
    
    public boolean shouldTest() {
        return ++numTransactions % NTEST == 0;
    }
    
    synchronized boolean isOpen() {return open;}
    
    void closeBank() {
        synchronized (this) {
            open = false;
        }
        for (Account account : accounts) {
            synchronized (account) {account.notifyAll();}
        }
    }
}