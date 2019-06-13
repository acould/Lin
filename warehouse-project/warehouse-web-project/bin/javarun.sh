#!/bin/bash

cd `dirname $0`/..
BASE=`pwd`

PROJECT_PATH=$BASE

. "$BASE/bin/setenv.sh"

export CLASSPATH=$CLASSPATH
$JAVA_HOME/bin/java $JAVA_CONF  -Dlog4j.config=/log4j.properties -Dspring.configfile=/spring-modules.xml $*
