#!/bin/bash
pushd `dirname $0`/../.. > /dev/null
BASE=`pwd`
popd > /dev/null
cd $BASE

if [ "x${CONF_PATH}x" == "xx" ];then
	CONF_PATH="$BASE/bin/conf"
fi

task=$1
host=$2

taskConf=$(grep -i "^${task}\s*=" $BASE/bin/conf/task.properties | cut -d= -f 2| sed 's/^\s*//;s/\s*$//')

if [ "x${host}x" == "xx" ]; then
	echo "bin/task-publish.sh task host"
	exit 0;
fi

if [ "x${taskConf}x" != "xx" ]; then
	taskKillCmd=$(echo $taskConf |awk -F';' '{print $1}')
	taskStartCmd=$(echo $taskConf |awk -F';' '{print $2}')

	testCmd=$(echo $taskConf |awk -F';' '{print $3}')
	testResult=$(echo $taskConf |awk -F';' '{print $4}')

	taskDir=$(grep -i "^task-dir\s*=" $CONF_PATH/system.properties | cut -d= -f 2| sed 's/^\s*//;s/\s*$//')
	sshPort=$(grep -i "^ssh-port\s*=" $CONF_PATH/system.properties | cut -d= -f 2| sed 's/^\s*//;s/\s*$//')
	
	cd "${BASE}${taskDir}${task}"

	packageCount=$(find target/*-publish.tar.gz -type f 2>&1|grep "No such file"|wc -l)
	if [ $packageCount -eq 1 ]; then
		bash $BASE/bin/lib/task-package.sh $task
	fi
	echo "scp -P $sshPort target/*-publish.tar.gz $host:~/"
    scp -P $sshPort target/*-publish.tar.gz $host:~/
    
    if [ "x${taskStartCmd}x" != "xx" ];then
    	count=$(ssh -p $sshPort $host "find . -maxdepth 1 -name ${task} -type d"|wc -l)
    	if [ $count -gt 0 ];then
    		date=$(date +%Y%m%d%H%M%S)
    		echo "ssh -p $sshPort $host \"bash ~/${task}/bin/${taskKillCmd};mkdir -p bak;sleep 3;mv $task bak/${task}-$date\""
    		ssh -p $sshPort $host "bash ~/${task}/bin/${taskKillCmd};mkdir -p bak;"
    		
    		TESTOK="1"
			while [ "$TESTOK" == "1" ]; do
				sleep 1
				. $BASE/bin/test-run.sh "$host" "$testCmd" "$testResult" 
				echo -e ".\c"
			done
			ssh -p $sshPort $host "mv $task bak/${task}-$date"
    	fi
    
    	echo "ssh -p $sshPort $host \"tar zxf ${task}-*-publish.tar.gz;rm -rf ${task}-*-publish.tar.gz\"" 
    	ssh -p $sshPort $host "tar zxf ${task}-*-publish.tar.gz;rm -rf ${task}-*-publish.tar.gz"
    	
    	echo "ssh -p $sshPort $host \"bash ~/$task/bin/${taskStartCmd};\""
		ssh -p $sshPort $host "bash ~/$task/bin/${taskStartCmd};"
    else		
		count=$(ssh -p $sshPort $host "find . -maxdepth 1 -name ${task} -type d"|wc -l)
    	if [ $count -gt 0 ];then
    		date=$(date +%Y%m%d%H%M%S)
    		echo "ssh -p $sshPort $host \"mkdir -p bak;sleep 3;mv $task bak/${task}-$date\""
   			ssh -p $sshPort $host "mkdir -p bak;sleep 3;mv $task bak/${task}-$date"
   		fi
   		
   		echo "ssh -p $sshPort $host \"tar zxf ${task}-*-publish.tar.gz;rm -rf ${task}-*-publish.tar.gz;\"";
   		ssh -p $sshPort $host "tar zxf ${task}-*-publish.tar.gz;rm -rf ${task}-*-publish.tar.gz;"
    fi
fi
