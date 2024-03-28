Please change the downgrade the version to Java 8 by the following command(if it is downloaded).
export JAVA_HOME=`/usr/libexec/java_home -v 1.8`

If execute this game the first time, please enter the following command in terminal.
gradle build -x test

To execute this game, please enter the following command in terminal.
grale run

You can customise the parameters in game. Please follow the format in config.json.

You can customise the 33x36 maze in each level by creating a level{x}.txt where x is the level number. (33 is height and 36 is width)
The boundary of the maze must be X.

Character meanings in level config.
X: unbreakable stone
B: breakable brick
G: the normal gremlin
R: the red gremlin (run and shoot faster, health is higher)
S: shield (become invincible for 3 sec)

All the other characters are not allowed.