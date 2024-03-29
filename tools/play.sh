#!/bin/sh
# a command that works on Linux/Mac
# the arguments are passed in the same order as in the standard command

MAP=$1 
PLAYER1=$2
PLAYER2=$3
MODE=$4
NTURN=100
MAXT=$6

DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"

PLAYER1="ShikataGaNaiBot"
PLAYER2="CompetitionBot"

#javac -encoding ISO-8859-1 $PLAYER1.java $PLAYER2.java Bot.java SimulatedPlanetWars15.java Heuristic15.java PerformanceMeasure15.java

echo "Player 1: "$PLAYER1 "(red)"
echo "Player 2: "$PLAYER2 "(blue)"


java -jar $DIR/PlayGame.jar $DIR/maps/8planets/map3.txt  "java $PLAYER1 " "java $PLAYER2" parallel $NTURN 4000   | python $DIR/visualizer/visualize_locally.py
