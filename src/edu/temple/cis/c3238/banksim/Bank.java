package edu.temple.cis.c3238.banksim;

/**
 * @author Cay Horstmann
 * @author Modified by Paul Wolfgang
 */
class Bank {

    public static final int NTEST = 10;
    private Account[] accounts;
    private long ntransacts = 0;
    private int initialBalance;
    private int numAccounts;

    public Bank(int numAccounts, int initialBalance) {
        this.initialBalance = initialBalance;
        this.numAccounts = numAccounts;
        accounts = new Account[numAccounts];
        for (int i = 0; i < accounts.length; i++) {
            accounts[i] = new Account(i, initialBalance);
        }
        ntransacts = 0;
    }

    public void transfer(int from, int to, int amount) {
        accounts[from].waitForAvailableFunds(amount);
        if (accounts[from].withdraw(amount)) {
            accounts[to].deposit(amount);
        }
    }

    public void test() {
        int sum = 0;
        for (int i = 0; i < accounts.length; i++) {
            System.out.println(accounts[i]);
            sum += accounts[i].getBalance();
        }
        System.out.println(" Sum: " + sum);
        if (sum != numAccounts * initialBalance) {
            System.out.println("Money was gained or lost");
        } else {
            System.out.println("The bank is in balance");
        }
    }

    public int size() {
        return accounts.length;
    }

}
