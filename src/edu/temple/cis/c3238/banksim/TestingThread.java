package edu.temple.cis.c3238.banksim;

import java.util.concurrent.Semaphore;

public class TestingThread extends Thread {
	
    private Bank bank;
    private int naccounts;
    private Semaphore testingSemaphore;

    public TestingThread (Bank bank, int naccounts, Semaphore testingSemaphore) {
    	this.bank = bank;
    	this.naccounts = naccounts;
    	this.testingSemaphore = testingSemaphore;
    }
    
	@Override
	public void run() {
		while(bank.isOpen) {
			
			if (bank.getLastTest() != bank.getNumTransactions()) {
				testingSemaphore.acquireUninterruptibly(naccounts);
					if (bank.shouldTest()) {
						bank.test();
						bank.resetTestBoolean();
						bank.updateLastTest();
						bank.countTransaction();
					}
					testingSemaphore.release(naccounts);
			} else {
				//bank.resetTestBoolean();
                System.out.printf("%-30s yielding should test : %s\n", Thread.currentThread().toString(), bank.shouldTest());
                //System.out.printf("%d %d\n", bank.getLastTest(), bank.getNumTransactions());
				yield();
			}
		}
	}
}