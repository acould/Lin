#!/bin/bash
pushd `dirname $0`/.. > /dev/null
BASE=`pwd`
popd > /dev/null
cd $BASE

service=$1

NOW_CONF_PATH="$BASE/bin/conf"

cmdType="";

confFile="";

test=$(echo $(awk "BEGIN{ print index(\"${service}\",\"-services-\") }") | sed 's/^\s*//;s/\s*$//')
if [ $test -eq 0 ];then
	test=$(echo $(awk "BEGIN{ print index(\"${service}\",\"-task-\") }") | sed 's/^\s*//;s/\s*$//')
	if [ $test -eq 0 ];then
		test=$(echo $(awk "BEGIN{ print index(\"${service}\",\"-web-\") }") | sed 's/^\s*//;s/\s*$//')
		if [ $test -gt 0 ];then
			cmdType="web"
			confFile=$NOW_CONF_PATH/web.properties
		fi
	else
		cmdType="task"
		confFile=$NOW_CONF_PATH/task.properties
	fi
else
	cmdType="service"
	confFile=$NOW_CONF_PATH/service.properties
fi

if [ "x${confFile}x" == "xx" ];then
	echo "${service} No Find!"
	exit 0;
fi

serviceConf=$(grep -i "^${service}\s*=" $confFile | cut -d= -f 2| sed 's/^\s*//;s/\s*$//')
