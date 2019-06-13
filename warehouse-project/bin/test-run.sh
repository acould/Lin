#!/bin/bash
if [ "x${BASE}x" == "xx" ];then
	pushd `dirname $0`/.. > /dev/null
	BASE=`pwd`
	popd > /dev/null
fi
cd $BASE

if [ "x${CONF_PATH}x" == "xx" ];then
	CONF_PATH="$BASE/bin/conf"
fi

testHost=$1
testCmd=$2
testResult=$3

sshPort=$(grep -i "^ssh-port\s*=" $CONF_PATH/system.properties | cut -d= -f 2| sed 's/^\s*//;s/\s*$//')

if [ "x${testCmd}x" != "xx" ]; then 
	TESTOK=$(ssh -p $sshPort $testHost "$testCmd"|grep "$testResult" |wc -l)
else
	TESTOK="1"
fi
