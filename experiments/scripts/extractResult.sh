#!/bin/bash

file=$1
nbGames=$2
output=$3
#parse the JSON file
json=$(cat $file)


# Get the board size
boardSize=$(echo "$json" | jq ".boardSize")

# Get the players
players=$(echo "$json" | jq ".players")

# If we have less than 2 players, we exit
if [ $(echo "$players" | jq 'length') -lt 2 ]; then
  echo "\u[31mError: You need at least 2 players\u[0m"
  exit 1
fi

#initialize command string
cmd="java -classpath "build:lib/*" core.Experiment $boardSize"


#loop through players array and add strategie and depth to command string
for j in $(seq 0 $(($(echo "$players" | jq 'length')-1))); do
  # Get Algorithm name (IndividualSearch or CoalitionSearch)
  algorithm=$(echo "$players" | jq .[$j].algorithm | tr -d '"')
  # Get the selection strategy name(Maxn, Paranoid, etc.)
  strategie=$(echo "$players" | jq .[$j].strategy | tr -d '"')
  # Get the depth
  depth=$(echo "$players" | jq .[$j].depth)
  # Get the coalition size (1 for IndividualSearch)
  coalitionSize=$(echo "$players" | jq .[$j].coalitionSize)
  # Get the boolean that indicates if we use pruning or not
  pruning=$(echo "$players" | jq .[$j].pruning)

  # Add the player to the command string
  cmd="$cmd $algorithm,$strategie,$depth,$coalitionSize,$pruning"
done

# We create a file that will contain the results of the experiment
# The name of the file is the name of the JSON file without the extension and we add the number of games and the date
# The file is created in the results folder
# The file is a CSV file
# The first line contains the name of the columns(Algorithm, Selection Strategy, Depth, Coalition Size, Pruning, Result)

# If the results directory does not exist, we create it
if [ ! -d "results" ]; then
  mkdir results
fi


# We create the file name
fileName="experiments/results/$output.csv"

# We create the file
touch $fileName

# If the file is empty, we add the header
if [ ! -s $fileName ]; then
  echo "Result;Nodes;nbGames;Depth;sizeGrid" >> $fileName
fi


ant compile

# We execute the command
for i in $(seq 1 $nbGames); do


  # We clean the result (we just get information after each ":")
  #result=$(echo $result | awk '{print $NF'} | sed ':a;N;$!ba;s/\n/;/g') >> $fileName
  #echo $result
  $cmd | awk '{print $NF}' | sed ':a;N;$!ba;s/\n/;/g' >> $fileName
done

