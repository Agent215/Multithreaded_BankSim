package edu.temple.cis.c3238.banksim;

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
    public final Account[] accounts;
    private long numTransactions = 0;
    private final int initialBalance;
    private final int numAccounts;
    private boolean open = true;
    AtomicInteger atom;             // use an atomic integer to provide mutual exclusion for test method

    public Bank(int numAccounts, int initialBalance) {
        this.initialBalance = initialBalance;
        this.numAccounts = numAccounts;
        accounts = new Account[numAccounts];
        for (int i = 0; i < accounts.length; i++) {
            accounts[i] = new Account(this, i, initialBalance);
        }
        numTransactions = 0;
        // use atomic int to make sure that
        atom = new AtomicInteger(0);
    }

    public void transfer(int from, int to, int amount) {

        //call function to check if bank needs to wait for more funds
        accounts[from].waitForAvailableFunds(amount);
        // if we are transfering then the atomic int will not be 0
        increment();
        if (accounts[from].withdraw(amount)) {
            accounts[to].deposit(amount);
        }
        decrement();
        Thread.yield();
        // once we leave the transfer let the test know it can test.
        // Uncomment line when race condition in test() is fixed.

            if (shouldTest()) {
                test();
            }

    }

    public synchronized void test() {

        while ((atom.get() != 0) && isOpen()) {
            try {
                wait(10);
                //System.out.printf("QWERTY %d\n", atom.get());
            } catch (InterruptedException e) {

                e.printStackTrace();
            }
        }
        // keep track of atomic int for debugging
       // System.out.printf("atom is %d\n", atom.get());
        if (atom.get() == 0) {
         //   System.out.printf("atom is %d\n", atom.get());
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
        // wake any threads that are sleeping. ( should be none)
        notifyAll();
    }

    public int getNumAccounts() {
        return numAccounts;
    }

    public synchronized boolean shouldTest() {
        return ++numTransactions % NTEST == 0;
    }

    synchronized boolean isOpen() {
        return open;
    }

    synchronized void increment(){

        atom.incrementAndGet();
    }

    synchronized void decrement(){

        atom.decrementAndGet();
    }

    void closeBank() {
        synchronized (this) {
            open = false;
        }
        for (Account account : accounts) {
            synchronized (account) {
                account.notifyAll();
            }
        }
    }
}
