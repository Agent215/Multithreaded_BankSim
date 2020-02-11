package edu.temple.cis.c3238.banksim;

/**
 * @author Cay Horstmann
 * @author Modified by Paul Wolfgang
 * @author Modified by Charles Wang
 * @author Modified by Alexa Delacenserie
 * @author Modified by Tarek Elseify
 */
public class BankSimMain {

    public static final int NACCOUNTS = 10;
    public static final int INITIAL_BALANCE = 10000;

    public static void main(String[] args) throws InterruptedException {
        Bank b = new Bank(NACCOUNTS, INITIAL_BALANCE);
        Thread[] threads = new Thread[NACCOUNTS];

        // Start a thread for each account.
        for (int i = 0; i < NACCOUNTS; i++) {
            threads[i] = new TransferThread(b, i, INITIAL_BALANCE);
            threads[i].start();
        }
        System.out.printf("%-30s Bank transfer is in process.\n", Thread.currentThread().toString());

        // Wait for all threads to complete execution.
        for(Thread thread : threads) {
            thread.join();
        }

        // Test to see whether the balances have remained the same
        // After all transactions have completed.
        b.test();
          
    }
}


