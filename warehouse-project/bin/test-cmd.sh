#!/bin/bash
pushd `dirname $0`/.. > /dev/null
BASE=`pwd`
popd > /dev/null
cd $BASE

if [ "x${CONF_PATH}x" == "xx" ];then
	CONF_PATH="$BASE/bin/conf"
fi

serverName=$1
service=$2
cmd=$3

. $BASE/bin/lib/getconf.sh $service

sshPort=$(grep -i "^ssh-port\s*=" $CONF_PATH/system.properties | cut -d= -f 2| sed 's/^\s*//;s/\s*$//')

if [ "x${cmdType}x" != "xx" ];then
	case $cmd in
	publish|deploy)
		cmd=publish
		testCmd=$(echo $serviceConf |awk -F';' '{print $3}')
		testResult=$(echo $serviceConf |awk -F';' '{print $4}')
		startCmd=$(echo $serviceConf |awk -F';' '{print $2}')
		
		bash $BASE/bin/lib/${cmdType}-${cmd}.sh $service $serverName
		
		if [ "x${startCmd}x" != "xx" ];then
			TESTOK="0"
			while [ "$TESTOK" == "0" ]; do
				sleep 4
				. $BASE/bin/test-run.sh "$serverName" "$testCmd" "$testResult" 
				echo -e ".\c"
			done
		fi
		
		echo "OK"
	;;
	kill)
		if [ "service" == "$cmdType" ];then
			killCmd="killws.sh"			
		else
			killCmd=$(echo $serviceConf |awk -F';' '{print $1}')
		fi
		if [ "x${killCmd}x" != null ];then
			echo "kill $serverName $service:"
			ssh -p $sshPort $serverName "bash ~/${service}/bin/${killCmd};"
		fi
	;;
	start)
		if [ "service" == "$cmdType" ];then
			startCmd="startws.sh"			
		else
			startCmd=$(echo $serviceConf |awk -F';' '{print $2}')
		fi
		if [ "x${startCmd}x" != null ];then
			echo "start $serverName $service:"
			ssh -p $sshPort $serverName "bash ~/${service}/bin/${startCmd};"
		fi
	;;
	install)
#		bash $BASE/bin/lib/${cmdType}-build.sh $service $serverName
		bash $BASE/bin/lib/${cmdType}-package.sh $service $serverName
	;;
	all)
#		bash $BASE/bin/lib/${cmdType}-build.sh $service $serverName
		bash $BASE/bin/lib/${cmdType}-package.sh $service $serverName
		bash $BASE/bin/lib/${cmdType}-publish.sh $service $serverName
	;;
	*)
		bash $BASE/bin/lib/${cmdType}-${cmd}.sh $service $serverName
	;;
	esac
fi