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
public class Account {

    private volatile int balance;
    private final int id;
    private Thread th;
    private Bank bank;

    public Account(int id, int initialBalance, Bank bank) {
        this.id = id;
        this.balance = initialBalance;
        this.bank = bank;
    }

    public int getBalance() {
        return balance;
    }

    public synchronized boolean withdraw(int amount) {

        if (amount <= balance) {
            int currentBalance = balance;
            Thread.yield(); // Try to force collision
            int newBalance = currentBalance - amount;
            balance = newBalance;
            return true;
        } else {
            return false;
        }

    }

    public synchronized void deposit(int amount) {
//        while (this.bank.shouldTest()) {
//
//            try {
//                System.out.println("**should be waiting now");
//                System.out.println("**should be testing now");
//                this.wait();
//            } catch (InterruptedException ex) {
//                Logger.getLogger(Account.class.getName()).log(Level.SEVERE, null, ex);
//            }
//
//        }
        int currentBalance = balance;
        Thread.yield();   // Try to force collision
        int newBalance = currentBalance + amount;
        balance = newBalance;

    }

// get the current thread 
    public synchronized Thread getThread() {
        th = Thread.currentThread();
        return th;
    }

    @Override
    public String toString() {
        return String.format("Account[%d] balance %d", id, balance);
    }
}
