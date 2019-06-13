#!/bin/sh
cd `dirname $0`/..
BASE=`pwd`
PROJECT_PATH=$BASE
. "$BASE/bin/setenv.sh"

sh bin/killws.sh

for (( i=1; i<=10; i++)); do
	sleep 1
	checkresult=`ps ux|grep $SERVICE_NAME|grep java|wc -l`
	if [ $checkresult -eq 0 ];then
		sh bin/startws.sh
fi
done

for (( i=1; i<=10; i++)); do
	sleep 3
	sh bin/check.sh
	checkresult=$?
	if [ $checkresult -eq 0 ];then
		break
	fi
done
