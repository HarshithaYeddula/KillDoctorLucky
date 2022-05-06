# CS 5010 Semester Project

This repo represents the coursework for CS 5010, the Fall 2022 Edition!

**Developer-1:** Ganesh Prasad Shivakumar

**Developer-2:** Harshitha Yeddula

**Email:** prasad.g@northeastern.edu

**Preferred Name:** Ganesh & Harshitha


Please find the design of this project here: [UML](https://github.com/khoury-22sp-cs5010-jump/cs5010-project-ganeshparsads/blob/main/res/runRelatedFiles/MileStone-4.pdf) 



### About/Overview

Give a general overview of the problem and how your program solve the problem
Design/Model Changes



### List of Features

The features that are added in this mileStone are:

#### MileStone-1:

1. Graphical representation for the world is generated based on the input file given.
2. Creates spaces/rooms in the world.
3. [Finds the neighbors of each space who are sharing wall.](https://github.com/khoury-22sp-cs5010-jump/cs5010-project-ganeshparsads/blob/main/res/runRelatedFiles/Neighbor%20%26%20Overlap%20Logic.pdf)
4. Checks for overlapping of spaces along with this checking input file format, number of spaces, number of weapons, max co-ordinate values.
5. Target character creation and tracking space where its receding.
6. Movement of the target in ordered list of spaces.
7. Check on the health of target.
8. Tracking Weapon to space mapping.

#### MileStone-2:

In this mileStone we focused mainly on adding synchronous controller which handles all the below featurs.

1. Command Controller which is designed based on Command Designed patters.
2. Display information about a specified space in the world.
3. Create a graphical representation of the world map and provide the ability to save the graphical representation to a file as a PNG file.
4. Add a human-controlled player to the game.
5. Add a computer-controlled player to the game.
6. Move a player.
7. Allow a player to pick up an item.
8. Allow a player to look around.
9. Display a description of a specific player.

#### MileStone-3:

In this mileStone we focused mainly on adding synchronous controller which handles all the below featurs.

1. Command Controller which is designed based on Command Designed patters.
2. Display information about a specified space in the world.
3. Create a graphical representation of the world map and provide the ability to save the graphical representation to a file as a PNG file.
4. Add a human-controlled player to the game.
5. Add a computer-controlled player to the game.
6. Move a player.
7. Allow a player to pick up an item.
8. Allow a player to look around.
9. Display a description of a specific player.

#### MileStone-4:

In this mileStone we focused mainly on view part.

1. Start by showing an about screen that welcomes the user to the game. In addition to the welcome message, this screen should provide credits to those who worked on the assignment.
2. Provide an option for starting a new game with a new world specification, starting a new game with the current world specification, and quitting the game through a JMenu (Links to an external site.).
3. The JFrame that you use should be able to be resized and still present a reasonable layout as long as the size if the window is at least 300 x 300. To accomplish this, embrace Java's layout managers to place components on the screen.
4. When the game begins, the majority of the program's screen should show the graphical representation of the world along with a graphical representation of the the target character (but not the pet) and any players that have been added to the game. Each of these graphical representations should be overlaid on top of the graphical representation of the world in such a way to show their current location. Your program should support graphical representations of the target, and up to 10 players (in any combination of human and computer).
5. Any world that is bigger than the area allocated to it on the screen should provide the ability to scroll the view.
6. Clearly indicate whose turn it is on the screen along with any information about where they are in the world.
7. Provide an option for getting the player's description by clicking on the player's graphical representation.
8. Provide an option for moving the player though the world using a mouse click. A player should not be able to make invalid moves.
9. Provide an option for the player to pick up an item in the world by pressing a key on the keyboard.
10. Provide an option for the player to look around the world by pressing a key on the keyboard.
11. Provide an option for the player to make an attempt on the target character's life by pressing a key on the keyboard.
12. Provide a clear indication of the results of each action a player takes.


### How to Run

To run the jar file please use below, where first args represent mansion text file and second args
represent maxTurns

```bash
java -jar res/runRelatedFiles/cs5010-final-project-harshitha_ganesh_team.jar res/mansion.txt 10

```


### How to Use the Program

Use instructions provided here for using jar files: [Jar Usage](https://docs.oracle.com/javase/tutorial/deployment/jar/appman.html)


For invoking World implementation please use the below package:

Once the jar files are included, please use below statement to import **GameController**, **GameWorld**, **RandomGenerator** interfaces.

```java

import gameworld.controller.CommandController;
import gameworld.controller.GameController;
import gameworld.service.GameWorld;
import gameworld.service.KillDoctorLuckyImpl;
import gameworld.service.RandomGenerator;
import gameworld.view.GamePlayView;
import gameworld.view.GamePlayViewImpl;

```


### Example Runs

List any example runs that you have in res/ directory and provide a description of what each example represents or does. Make sure that your example runs are provided as *plain text files*.

Output of example runs are in res/runRelatedFiles/

#### MileStone-1:

1. Create world from the text file.
2. Storing space & weapon list inside the world.
3. Displaying neighbors information of a space.
4. Displaying Space information with weapons along with neighbors.
5. Target movement.
6. Building graphical representation.

#### MileStone-2:

1. adding a human-controlled player to the world
2. adding a computer-controlled player to the world
3. the player moving around the world
4. the player picking up an item
5. the player looking around
6. taking turns between multiple players
7. displaying the description of a specific player
8. displaying information about a specific space in the world
9. creating and saving a graphical representation of the world map to the current directory
10. demonstrates the game ending after reaching the maximum number of turns

#### MileStone-3:

I have added 3 test runs:

1. human-dry-run.txt: 
    > a human-player making an attempt on the target character's life
    >
    > a human-player winning the game by killing the target character
    >
    > the humamn-player moving the target character's pet

2. comp-dry-run.txt:
    > a computer-controlled player making an attempt on the target character's life.
    >
    > a computer-controlled player winning the game by killing the target character
    >
    > the computer player moving the target character's pet

3. lookaround-dfs-pet.txt:
    > the target character's pet effect on the visibility of a space from neighboring spaces
    >
    > the target character escaping with his life and the game ending
    >
    > if you implement the extra credit be sure that you include evidence of this in 
    >your example run.

#### MileStone-4:
I have added 3 test runs from command gui driver

1. human-dry-run.txt: 
    > a human-player making an attempt on the target character's life
    >
    > a human-player winning the game by killing the target character
    >
    > the humamn-player moving the target character's pet

2. comp-dry-run.txt:
    > a computer-controlled player making an attempt on the target character's life.
    >
    > a computer-controlled player winning the game by killing the target character
    >
    > the computer player moving the target character's pet

3. lookaround-dfs-pet.txt:
    > the target character's pet effect on the visibility of a space from neighboring spaces
    >
    > the target character escaping with his life and the game ending
    >
    > if you implement the extra credit be sure that you include evidence of this in 
    >your example run.

Using view we ran dry runs, for which we have kept the screenshots in the *res/DryRunImages* folder.

### Design/Model Changes

Changes that are introduced during implementation.

#### MileStone-1:
1. With my earlier changes I have added additional layer on space, target, weapons. That are removed as they are redundant.
2. Moved World implementation from Singleton to Facade pattern.
3. Storing neighbors inside spaces based on SOLID property.
4. Storing space where target is present. 

#### MileStone-2:
1. Space model will be storing additional player information.
2. GameWorld model will be storing additinal data such as Players info, Player's Turn, MaxTurns info.
3. Added new functions to accomodate every commands of controller in both Space and GameWorld.

#### MileStone-3:
1. Pet DFS path will be stored inside gameWorld object and updating adjacency map is done from gameworld.
2. Space model will be storing pet information with the whole object.
3. Keeping space display command, as it is without removing so that players when they are starting 
the game can check which space has more weapons.

#### MileStone-4:
1. Introduced view facade which was not part of our earlier design.
2. Added new GUI driver based on the requirement.
3. Added new methods to move the decisions from the view to controller whenever there is interaction needed.

### Assumptions

List any assumptions that you made during program development and implementations. Be sure that these do not conflict with the requirements of the project.

#### MileStone-1:
> X1 & X2 and Y1 & Y2 coordinates of a space are assumed to be different.

> Multiple weapons/items can be in one space but items are assumed to be not supposed to be shared across spaces.

> Target is assumed to not move after reaching the MAX indexed space.

#### MileStone-2:
> Player cannot share the name of target.
> Player names should be unique.
> Weapnom names should be unique.
> Space names should be unique.
> Game assumed to have three stages:
>> Pre game: Stage where game setup is done by adding below commands to the world:
>>> Create GUI
 
>>> Add Human Player

>>> Add Computer Player

>>> Start Game

>>> Display World info

>>> Display space info

>> Run Game: Stage where players actual participation comes into picture, with below commands:
>>> Move Player
>
>>> Look Around
>
>>> Pick an item
> 
>> Post game: After running stage ends, players can view the final world and spaces to summarize 
>the game. 
Stages don't share commands is the assumption.

#### Milestone-3:
> Computer player will only attack if it's not been seen by other players, or it as pet in its room.
>
> Computer player item will never go unsuccessful, as it'll check all the cases before attacking.
> 
> Once a player is used the weapon, that weapon will be taken out of the gameworld 
> even if its successfull or not.
>
> There will be only one pet and target in the play and supporting the same. 

#### Milestone-4:
> More than 3 player in the space. As boxes are scaled in such a way that they can support only 3 players.
> Pet will be shown iff if its present with turn player.
> All the players will be shown at all time.

### Limitations

What limitations exist in your program. This should include any requirements that were *not* implemented or were not working correctly (including something that might work some of the time).

#### MileStone-1:

1. There might be missing overlap conditions.
2. As we don't have any implementation or requirements on weapons or items. There are fewer tests on items.
3. Since we don't have instructions on how to use weapons, there are no functionalities implemented to reduce targets power. 

#### MileStone-2:
Due to above assumptions, there are few limitations:

1. Players cannot be added once the game switched from Pre-Game to Run-Game.
2. Players and Target cannot share the same name.

#### Milestone-3:

1. Only one pet is supported.
2. Players cannot make sure their attack and lookaround at the same time.
3. Computer player attacks will not be unsuccessful.
4. There is no public function exposed from the gameworld for pet movement using dfs, 
as it's internal logic inside the model.

#### Milestone-4:
> Cannot fit more than 3 player, 1 target and pet inside a sqaure.
> 
> User experience is somewhat comprimized as compared initial design because of limitations of java swing.

### Citations

- Graphics2D (Java Platform SE 7 ). (n.d.). Graphics. https://docs.oracle.com/javase/7/docs/api/java/awt/Graphics2D.html

- How to Write a Mouse Listener (The JavaTM Tutorials > Creating a GUI With Swing > Writing Event Listeners). (n.d.). Mouse. https://docs.oracle.com/javase/tutorial/uiswing/events/mouselistener.html

- How to Write a Key Listener (The JavaTM Tutorials > Creating a GUI With Swing > Writing Event Listeners). (n.d.). Key. https://docs.oracle.com/javase/tutorial/uiswing/events/keylistener.html

-MVC Framework - Introduction. (n.d.). MVC. Retrieved November 18, 2021, from https://www.tutorialspoint.com/mvc_framework/mvc_framework_introduction.htm

-GeeksforGeeks. (2020, December 16). Shortest path in an unweighted graph. Retrieved November 1, 2021, from https://www.geeksforgeeks.org/shortest-path-unweighted-graph/

> https://dzone.com/articles/command-design-pattern-in-java.
> 
> https://javarevisited.blogspot.com/2016/05/command-design-pattern-in-java-example-code.html#axzz7Mo0mYhyY
>
> https://stackabuse.com/graphs-in-java-depth-first-search-dfs/
