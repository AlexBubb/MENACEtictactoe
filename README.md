# MENACEtictactoe
A digital recreation of MENACE

A full explination of the Machine Algorithm implemented:\n
Please watch this video by Vsause that will better explain the theroritical algorithim with an exelent physical example: https://youtu.be/sw7UAZNgGg8?si=x7EAdTBSKi88Hiiz
There are three parts to my program: 
Creating the boxes (search function)
Removing bad moves (learn function)
Playing the game (play function)
The search function works by creating a ternary counter. It is easy to validate if a ternary number is a valid game board because if its valid, then it has one more 1 than 2. Then it creates a box for the valid game board and contiunes when it reaches the maximum number a 9 digit ternay number can represent we know all possible game boards have been found.
The learn function is much more interesting. Unlike the video it dosen't learn by playing but learns by evaluating each move it can make. For instance take this game boad
102
021
001
the program will find each place it can make its next move and find that one of them leads to a win
102
021
201
it will then remove all beads that aren't the winning move and proceed to the next box.
Now examine this board:
102
021
100
First it will find that no move can win the game then will make a move and examine the ways player one might respond
122
021
100
It will find that player one will most likley make this move winning the game
122
121
100
Therefore placing its move in the top middle leads to a loss and will not make that move.
Eventually it will find that all moves will lead to a loss except:
102
221
100
This method works great but tic tac toe raieses and interesting issue:
101
020
102
What will the computer do here?
With the current algorithm it will crash because it determined that there is no move it can make. Therefore we needed an addition to the algorithm that can deal with this. The solution to this problem that I found was to create an array called banned. When a box loses all of its beads its top board is added to banned. The when checking the moves player one can make my program checks if a move they can make wins the game OR is in banned. Now banned boards can no longer be reached because they are treated the same. Now what might happed if it determines that the periviouse example is banned is this:
Example Board:
101
000
002
A possible move for player two
101
020
002
But wait player one can...
101
020
102
And thats banned so we can't make that move
now from this board all other moves are then removed until its only option is:
101
200
002
This solution works great but has one issue. If a banned board can be reached from a box that has already been evaluated then not all instances of the banned board can be removed. This creates a need for recusion where we need to start again with an updated banned list. The problem is this siginficantly incresses the wait time as we need to restart for every new banned board. There is one saving grace in that for many boards the number of moves avalible has already been filtered leaving the time it takes to catch up to where it was faster on the seccond runthrough. The good news is because we have to go back if there is a senario where player one can force player two into a banned box, because that move gets removed and the box nolonger has any moves to make it itself gets banned.
The result of this algorithm is a set of boxes that can only make moves to perfectly play tic tac toe and force a win or tie every single time.
A couple of final notes:
across the program you will find references to and "id" of each board. It was helpful for debuging and coding to have a way to number a given box. An id for a board is computed by treating a board as a ternary number so:
101
201
002
can be read as: 101201002
this number is then converted into decimal to find the id: 7805 of the given board.
The interesting thing about this algorithm is that it can be applied to any game so long as you have the computing power necesary to map and solve the game. For instance this could be used for chess but searching every estimated 10^21 possible moves and then individualy going back and evaluating every possible move for each player.
Credits: All of the code is this project was coded by me with the occansial reference to documentation or help from the internet on how to do a specific list comprehention. Quite clearly the algorithm was not invented by me but is creditied to Donald Michie where he created the true phisical representation of the matchboxes to play tic tac toe called MENACE. My learning algorithm and adaptation of the baning system is entirely my own although I don't know if it has been independantly discovered elsewhere. Finaly thanks to Kevin from VSAUSE for creating his great video teaching and explaining the algorithm and inspireing this project.
