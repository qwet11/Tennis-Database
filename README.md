# Tennis-Database

This document will explain the changes/additions I made to the assignment:

> In the TennisMatchContainer class, I decided to use the JCF class LinkedList.
> This is because I expect a lot of match inserts and removals since there are generally more matches than players, and 
> the LinkedList has faster inserts and removals than an ArrayList. 

> In TennisPlayerContainer and TennisDatabase class/interface, I changed the exceptions from only unchecked exception to unchecked and checked exceptions so that I can distinguish > between when there is no player with the id and when there is no match with the player.

> In TennisPlayerContainer class, I added the getPlayerNode(String id) and getPlayerNodeRec(TennisPlayerContainerNode currNode, String id) method so that I can easily find the node when I knew the id. 

> In TennisPlayerQueue class, I added the resizeQueue() method, which doubles the size of the queue and resets the frontIndex to 0, so that I can easily resize the queue. 

> In TennisPlayerContainerIterator class, I added the inorder(), preorder(), reverseInorder(), and postOrder() methods so that the iterator will traverse the BST in different ways.

> In the main class (Assignment2), I added a feature which ensures that all of the dates are realistic. So, the months will always be 1-12, the days 1-31, and the years 7900-2100.

> In the main class (Assignment2), I added a feature which shows the user what they inputted and asks if it is correct. If it is not, the input process repeats. If it is, the code continues. The user also has the option to exit to the main menu. 

> I added 4 other tests file in this folder: 
> The 2nd input file is meant to mainly test the players input (that everything is sorted and that it can handle "large" amount of players). 
> The 3rd input file is meant to mainly test the match input (to test if it can sort the dates properly and handle a long score).
> The 4th input file is meant to test that the program requires all of the players to be stated before the matches
> The 5th input file is meant to test that the program denies incorrect data
