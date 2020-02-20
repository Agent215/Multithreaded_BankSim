package edu.temple.cis.c3238.banksim;

public class TestingThread extends Thread {
	
    private Account[] accounts;
    private int initialBalance;

    public TestingThread (Account[] accounts, int initialBalance) {
    	this.accounts = accounts;
    	this.initialBalance = initialBalance;
    }
    
	@Override
	public void run() {
    	int totalBalance = 0;
        for (Account account : accounts) {
            System.out.printf("%-30s %s%n", 
                    Thread.currentThread().toString(), account.toString());
            totalBalance += account.getBalance();
        }
        System.out.printf("%-30s Total balance: %d\n", Thread.currentThread().toString(), totalBalance);
        if (totalBalance != accounts.length * initialBalance) {
            System.out.printf("%-30s Total balance changed!\n", Thread.currentThread().toString());
            System.exit(0);
        } else {
            System.out.printf("%-30s Total balance unchanged.\n", Thread.currentThread().toString());
        }
	}
}