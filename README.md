# BankSim

## General Design 

This is the design of the BankSim before we made any modifications on it. 
The race condition can occur when two threads are both inside the deposit method at the same time. For example they both could see the balance at 5000 when they enter then each independently add their own amount.  Instead of adding the amounts together  one overwrites the other.


![UML sequence Diagram](https://github.com/3296Spring2020/banksim-multithreading-02-schultz-essel-teameffort/raw/coolBranch/BankSim%20(3).png)


![UML Sequence Solution](https://raw.githubusercontent.com/3296Spring2020/banksim-multithreading-02-schultz-essel-teameffort/coolBranch/UML%20Solution.png?token=AI265SNORDNFWT3M5CP6NHC6L2ISA)

- _thread1 begins the transfer() method on the Bank b which results in a wait (Task 4)_
- _thread1 returns from the waut because another thread has finished a deposit (Task 4)_
- _thread1 increment()s the atomic integer (Task 3)_
- _thread2 tries to run the test() method from Bank b (Task 3)_
- _thread2 waits within test method because the atomic integer is >0 signalling one thread has not completed it's full transfer (Task 3)_
- _thread1 finishes transfer and decrement()s the atomic integer (Task 3)_
- _thread2 wakes from busy waiting and executes the rest of test() (Task3)_
- _thread1 finishes it's execution loop and close()s b (Task 5)_
- _thread2 (and all other threads) call isOpen() and get a false causing them to break their execution loop (Task 5)_

## Requirements
There were multiple requirements for this project. The primary goal was to reverse engineer a simple Banking simulation that utilized 
a multi threaded approach. This software while appearing to work correctly actually contained several race conditions. We were to identify these race conditions and document them with a UML sequence diagram. Additionally we were to use the Trello website for learning basic SCRUM techniques for project management. 
We had five tasks :
 - identify initial race conditions and create UML sequence diagram to document it.
 - solve the above race condition
 - solve race condition when testing occurs at intervals instead of just once at the end
 - create a wait/notify solution so accounts can wait for more funds to transfer if they run out
 - solve deadlock issue by closing bank once one thread finishes
 
 Moreover a high level of parallelism must be maintained. That is it would not suffice to only have one account transferring at a time.
Tasks 2 and 3 needed to be completed by different people. 

## Team work
We exchanged phone numbers and schedules early on in the project to facilitate communication.
On three different occasions we met up to work on tasks collaboratively. All other time we communicated via text and trello board.
### Zach Essel 
We originally a split up the tasks with me taking the evens and Brahm taking the odds.  We did task 1 and 2 in lab the first day.  Then Brahm started to work on Task 3 while I worked on task 4 simultaneously.  This seemed like it would work well until we found our solutions were incompatible after I first merged them.  From that point on we met up to work together on the solution for a few hours on several different days.  We didn't push changes that often because they rarely lived past testing, when we would then move on to something else.  Brahm programmed solution attempts that included semaphores and reentrant locks.  I tried solutions that included counting semaphores and barriers.  I'm not sure if any of these made it to a formal commit.  When we came up with our final solution together, a combination of busy waiting on the test and a counter, Brahm immediately began testing it.  He added yields, and multiple trials of increased transactions.  He also made the UML sequence diagram to represent the original race condition, both it's rough draft and final copy.  I created a UML diagram to show the execution of our solution.  Brahm also did the majority of work on the readme and edited the Trello board.

In the end my contributions were: 
- Creating the Trello board
- Task 2
- some of Tasks 3-5
- UML Diagram
- some of the readme
### Abraham Schultz
- created initial UML sequence diagram for task 1.
- In collaboration with Zach helped implement mutual exclusion for task 3.
- Implemented the close bank functionality for task 5.
- Wrote much of the Readme 



## Testing
The main purpose of this project was to fix the race condition relating to the testing method. In essence we had to test that the testing method was working correctly. We also had to check our assumptions about the bank and account classes that were provided to us.
We took two different approaches to each of these. We used a manual testing approach for the race conditions. And an automated approach for the individual class methods. We tested the methods for the bank and account classes using Junit and unit testing. For testing if we solved the race conditions we would run the program many more times than the required 10,000 times. We ran it as many as 1,000,000 times per run. Beyond supplying a mathematical proof, this is as close as we could reasonably come to showing that the mutual exclusion was working. When testing the intermittent test method Zach built the mutual exclusion and Abraham tested it. 
We did think we solved the race condition when running the transfer thread only 10,000 times. But upon running it 1,000,000 times we found that the balance changed. We then had to do some bug fixes to our increment and decrement methods for the atomic integer.


