#!/bin/bash

# colour for visibility
green='\033[0;32m'
red='\033[0;31m'
clear='\033[0m'

#-> AGGREGATE SERVER LOG OF REQUESTS IS STORED IN OUTPUT.TXT
#-> RESULT IS STORED IN RESULT.TXT

### BASE CASES W/ M1-M9 PROFILES ###

# Test Case 1: 1 Member Proposes
echo "Calculating Test Case 1..."
> result.txt
pkill java
sleep 5
java AggregateServer > output.txt &
java Member M1 Simeon > /dev/null 2>&1 &
java Member M4 > /dev/null 2>&1 &
java Member M5 > /dev/null 2>&1 &
java Member M6 > /dev/null 2>&1 &
java Member M7 > /dev/null 2>&1 &
java Member M8 > /dev/null 2>&1 &
java Member M9 > /dev/null 2>&1 &
wait
# Expected Result: Simeon
DIFF=$(diff result.txt "Simeon.txt")
if [ "$DIFF" != "" ]
then
	echo -e "${red}Test case 1 failed.${clear}"
else
	echo -e "${green}Test case 1 passed.${clear}"
fi

# Test Case 2: 2 Members Propose Simulataneously
echo "Calculating Test Case 2..."
> result.txt
pkill java
sleep 5
java AggregateServer > output.txt &
java Member M1 Simeon > /dev/null 2>&1 &
java Member M2 Declan > /dev/null 2>&1 &
java Member M4 > /dev/null 2>&1 &
java Member M5 > /dev/null 2>&1 &
java Member M6 > /dev/null 2>&1 &
java Member M7 > /dev/null 2>&1 &
java Member M8 > /dev/null 2>&1 &
java Member M9 > /dev/null 2>&1 &
wait
# Expected Result: Simeon or Declan
DIFF=$(diff result.txt "Simeon.txt")
DIFF2=$(diff result.txt "Declan.txt")
if [ "$DIFF" == "" ] || [ "$DIFF2" == "" ]
then
	echo -e "${green}Test case 2 passed.${clear}"
else
	echo -e "${red}Test case 2 failed.${clear}"
fi

# Test Case 3: 3 Members Propose Simulataneously
echo "Calculating Test Case 3..."
> result.txt
pkill java
sleep 5
java AggregateServer > output.txt &
java Member M1 Simeon > /dev/null 2>&1 &
java Member M2 Declan > /dev/null 2>&1 &
java Member M3 Amelia > /dev/null 2>&1 &
java Member M4 > /dev/null 2>&1 &
java Member M5 > /dev/null 2>&1 &
java Member M6 > /dev/null 2>&1 &
java Member M7 > /dev/null 2>&1 &
java Member M8 > /dev/null 2>&1 &
java Member M9 > /dev/null 2>&1 &
wait
# Expected Result: Simeon or Declan or Amelia
DIFF=$(diff result.txt "Simeon.txt")
DIFF2=$(diff result.txt "Declan.txt")
DIFF3=$(diff result.txt "Amelia.txt")
if [ "$DIFF" == "" ] || [ "$DIFF2" == "" ] || [ "$DIFF3" == "" ]
then
	echo -e "${green}Test case 3 passed.${clear}"
else
	echo -e "${red}Test case 3 failed.${clear}"
fi

### BONUS IMPLEMENTATION N COUNCILLORS W/ IMMEDIATE,MEDIUM,LATE,NEVER PROFILES ###

# Test Case 4: 5 Members Propose Simulataneously
echo "Calculating Test Case 4..."
> result.txt
pkill java
sleep 5
java AggregateServer > output.txt &
java Member immediate Simeon > /dev/null 2>&1 &
java Member late Declan > /dev/null 2>&1 &
java Member never Amelia > /dev/null 2>&1 &
java Member medium Tommy > /dev/null 2>&1 &
java Member immediate Marlin > /dev/null 2>&1 &
wait
# Expected Result: Simeon or Declan or Tommy or Marlin (NOT AMELIA)
DIFF=$(diff result.txt "Simeon.txt")
DIFF2=$(diff result.txt "Declan.txt")
DIFF3=$(diff result.txt "Tommy.txt")
DIFF4=$(diff result.txt "Marlin.txt")
if [ "$DIFF" == "" ] || [ "$DIFF2" == "" ] || [ "$DIFF3" == "" ] || [ "$DIFF4" == "" ]
then
	echo -e "${green}Test case 4 passed.${clear}"
else
	echo -e "${red}Test case 4 failed.${clear}"
fi

# Test Case 5: 10 Members Propose Simulataneously
echo "Calculating Test Case 5..."
> result.txt
pkill java
sleep 5
java AggregateServer > output.txt &
java Member never Simeon > /dev/null 2>&1 &
java Member medium Declan > /dev/null 2>&1 &
java Member immediate Amelia > /dev/null 2>&1 &
java Member never Tommy > /dev/null 2>&1 &
java Member late Marlin > /dev/null 2>&1 &
java Member never James > /dev/null 2>&1 &
java Member late Margaret > /dev/null 2>&1 &
java Member immediate Holly > /dev/null 2>&1 &
java Member medium Jon > /dev/null 2>&1 &
java Member never Ethan > /dev/null 2>&1 &
wait
# Expected Result: Declan or Amelia or Marlin or Margaret or Holly or Jon
DIFF=$(diff result.txt "Declan.txt")
DIFF2=$(diff result.txt "Amelia.txt")
DIFF3=$(diff result.txt "Marlin.txt")
DIFF4=$(diff result.txt "Margaret.txt")
DIFF5=$(diff result.txt "Holly.txt")
DIFF6=$(diff result.txt "Jon.txt")
if [ "$DIFF" == "" ] || [ "$DIFF2" == "" ] || [ "$DIFF3" == "" ] || [ "$DIFF4" == "" ] || [ "$DIFF5" == "" ] || [ "$DIFF6" == "" ]
then
	echo -e "${green}Test case 5 passed.${clear}"
else
	echo -e "${red}Test case 5 failed.${clear}"
fi

### FAILURE TOLERANCE ###

# Test Case 6: Testing 2 "Never" Profiles That Leave after Proposing (with m = 2 failures & 2m+1 = 5 members, system should withstand)
echo "Calculating Test Case 6..."
> result.txt
pkill java
sleep 5
java AggregateServer > output.txt &
java Member medium Simeon > /dev/null 2>&1 &
java Member immediate Declan > /dev/null 2>&1 &
java Member late Amelia > /dev/null 2>&1 &
java Member never Tommy > /dev/null 2>&1 &
java Member never Marlin > /dev/null 2>&1 &
wait
# Expected Result: Simeon or Declan or Amelia
DIFF=$(diff result.txt "Simeon.txt")
DIFF2=$(diff result.txt "Declan.txt")
DIFF3=$(diff result.txt "Amelia.txt")
if [ "$DIFF" == "" ] || [ "$DIFF2" == "" ] || [ "$DIFF3" == "" ]
then
	echo -e "${green}Test case 6 passed.${clear}"
else
	echo -e "${red}Test case 6 failed.${clear}"
fi
