#! /bin/bash

NUM_COL=0
arr=()
for FILE in */
do
	if [ ${#FILE} -gt $NUM_COL ]
	then
		NUM_COL=${#FILE}
	fi
	#check for spaces in filename
	if [[ $FILE =*" "* ]]
	then
		echo "'$FILE'" | tr -d '/'
	elif [[ ! "$FILE" = *" "* ]]
	then
		if [ ! $NUM_COL -gt 80 ]
		then
			arr+=( "$FILE" )
		else
			printf "rs: Display width 80 is less than column width $NUM_COL"
		fi
	fi
done
echo "${arr[*]}" | tr -d '/' | rs -w80 0 $NUM_COL