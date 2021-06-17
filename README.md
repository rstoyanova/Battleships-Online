# Battleships Online :anchor:

## Game overview

BattleshipsOnline is a turn-based strategy guessing game for two players. 

### Rules

-	The game is played by two players.
-	Each player has a board 10x10. The rows are marked with the letters A to J, and the columns are numbered 1 to 10.
-	Each player has also ships with a certain length and count:
    	  [ship-type] | length | #
          destroyer   |     2  | 1
          submarine   |     3  | 2
          battleship  |     4  | 2
          carrier     |     5  | 1 
-	At the beginning of the game, each player places his ships on the field, and they can only be in a straight line (horizontally or vertically)
-	After the preparations starts the game.
-	The goal of each player is to hit his opponent's ships, as the players take turns and each is entitled to one shot per turn.
    -	The player on the move gives the coordinates of the cell he is shooting at, and in response receives an indication of whether he has hit or not, and if he has hit, whether the ship has been sunk.
    -	In order for a ship to sunk, all its cells must be hit.
-	The game is over when one of the players runs out of ships.

### Game Server

The Server has the following functionalities:

-	Create a game.
-	List all currently active games with information about the number of players in it.
-	Join an already created game.
-	Save the status of the game currently being played.
-	Restore a saved game.
-	Delete a saved game.

### Game Client

The client server has the following console interface

-	Loging

```bash
$ java BattleshipsClient.java --username pesho

#################################################
                 Possible commands
------------------------------------------------
       create-game [name]
       join-game [name]
       load-game [name]
       saved-games

```
-	Creating a game
```bash
menu> create-game my_game
Created game my_game, players 1/2
```

-	Joining a game

```bash

menu> saved-games
| NAME     | CREATOR | STATUS      | PLAYERS |
|----------+---------+-------------+---------|
| my-game  | pesho   | pending     | 1/2     |
| 1v1      | gosho   | in progress | 2/2     |

menu> join-game my-game
Joined game "my-game"
PLAYERS: 2/2, type "start" to start the game
```
-	Enter a move

```bash

       YOUR BOARD
   1 2 3 4 5 6 7 8 9 10
   _ _ _ _ _ _ _ _ _ _
A |_|*|_|-|_|_|_|*|*|_|                         Legend:
B |_|*|_|_|_|_|_|_|_|_|				* - ship field
C |_|*|_|_|_|_|_|_|_|_|				X - hit ship field
D |_|X|_|_|*|*|*|_|-|_|				О - hit empty field
E |_|_|_|_|_|_|_|_|_|_|
F |_|_|-|_|_|_|-|_|_|_|
G |_|_|_|_|_|_|_|_|_|_|
H |_|*|_|_|_|X|X|X|X|_|
I |_|*|_|_|_|_|_|_|_|_|
J |_|*|_|_|_|_|_|_|_|_|


      ENEMY BOARD
   1 2 3 4 5 6 7 8 9 10
   _ _ _ _ _ _ _ _ _ _
A |_|_|_|_|_|_|_|_|_|_|
B |_|_|_|_|_|_|_|_|-|_|
C |_|-|_|_|_|_|-|_|_|_|
D |_|_|_|_|_|_|_|_|_|_|
E |_|_|_|-|_|_|_|_|-|_|
F |_|_|_|_|_|_|_|_|_|_|
G |_|_|_|_|_|_|_|X|_|_|
H |_|_|X|X|X|_|_|X|_|_|
I |_|_|_|_|_|_|_|X|_|_|
J |_|_|_|_|_|_|_|X|_|_|

pesho's last turn: D9
Enter your turn:
```

## Code overview
The game is implemented with Client-server architecture.  
*Written in Java 15 using IntelliJ IDEA.*  
-  Client  
    The client has only system for receiving and sending messages.  
- Server  
    There are some main parts    
    - Command center  
    Here is everything in relation to the commands send from the users. Implementation with Command design pattern.  
    - Game Battleships  
    The whole implementation of the game with it's main components:  
        `Board` contains a matrix of fields representing the game's board.  
        `Game engine` implements the logic of the game and every action that may take place on the board.  
        `Ships` implementation of each ship with its specifics for convenience.  
    - Server  
    Used NIO for establishing and maintaining the communication between the server and clients.  
        `Server data` contains all data collected while the server is running (f.e. logged players, saved games, state of the games,...)  
        `Saving system` system for saving games or the whole server data in binary files. 
- Tests  
    Unit tests using JUnit 4  


### Hierarchy 

```bash
src
╷
├─ commandcenter
|  ├─ command
|  |    ├─ Command
|  |    ├─ Attack
|  |    ├─ CreateGame
|  |    └─ (...)
|  ├─ parser
|  |    ├─ CmdParser
|  |    ├─ FieldParser
|  |    └─ ShipParser
|  ├─ Controller
|  └─ Executor
├─ exceptions
|  ├─ game.battleships
|  |    └─ (...)
|  └─ server.data
|       └─ (...)
├─ game.battleships
|  ├─ board
|  |    ├─ BattleshipsBoard
|  |    ├─ BattleshipsBoardField
|  |    ├─ Field
|  |    └─ Grid
|  ├─ gameengine
|  |    ├─ ActiveBattleshipsGame
|  |    ├─ BattleshipsGame
|  |    └─ Game
|  └─ ships
|       └─ (...)
├─ server
|  ├─ data
|  |    ├─ actions
|  |    |    ├─ Loader
|  |    |    └─ Saver
|  |    ├─ ServerStorage
|  |    └─ Stogare
|  ├─ BattleshipsServer
|  └─ RequestHandler
test
   └─ (...)
```


