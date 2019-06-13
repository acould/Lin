#!/bin/bash

cd `dirname $0`/..
BASE=`pwd`

PROJECT_PATH=$BASE
	
. "$BASE/bin/setenv.sh"

#pinpoint agent load
#AGENT_LOAD_SCRIPT="/usr/local/pinpoint-agent/apm/pinpoint.sh"
#.  $AGENT_LOAD_SCRIPT
JAVA_CONF="$JAVA_CONF $AGENT_CONF"

	
	if [ "$ISRUN" != "1" ]; then
		if [ ! -d "$BASE/logs/pids.log" ] ; then
			mkdir -p $BASE/logs/pids.log
			mkdir -p $BASE/tmp/run.log
		fi
	
		echo "Starting ${PROJECT}..."
		nohup $JAVA_HOME/bin/java -Xbootclasspath/p:$BOOTLIB -server $JAVA_CONF -jar $libPath/jetty-runner-*.jar --stop-port ${STOPPORT} --stop-key ${PROJECT} $JettyConf $BASE/web > $STDOUTFILE 2>&1 &
		while
			tmpport=$(netstat -tnlp 2>&1|grep "$RUNPORT"|wc -l)
			[ "$tmpport" == "0" ]
		do
			echo -e ".\c"
			sleep 2
		done
	
		if [ "$CONTROLNGINX" != "0" ]; then
			# start httpd
			nohup bash $BASE/bin/httpd.sh start > /dev/null 2>&1 &
		fi
		
	else
		echo "${PROJECT} is running."
	fi
