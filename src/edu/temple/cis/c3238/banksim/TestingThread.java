package edu.temple.cis.c3238.banksim;

/**
 *
 */
public class TestingThread implements Runnable {
    private Account[] accounts;
    private final int initialBalance;
    private final int numAccounts;
    
    public TestingThread(Account[] accounts, int initialBalance, int numAccounts) {
        this.accounts = accounts;
        this.initialBalance = initialBalance;
        this.numAccounts = numAccounts;
    }
    
    @Override
    public void run() {
    	
    	Account[] accounts2 = this.accounts.clone();
    	
    	int totalBalance = 0;
        for (Account account : accounts2) {
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
}
