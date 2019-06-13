#!/bin/bash
pushd `dirname $0`/../.. > /dev/null
BASE=`pwd`
popd > /dev/null
cd $BASE

if [ "x${CONF_PATH}x" == "xx" ];then
	CONF_PATH="$BASE/bin/conf"
fi

web=$1
host=$2

webConf=$(grep -i "^${web}\s*=" $BASE/bin/conf/web.properties | cut -d= -f 2| sed 's/^\s*//;s/\s*$//')

if [ "x${host}x" == "xx" ]; then
	echo "bin/web-publish.sh web host"
	exit 0;
fi

if [ "x${webConf}x" != "xx" ]; then
	webKillCmd=$(echo $webConf |awk -F';' '{print $1}')
	webStartCmd=$(echo $webConf |awk -F';' '{print $2}')
	
	testCmd=$(echo $webConf |awk -F';' '{print $3}')
	testResult=$(echo $webConf |awk -F';' '{print $4}')

	webDir=$(grep -i "^web-dir\s*=" $CONF_PATH/system.properties | cut -d= -f 2| sed 's/^\s*//;s/\s*$//')
	sshPort=$(grep -i "^ssh-port\s*=" $CONF_PATH/system.properties | cut -d= -f 2| sed 's/^\s*//;s/\s*$//')
	
	cd "${BASE}${webDir}/${web}"
	
	packageCount=$(find target/${web}-*.tar.gz -type f 2>&1|grep "No such file"|wc -l)
	if [ $packageCount -eq 1 ]; then
		bash $BASE/bin/lib/web-package.sh $web
	fi
	
	echo "scp -P $sshPort target/${web}-*.tar.gz $host:~/"
    scp -P $sshPort target/${web}-*.tar.gz $host:~/
    
    count=$(ssh -p $sshPort $host "find . -maxdepth 1 -name ${web} -type d"|wc -l)
   	if [ $count -gt 0 ];then
   		date=$(date +%Y%m%d%H%M%S)
   		ssh -p $sshPort $host "bash ~/${web}/bin/${webKillCmd};mkdir -p bak;"
   		
   		TESTOK="1"
		while [ "$TESTOK" == "1" ]; do
			sleep 1
			. $BASE/bin/test-run.sh "$host" "$testCmd" "$testResult" 
			echo -e ".\c"
		done
		ssh -p $sshPort $host "mv $web bak/${web}-$date;"
   	fi
   	
   	echo "ssh -p $sshPort $host \"tar zxf ${web}-*.tar.gz;rm -rf ${web}-*.tar.gz\""
   	ssh -p $sshPort $host "tar zxf ${web}-*.tar.gz;rm -rf ${web}-*.tar.gz"
   	
   	. $BASE/bin/lib/web-sync.sh $web $host
   	
   	echo "ssh -p $sshPort $host \"ln -sf ~/web/$web/web ~/$web/web;mkdir -p ~/logs/$web/access.log;mkdir -p ~/$web/logs;ln -sf ~/logs/$web/access.log ~/$web/logs/;bash ~/$web/bin/${webStartCmd};\""
	ssh -p $sshPort $host "ln -sf ~/web/$web/web ~/$web/web;mkdir -p ~/logs/$web/access.log;mkdir -p ~/$web/logs;ln -sf ~/logs/$web/access.log ~/$web/logs/;bash ~/$web/bin/${webStartCmd};"
fi
