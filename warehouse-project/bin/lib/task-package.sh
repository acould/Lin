#!/bin/bash
pushd `dirname $0`/../.. > /dev/null
BASE=`pwd`
popd > /dev/null
cd $BASE

if [ "x${CONF_PATH}x" == "xx" ];then
	CONF_PATH="$BASE/bin/conf"
fi

task=$1

taskConf=$(grep -i "^${task}\s*=" $BASE/bin/conf/task.properties | cut -d= -f 2| sed 's/^\s*//;s/\s*$//')

bash $BASE/bin/lib/initconf.sh

if [ "x${taskConf}x" != "xx" ]; then
    taskNamePath=$(grep -i "^task-name-path\s*=" $CONF_PATH/system.properties | cut -d= -f 2| sed 's/^\s*//;s/\s*$//')
	taskProfile=$(grep -i "^task-profile\s*=" $CONF_PATH/system.properties | cut -d= -f 2| sed 's/^\s*//;s/\s*$//')
    taskDependProject=$(grep -i "^task-depend-project\s*=" $CONF_PATH/system.properties | cut -d= -f 2| sed 's/^\s*//;s/\s*$//')
	
	if [ "x${taskDependProject}x" != "xx" ]; then
		if [ -d ${BASE}/${taskDependProject} ] ; then
			bash ${BASE}/${taskDependProject}/bin/build.sh
			cd ${BASE}
		else
			echo "${taskDependProject} not find!"
		fi
	fi
    
    gitVersion=$(git rev-parse HEAD)
    PL="${taskNamePath}:${task}";
	
	otherProfile=""
	if [ "x${taskProfile}x" != "xx" ];then
		otherProfile="-P${taskProfile}"
	fi
	
	mvn -U -am clean install -pl $PL $otherProfile,publish -Dgit.version=${gitVersion}
fi
