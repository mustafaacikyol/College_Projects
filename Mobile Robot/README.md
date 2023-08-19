## Mobile Robot

By overcoming the obstacles in front of a robot that moves according to certain rules, it reaches the desired target. It is expected to design a game that allows Three problems in the game
needs to be resolved. Object-oriented programming and data for solving problems
structures are expected to be used.

In the project, you are expected to find solutions to the two problems described below:

PROBLEM 1:
In this problem, you can send the robot to the target given on the grid (grid) without getting stuck in the obstacles.
It is expected that you will deliver it as soon as possible and by the shortest way. The robot is not the whole grid,
You must ensure that he reaches the destination by traveling only the necessary roads.

Step 1: You need to create a square grid area of the desired dimensions.
Step 2: Obstacles and walls should be placed on the grid. Grid size, number of obstacles, and
The location information content of the obstacles will be taken from a text file in matrix format. this text
application from a url address that will be given to the file in advance (it will be shared over e-support)
Grid and obstacle structure according to the design in the file by automatically accessing when run
will be created. Obstacles can consist of different types of objects. (Text to be given
0 to unobstructed paths; The values 1, 2, 3 are for three different types of objects.
will represent obstacles. These three obstacles occupy a different number of square areas.
Object with 1 value from object only 1 square area 2 objects with value 2 next to each other
a maximum of 2x2 with a square; Objects with a value of 3 contain 3 squares next to each other.
It will be placed on a maximum 3x3 square area)
Step 3: The starting and target points of the robot are suitable (obstacle or wall) on the grid.
must be randomly assigned to the squares. The robot initially created the entire grid world.
He should not know, he should only be able to see the next frames. Robot at every step
The frames that he did not learn should be shown as cloudy (closed), and the learned frames should be opened and related.
should be specified according to the object in the squares (obstacle, wall, road, etc.).
Step 4: In line with all this information, the shortest time the robot can reach the target as soon as possible.
the path should be displayed on the step-by-step grid. The places where the robot has passed before will be determined
It should leave a trace on the road at every step. If the goal is reached
The shortest path according to the robot going from the starting point to the target location is also on the grid.
should be drawn. Information about the total time elapsed (in seconds) and how many frames have been passed.
should be displayed on the screen.

PROBLEM 2:
In this problem, you are expected to deliver the robot to the exit point in the maze.
Step 1: You need to create a grid of dimensions requested by the user.
Step 2: A labyrinth should be created by placing obstacles of type 1 on the grid. Labyrinth
There must be roads that do not necessarily reach the exit.
Step 3: The entrance and exit points of the maze are any diagonal 2 corners of the rectangular grid.
should be determined. The robot must not initially know the maze. A path entered incorrectly in the maze
detected, the robot will go to the last location that it correctly detected, and from there it will travel
must continue to search.
Step 4: In line with all this information, the path followed by the robot to reach the exit step by step
should be displayed on the grid. At each step, we trace the paths that the robot has passed before.
must leave. When the robot reaches the destination, it goes from the entry point to the exit point.
The path must be drawn on the grid. Total elapsed time (in seconds), over how many frames
passed information should be displayed on the screen.

Class Definitions:

According to the definitions below, classes are expected to be created and coded in the project.

Robot Class:
● The robot should only be able to move in the up-down or right-left direction, and in the diagonal direction.
should not move.
● Information about only one unit from the current position in the robot grid world.
can see. It should not see the entire map.

Grid Class:
● For problem 1 in this class, the grid design is based on the text file at the given url.
While creating the grid for problem 2, according to the size information to be received from the user
should be created.

Obstacle Class:
● For problem 1 in the obstacle class, use three different types of objects to avoid obstacles.
maze for problem 2 while its creation is done according to text file at url
The occurrence is random within the application using a single object type 1.
will be created.

Application Class:
● In the application, the time it takes for the robot to reach the target in problem 1 and problem 2, how many
Functions of keeping information and displaying it on the screen as it passes over the square.
should provide.