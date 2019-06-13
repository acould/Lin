#!/bin/bash
pushd `dirname $0`/.. > /dev/null
BASE=`pwd`
popd > /dev/null
cd $BASE

if [ "x${CONF_PATH}x" == "xx" ];then
	CONF_PATH="$BASE/bin/conf"
fi

file=$CONF_PATH/server.properties

type=$1
value=$2

if [ "service" == "$type" ]; then
	count=`awk 'END{print NR}' $file`
	for ((i=1;i<=$count;i++));do
		fileValue=`cat $file | sed -n ''$i'p' | sed 's/^\s*//;s/\s*$//'`
		serverName=$(echo $fileValue |awk -F'=' '{print $1}')
		serviceList=$(echo $fileValue |awk -F'=' '{print $2}')
			
		. $BASE/bin/lib/getconf.sh $value
		
		if [ "x${cmdType}x" != "xx" ]; then
			testCmd=$(echo $serviceConf |awk -F';' '{print $3}')
			testResult=$(echo $serviceConf |awk -F';' '{print $4}')
			for service in $(echo $serviceList|awk -F, '{for (num=1;num<=NF;num++)print $num}'); do
				if [ "$value" == "$service" ];then
					. $BASE/bin/test-run.sh "$serverName" "$testCmd" "$testResult"
					if [ "$TESTOK" == "0" ];then
						echo "$serverName $value fail!";
					else
						echo "$serverName $value ok!";
					fi
				fi
			done
		fi
	done
elif [ "server" == "$type" ]; then
	serviceList=$(grep -i "^${value}\s*=" $file | cut -d= -f 2| sed 's/^\s*//;s/\s*$//')
	for service in $(echo $serviceList|awk -F, '{for (num=1;num<=NF;num++)print $num}'); do
		. $BASE/bin/lib/getconf.sh $service
		if [ "x${cmdType}x" != "xx" ]; then
			testCmd=$(echo $serviceConf |awk -F';' '{print $3}')
			testResult=$(echo $serviceConf |awk -F';' '{print $4}')
			. $BASE/bin/test-run.sh "$value" "$testCmd" "$testResult"
			if [ "$TESTOK" == "0" ];then
				echo "$serverName $service fail!";
			else
				echo "$serverName $service ok!";
			fi
		fi
	done
else
	count=`awk 'END{print NR}' $file`
	for ((i=1;i<=$count;i++));do
		fileValue=`cat $file | sed -n ''$i'p' | sed 's/^\s*//;s/\s*$//'`
		serverName=$(echo $fileValue |awk -F'=' '{print $1}')
		serviceList=$(echo $fileValue |awk -F'=' '{print $2}')
			
		for service in $(echo $serviceList|awk -F, '{for (num=1;num<=NF;num++)print $num}'); do
			. $BASE/bin/lib/getconf.sh $service
			if [ "x${cmdType}x" != "xx" ]; then
				testCmd=$(echo $serviceConf |awk -F';' '{print $3}')
				testResult=$(echo $serviceConf |awk -F';' '{print $4}')
				. $BASE/bin/test-run.sh "$serverName" "$testCmd" "$testResult"
				if [ "$TESTOK" == "0" ];then
					echo "$serverName $service fail!";
				else
					echo "$serverName $service ok!";
				fi
			fi
		done
	done
fi
