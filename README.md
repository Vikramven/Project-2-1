# Project-2-1

## Rules:

Dice Chess is a variant of the classic Chess in which a dice is used to add randomness to the game. 
However, the goal remains the same: capture the opponent's King.

1. At every turn, the player rolls three dice. The outcome determines which pieces can be used for that move.
2. In this version, there is no check or checkmate. It is allowed to move the King to a square attacked by an opponent's piece.
3. In the case that a pawn would advance to the last row, the player has to wait until he/she rolls pawn again, contrary to 
some versions of the game. Thus, the player promotes the pawn to one of the pieces chosen by a separate single dice roll. 
If 'pawn' is the roll result, the player has the freedom to choose whichever piece the pawn gets promoted to.
4. Castling is allowed and issued to solely a King's move (not Rook).

## Run Procedures

#### Run from Terminal/Command Line
    (If you have a Gradle distribution installed in your PC)
    - Assure the 'gradle-[version]' bin directory is added to path
    - Navigate to 'Project-2-1', where 'build.gradle' is located
    - Type 'gradle run'

#### Run from Intellij
    - Select 'Edit Configurations' next to the 'run' green arrow
    - Select 'Add New Configuration', using the '+' symbol.
    - Select 'Gradle'
    (A configuration section pops up on the right)  
    - Give it a 'Name' of your choice
    - Write the task 'run' in the field 'Tasks and arguments'
    - 'Apply' followed by 'OK'
    - Ready to run
