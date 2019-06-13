#!/bin/bash

cd `dirname $0`/..
BASE=`pwd`

PROJECT_PATH=$BASE

. "$BASE/bin/setenv.sh"

# top httpd
if [ "$CONTROLNGINX" != "0" ]; then
	bash $BASE/bin/httpd.sh stop
fi

if [ "$ISRUN" == "1" ]; then
	echo "Stoping ${PROJECT}..."
	$JAVA_HOME/bin/java -jar $libPath/jetty-start-*.jar -DSTOP.PORT=${STOPPORT} -DSTOP.KEY=${PROJECT} --stop
fi
