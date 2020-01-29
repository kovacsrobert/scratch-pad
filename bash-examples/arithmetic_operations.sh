#!/bin/bash

# read variable from stdin
myVar=$(</dev/stdin)

# execute float point aritmetic to 4 scale
myVar2=`echo "scale=4;$myVar" | bc`

# round and print to 3 scale
printf "%.3f" "$myVar2"
