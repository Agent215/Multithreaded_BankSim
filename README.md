# BankSim

## General Design 

This is the design of the BankSim before we made any modifications on it. 

![UML sequence Diagram](https://github.com/3296Spring2020/banksim-multithreading-02-schultz-essel-teameffort/raw/master/BankSim.png)

## Requirements
There were multiple requirements for this project. The primary goal was to reverse engineer a simple Banking simulation that utilized 
a multi threaded approach. This software while appearing to work correctly actually contained several race conditions. We were to identify these race conditions and document them with a UML sequence diagram. Additionally we were to use the Trello website for learning basic SCRUM techniques for project management. 
We had five tasks :
1 - identify initial race conditions and create UML sequence diagram to document it.
2 - solve the above race condition
3 - solve race condition when testing occurs at intervals instead of just once at the end
4 - create a wait/notify solution so accounts can wait for more funds to transfer if they run out
5 - solve deadlock issue by closing bank once one thread finishes

Tasks 2 and 3 needed to be completed by different people. 
## Team work

- Zach Essel 
- Abraham Schultz

