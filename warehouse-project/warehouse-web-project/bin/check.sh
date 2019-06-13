#!/bin/bash

cd `dirname $0`/..
BASE=`pwd`

PROJECT_PATH=$BASE

. "$BASE/bin/setenv.sh"

if [ "$ISRUN" != "1" ]; then
	echo "fail 1."
	exit 1
else
	status=`curl -I -m 10 -o /dev/null -s -w %{http_code} http://127.0.0.1:${PORT}`
	if [ "$status" == "200" ] || [ "$status" == "302" ] || [ "$status" == "301" ] || [ "$status" == "404" ]; then
		echo "ok"
		exit 0
	fi
	echo "fail 2."
	exit 2
fi
