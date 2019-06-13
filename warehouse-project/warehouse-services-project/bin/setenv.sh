#!/bin/bash

BASE=$PROJECT_PATH

if [ -z "$PROJECT_PATH" ] ; then
	cd `dirname $0`/..
	BASE=`pwd`
fi

export SERVICE_NAME=${BASE##*/}

if [ `cat /proc/version|grep Ubuntu|wc -l` == "1" ] ; then

export TZ='Asia/Shanghai';

else

export LANG=zh_CN
export LC_ALL=zh_CN.UTF-8

fi

export JAVA_HOME=/usr/java/default

confPath=$BASE/conf
BASEROOT="$BASE/../"
#SERVICE_NAME=$(grep -i "^dubbo.name\s*=" $confPath/baseconfig.properties | cut -d= -f 2| sed 's/^\s*//;s/\s*$//')
SERVICE_NAME=${SERVICE_NAME}

STDOUTFILE="${BASEROOT}logs/${SERVICE_NAME}/stdout.log"
mkdir -p ${BASEROOT}logs/${SERVICE_NAME}

CLASSPATH=`echo $JAVA_HOME/lib/*.jar | tr ' ' ':'`
CLASSPATH=$CLASSPATH:$confPath
CLASSPATH=$CLASSPATH:`echo $BASE/lib/*.jar | tr ' ' ':'`

#debug
#file $confPath/baseconfig.properties | grep -q CRLF && dos2unix $confPath/baseconfig.properties
#PORT=$(grep -i "^${SERVICE_NAME}.port\s*=" $confPath/baseconfig.properties | cut -d= -f 2| sed 's/^\s*//;s/\s*$//')
#let "RUNPORT = $PORT + 1"
#let "STOPPORT = $PORT + 2"
#let "DEBUGPORT = $PORT + 3"
#ISDEBUG=$(grep -i "^sys.debug\s*=" $confPath/baseconfig.properties | cut -d= -f 2| sed 's/^\s*//;s/\s*$//')
#PROJECT=$(grep -i "^sys.name\s*=" $confPath/baseconfig.properties | cut -d= -f 2| sed 's/^\s*//;s/\s*$//')

ISRUN=$(ps ux|grep "base.name=${SERVICE_NAME} "|grep java |wc -l)

#while 
#	tmpport=$(ps ux|grep ",address=$DEBUGPORT"|grep java |wc -l)
#	[ "$tmpport" != "0" ]
#do
#	let "DEBUGPORT = $DEBUGPORT + 1"
#done

#if [ "$ISDEBUG" == "true" ] ; then
#	DEBUG_OPTS="-Xdebug -Xnoagent -Djava.compiler=NONE -Xrunjdwp:transport=dt_socket,address=$DEBUGPORT,server=y,suspend=n"
#fi

#run
#memoryConfig=$(grep -i "^${SERVICE_NAME}-memory\s*=" $confPath/baseconfig.properties | cut -d= -f 2| sed 's/^\s*//;s/\s*$//')
#
#if [ "x${memoryConfig}x" == "xx" ];then
#	memoryConfig=$(grep -i "^dubbo-memory-default\s*=" $confPath/baseconfig.properties | cut -d= -f 2| sed 's/^\s*//;s/\s*$//')
#fi
#
#MSSIZE=$(echo $memoryConfig |awk -F'|' '{print $1}')
#MXSIZE=$(echo $memoryConfig |awk -F'|' '{print $2}')
#echo "$BASE/../binconf/jvm.properties"
if [ -f "$BASE/../binconf/jvm.properties" ]; then
    MSSIZE=$(grep -i "^jvm.xms\s*=" $BASE/../binconf/jvm.properties | cut -d= -f 2| sed 's/^ //;s/ $//')
    MXSIZE=$(grep -i "^jvm.xmx\s*=" $BASE/../binconf/jvm.properties | cut -d= -f 2| sed 's/^ //;s/ $//')
else
    MSSIZE=1g
    MXSIZE=1g
fi

ZKINFO="-Dmonitor.jvm=on -Dmonitor.heartbeat.interval=10"

JAVA_CONF="-XX:+UseParNewGC -XX:+UseConcMarkSweepGC -XX:CMSInitiatingOccupancyFraction=70 -XX:+AggressiveOpts -XX:+UseFastAccessorMethods -Xms$MSSIZE -Xmx$MXSIZE $DEBUG_OPTS $ZKINFO -Djava.io.tmpdir=$BASE/tmp/run.log -Dbase.name=$SERVICE_NAME -Dbase.root=$BASEROOT -Dlog4j.config=$confPath/log4j.properties"
