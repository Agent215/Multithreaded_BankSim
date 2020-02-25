# BankSim

## General Design 

This is the design of the BankSim before we made any modifications on it. 

![UML sequence Diagram](https://github.com/3296Spring2020/banksim-multithreading-02-schultz-essel-teameffort/raw/master/BankSim.png)

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
### Abraham Schultz
- created initial UML sequence diagram for task 1.
- In collaboration with Zach helped implement mutual exclusion for task 3.
- Implemented the close bank functionality for task 5.
- Wrote much of the Readme 



## Testing
The main purpose of this project was to fix the race condition relating to the testing method. In essence we had to test that the testing method was working correctly. We also had to check our assumptions about the bank and account classes that were provided to us.
We took two different approaches to each of these. We used a manual testing approach for the race conditions. And an automated approach for the individual class methods. We tested the methods for the bank and account classes using Junit and unit testing. For testing if we solved the race conditions we would run the program many more times than the required 10,000 times. We ran it as many as 1,000,000 times per run. Beyond supplying a mathematical proof, this is as close as we could reasonably come to showing that the mutual exclusion was working. When testing the intermittent test method Zach built the mutual exclusion and Abraham tested it. 
We did think we solved the race condition when running the transfer thread only 10,000 times. But upon running it 1,000,000 times we found that the balance changed. We then had to do some bug fixes to our increment and decrement methods for the atomic integer.


