echo 'Player 1:' $2 '(red)'
echo 'Player 2:' $3 '(blue)'
javac $2.java
bash playNoGUI.sh $1 $2 $3 $4 100 200 > log.txt
