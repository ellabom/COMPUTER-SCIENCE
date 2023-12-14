#! /bin/bash
NUM_COL=0
max=
arr=()
if [ $# -ne 1 ]
then 
	echo "Usage Message: lsdir.sh $0"S
	exit 0
else 
	if [ ! -e "$1" ]
	then
		printf "could not change current working directory to '$1'.\n"
		exit 1
	else
		cd $1
		for FILE in */
		do
			if [ ${#FILE} -gt $NUM_COL ]
			then
				max=${#FILE}
				NUM_COL=$(( 80/(max+1)))
			fi
			#check for spaces in filename
			if [[ $FILE = *" "* ]]
			then
				echo "'$FILE'" | tr -d '/'
			else [[ ! "$FILE" = *" "* ]]
				if [ ! $NUM_COL -gt 80 ]
				then 
					arr+=( "$FILE" )
				else
					printf "rs: Display width 80 is less than coluwn width $NUM_COL"
				fi
			fi
		done
		echo "${arr[*]}" | tr -d '/' | rs -w80 0 $NUM_COL
	fi
fi