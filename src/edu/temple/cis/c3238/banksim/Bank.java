package edu.temple.cis.c3238.banksim;

/**
 * @author Cay Horstmann
 * @author Modified by Paul Wolfgang
 */
class Bank {

    public static final int NTEST = 10;
    private int[] accounts;
    private long ntransacts = 0;
    private int initialBalance;
    private int numAccounts;

    public Bank(int numAccounts, int initialBalance) {
        this.initialBalance = initialBalance;
        this.numAccounts = numAccounts;
        accounts = new int[numAccounts];
        for (int i = 0; i < accounts.length; i++) {
            accounts[i] = initialBalance;
        }
        ntransacts = 0;
    }

    public synchronized void transfer(int from, int to, int amount) {
        if (accounts[from] < amount) {
            return;
        }
        int fromOldBalance = accounts[from];
        Thread.yield();
        int fromNewBalance = fromOldBalance - amount;
        accounts[from] = fromNewBalance;
        Thread.yield();
        int toOldBalance = accounts[to];
        int toNewBalance = toOldBalance + amount;
        accounts[to] = toNewBalance;
    }

    public void test() {
        int sum = 0;
        for (int i = 0; i < accounts.length; i++) {
            System.out.printf("Account[%d] %d%n", i, accounts[i]);
            sum += accounts[i];
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
