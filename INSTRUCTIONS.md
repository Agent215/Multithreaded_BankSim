# BankSim

The purpose of this lab assignment is to observe and resolve the race condition in a multi-thread program. You are given the initial source code of a bank fund transferring simulation program as described in the class. The initial program does not have any means to protect the critical part of the code to prevent race condition. So race condition always happens when it runs. Here are the tasks you have to do (in that order) and their requirements.

Task 1. Run the initial program as is to observe race condition and explain how a race condition may occur. Draw a simple  UML sequence diagram to support your explanation. Add your explanation and the sequence diagram to [README.md](README.md)

Task 2. Implement protection code to resolve the race condition issue. We may use either synchronized object locks or ReentrantLock class to prevent two treads from having access the same global variables simultaneously. You solution must allow the bank to transfer multiple fund between unrelated accounts, e.g. from account [2] to account [5] and from account [3] to account [7] at the same time.

Task 3. The test method in the bank simulation program implemented Task 2 may still reports an error. This is because while the test method is summing the amounts in each account in one thread, transfers are still taking place in other threads. This is another source to a race condition. Refactoring the method of testing summing the accounts in each account in a new and separate thread. Provide code protection so that the newly-created testing thread and any other amount transferring threads are running exclusively, i.e. the testing thread starts with a signal to all transferring threads, waits for all the transferring threads finishing the current transferring, and then takes over the testing task while all the transferring threads are waiting until the test is done.

Task 4. The initial code does not allow an account to transfer out fund if the transferring amount is greater than the account balance. Implement a wait/notify solution to defer the transfer until the account balance becomes greater than the transferring amount (assume some the account will receive the fund later).

Task 5. Deadlock condition may occur (very rare) if one thread finishes all the fund transfers and exit. Implement a solution in which all thread stop fund transferring (bank is close) whenever one tread completes its fund transfers. This will solve the deadlock condition issue.

Note that a solution that does not permit un-related transfers to take place simultaneously is not acceptable.
