#!/bin/sh
# a command that works on Linux/Mac
# the arguments are passed in the same order as in the standard command
# Usage: sim.sh <simulation_per_map> <player1> <player2> <max_turns> <max_turn_length>
# E.g. sim.sh 5 CustomBot CustomBot 100 4000

SIMULATIONS=$1
PLAYER1=$2
PLAYER2=$3
MODE=parallel
NTURN=100
MAXT=4000

DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"

javac -encoding ISO-8859-1 $PLAYER1.java $PLAYER2.java Bot.java SimulatedPlanetWars.java Heuristic.java PerformanceMeasure.java

echo "Player 1: "$PLAYER1 "(red)"
echo "Player 2: "$PLAYER2 "(blue)"


# Keep track of wins
player1total=0
player2total=0
drawstotal=0

#for map in ${maps[*]}
for map in $DIR/maps/**/*.txt
do
    # Keep track of wins
    player1=0
    player2=0
    draws=0
    echo $map
    COUNTER=0
    while [  $COUNTER -lt $SIMULATIONS ]; do
        echo Game $COUNTER
        output=$(java -jar $DIR/PlayGame.jar $map  "java $PLAYER1 " "java $PLAYER2" parallel $NTURN $MAXT 2>&1 )
        winner=$(echo $output | grep -E -o "Player [0-9] Wins"  )
        if [ "$winner" = "Player 1 Wins" ]
        then
            player1=$((player1 + 1))
        elif [ "$winner" = "Player 2 Wins" ]
        then
            player2=$((player2 + 1))
        else
            draws=$((draws + 1))
        fi
        let COUNTER+=1

    done

    echo Player 1 wins: $player1
    echo Player 2 wins: $player2
    echo Draws: $draws

    let player1total+=$player1
    let player2total+=$player2
    let drawstotal+=$draws
done

    echo Player 1 total wins: $player1total
    echo Player 2 total wins: $player2total
    echo Draws total: $drawstotal
