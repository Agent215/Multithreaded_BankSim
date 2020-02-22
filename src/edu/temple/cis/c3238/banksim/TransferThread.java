package edu.temple.cis.c3238.banksim;

import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Cay Horstmann
 * @author Modified by Paul Wolfgang
 * @author Modified by Charles Wang
 * @author Modified by Alexa Delacenserie
 * @author Modified by Tarek Elseify
 */
class TransferThread extends Thread {

    private final Bank bank;
    private final int fromAccount;
    private final int maxAmount;
    private Semaphore testingSemaphore;

    public TransferThread(Bank b, int from, int max, Semaphore testingSemaphore) {
        bank = b;
        fromAccount = from;
        maxAmount = max;
        this.testingSemaphore = testingSemaphore;
    } // end TransferThread

    @Override
    public void run() {

        for (int i = 0; i < 100; i++) {
        	while (bank.shouldTest()) {
                //System.out.printf("%-30s yielding should test : %s\n", Thread.currentThread().toString(), bank.shouldTest());
        		yield();
        		if (!bank.isOpen()) {
                    break;
        		}
        	}
        	testingSemaphore.acquireUninterruptibly();
                int toAccount = (int) (bank.getNumAccounts() * Math.random());
                int amount = (int) (maxAmount * Math.random());
                bank.transfer(fromAccount, toAccount, amount);
                bank.countTransaction();
                //System.out.printf("%-30s ran\n", Thread.currentThread().toString());
        	testingSemaphore.release();
        }

        //this.bank.closeBank();
        //  System.out.println("**closing a bank account");
        System.out.printf("%-30s Account[%d] has finished with its transactions.\n", Thread.currentThread().toString(), fromAccount);
        this.bank.closeBank();

    } // end run
} // end TransferThread
