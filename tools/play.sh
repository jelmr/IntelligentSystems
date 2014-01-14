#!/bin/sh
# a command that works on Linux/Mac
# the arguments are passed in the same order as in the standard command

MAP=$1 
PLAYER1=$2
PLAYER2=$3
MODE=$4
NTURN=$5
MAXT=$6

DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"

javac -encoding ISO-8859-1 $PLAYER1.java $PLAYER2.java Bot.java SimulatedPlanetWars.java Heuristic.java PerformanceMeasure.java

echo "Player 1: "$PLAYER1 "(red)"
echo "Player 2: "$PLAYER2 "(blue)"


java -jar $DIR/PlayGame.jar $DIR/maps/8planets/map1.txt  "java $PLAYER1 " "java $PLAYER2" $MODE $NTURN $MAXT   | python $DIR/visualizer/visualize_locally.py
