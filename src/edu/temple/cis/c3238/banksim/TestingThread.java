/*
 Testing thread to run during bank transfer process
 */
package edu.temple.cis.c3238.banksim;

/**
 *
 * @author Abraham (brahm)
 */
class TestingThread extends Thread {

    private Bank bank;

    // constructor 
    public TestingThread(Bank b) {

        this.bank = b;
    }

    @Override
    public void run() {

        boolean cond = this.bank.getIsOpen();
        while (cond) {
            // System.out.println("**********" + this.bank.getIsOpen());
            cond = this.bank.getIsOpen();

            if (this.bank.shouldTest()) {
                this.bank.test();

            }
        }

    } // end run

}
