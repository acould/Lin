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
		mkdir -p $BASE/conf
	fi
	echo "Starting ${SERVICE_NAME}..."

	export CLASSPATH=$CLASSPATH
	
##nohup $JAVA_HOME/bin/java -server $JAVA_CONF -Ddubbo.name=${SERVICE_NAME} -Ddubbo.port=${PORT} -Dspring.configfile=spring-modules.xml star.dubbo.DubboService >> $STDOUTFILE 2>&1 &
	nohup $JAVA_HOME/bin/java -server $JAVA_CONF -Ddubbo.spring.config=spring-modules.xml star.fw.ServiceLauncher >> $STDOUTFILE 2>&1 &
	echo $!> $BASE/logs/pids.log/${SERVICE_NAME}.pid

else
	echo "${SERVICE_NAME} is running."
fi
