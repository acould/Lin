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
cmd=$3

function runAll
{
	testfile=$1
	count=`awk 'END{print NR}' $testfile`
	for ((i=1;i<=$count;i++));do
		fileValue=`cat $testfile | sed -n ''$i'p' | sed 's/^\s*//;s/\s*$//'`
		service=$(echo $fileValue |awk -F'=' '{print $1}')
		
		bash $BASE/bin/cmd.sh "service" $service $cmd
	done
}

if [ "service" == "$type" ]; then
	hasPackageService="0"
	case $cmd in
	build|install|package)
		bash $BASE/bin/test-cmd.sh $value $value $cmd
	;;
	all)
		bash $BASE/bin/test-cmd.sh $value $value install
		bash $BASE/bin/cmd.sh service $value publish
	;;
	deploy)
		bash $BASE/bin/test-cmd.sh $value $value package
		bash $BASE/bin/cmd.sh service $value publish
	;;
	*)
		count=`awk 'END{print NR}' $file`
		for ((i=1;i<=$count;i++));do
			fileValue=`cat $file | sed -n ''$i'p' | sed 's/^\s*//;s/\s*$//'`
			serverName=$(echo $fileValue |awk -F'=' '{print $1}')
			serviceList=$(echo $fileValue |awk -F'=' '{print $2}')
		
			if [ "deploy" == "$cmd" ] && [ "$hasPackageService" == "0" ]; then
				bash $BASE/bin/test-cmd.sh $serverName ${value} package
				hasPackageService="1"
			fi
			
			for service in $(echo $serviceList|awk -F, '{for (num=1;num<=NF;num++)print $num}'); do
				if [ "$value" == "$service" ];then
					bash $BASE/bin/test-cmd.sh $serverName $service $cmd
				fi
			done
		done
	;;
	esac
elif [ "server" == "$type" ]; then
	serviceList=$(grep -i "^${value}\s*=" $file | cut -d= -f 2| sed 's/^\s*//;s/\s*$//')
	for service in $(echo $serviceList|awk -F, '{for (num=1;num<=NF;num++)print $num}'); do
		if [ "deploy" == "$cmd" ]; then
			bash $BASE/bin/test-cmd.sh $value $service package
		fi
		bash $BASE/bin/test-cmd.sh $value $service $cmd
	done
else
	file=$BASE/bin/conf/$value.properties
	if [ -e $file ];then
		runAll $file
	fi
fi
