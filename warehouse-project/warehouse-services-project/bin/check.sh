#!/bin/bash

cd `dirname $0`/..
BASE=`pwd`

PROJECT_PATH=$BASE

. "$BASE/bin/setenv.sh"

function testDubbo() {
	status=`(echo "status";sleep 1;)|telnet 127.0.0.1 $1 2>&1|xargs echo|awk '{print $10}'`
        if [ "x${status}x" == "xOKx" ]; then
           echo "ok"
        fi
}

if [ "$ISRUN" != "1" ]; then
	echo "fail 1."
	exit 1
else
	pid=`ps -ef|grep "base.name=${SERVICE_NAME}"| grep -v 'grep ' | awk '{print $2}'`
	if [ "x${pid}x" == "xx" ]; then
		echo "fail 2."
		exit 2
	fi
	port=`netstat -tnlp 2>&1|awk '{print $4,":"$NF}'|grep ":${pid}/java"|awk '{print $1}'|awk -F: '{print $NF}'`
	if [ "x${port}x" == "xx" ]; then
		echo "fail 3."
		exit 3
	fi
	num=`echo $port|awk '{print NF}'`
	for((i=1;i<=$((num)) ;i++));do
		p=`echo $port|awk "{print $"$i"}"`
		ret=$(testDubbo $p)
		if [ "x${ret}x" != "xx" ]; then
			echo $ret
			exit 0
		fi
	done
	echo "fail 4."
	exit 4
fi
