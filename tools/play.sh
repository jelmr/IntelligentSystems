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

PLAYER1="MinMaxBot"
PLAYER2="CustomBot"

javac -encoding ISO-8859-1 $PLAYER1.java $PLAYER2.java Bot.java SimulatedPlanetWars.java Heuristic.java PerformanceMeasure.java

echo "Player 1: "$PLAYER1 "(red)"
echo "Player 2: "$PLAYER2 "(blue)"


java -jar $DIR/PlayGame.jar $DIR/maps/8planets/map1.txt  "java $PLAYER1 " "java $PLAYER2" parallel $NTURN 4000   | python $DIR/visualizer/visualize_locally.py
