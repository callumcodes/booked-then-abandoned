#!/bin/bash

array=(Pink Red Teal Green Orange)

for color in "${array[@]}" 
do
  printf "$color \n"
  curl -d '{"lastUsed": 1519746450}' -H "Content-Type: application/json" -X PUT https://booked-then-abandoned.herokuapp.com/room/$color
  printf "\n" 	
done

printf "\nFinished!!\n"
