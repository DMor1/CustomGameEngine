# CustomGameEngine

This project was created around 2010 to 2011 based heavily on a few books, but most notably: "Developing Games in Java" by David Brackeen. Although many concepts and methods were taken from the book, the game engine design was optimized to make it more efficient and permit more complex functionality.

Accompanying the game engine in which all the code for is in the net/danielmor/engine folder, there is also a game remake for the "Original Super Mario Bros." first level. All the content was reconstructed from scratch myself using photoshop for graphics and soundeffects were reproduced using my guitar and editing software. All this being said, the game is a reproduction of the original belonging to Nintendo and not mine. 

Furthermore, this was my first experience developing a larger project around 10th grade and was done before I was exposed to advanced Computer Science concepts in college. Looking back at this code now, much of it could be optimized using better data structures or algorithms and packaged in a cleaner way.

Running The Program:
1) Compile all java files (Assuming Linux) 
javac *.java

2) Execute TheOriginalSuperMarioBros
java TheOriginalSuperMarioBros

Note: For some reason, the game doesn't run very well in linux. The effects appear to be a poor refresh rate and double buffering not properly working. In addition, at least on my computer this causes collision checks to be skipped and Mario falling through the floor early in the level. This hasn't occured while running the program in windows. This may also be fixed by running the program on a faster computer due to how the game processes images.
