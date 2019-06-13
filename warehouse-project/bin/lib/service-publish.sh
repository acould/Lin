#!/bin/bash
pushd `dirname $0`/../.. > /dev/null
BASE=`pwd`
popd > /dev/null
cd $BASE

if [ "x${CONF_PATH}x" == "xx" ];then
	CONF_PATH="$BASE/bin/conf"
fi

service=$1
host=$2

serviceConf=$(grep -i "^${service}\s*=" $BASE/bin/conf/service.properties | cut -d= -f 2| sed 's/^\s*//;s/\s*$//')

if [ "x${host}x" == "xx" ]; then
	echo "bin/service-publish.sh service host"
	exit 0;
fi

if [ "x${serviceConf}x" != "xx" ]; then	
	testCmd=$(echo $serviceConf |awk -F';' '{print $1}')
	testResult=$(echo $serviceConf |awk -F';' '{print $2}')

	serviceDir=$(grep -i "^service-dir\s*=" $CONF_PATH/system.properties | cut -d= -f 2| sed 's/^\s*//;s/\s*$//')
	sshPort=$(grep -i "^ssh-port\s*=" $CONF_PATH/system.properties | cut -d= -f 2| sed 's/^\s*//;s/\s*$//')
	
	cd "${BASE}${serviceDir}/${service}"

	packageCount=$(find target/${service}-*.tar.gz -type f 2>&1|grep "No such file"|wc -l)
	if [ $packageCount -eq 1 ]; then
		bash $BASE/bin/lib/service-package.sh $service
	fi
	
	echo "scp -P $sshPort target/${service}-*.tar.gz $host:~/"
    scp -P $sshPort target/${service}-*.tar.gz $host:~/
    
    count=$(ssh -p $sshPort $host "find . -maxdepth 1 -name ${service} -type d"|wc -l)
    if [ $count -gt 0 ];then
    	date=$(date +%Y%m%d%H%M%S)
    	
    	echo "ssh -p $sshPort $host \"bash ~/$service/bin/killws.sh;mkdir -p bak;sleep 3;mv $service bak/$service-$date\""
    	ssh -p $sshPort $host "bash ~/$service/bin/killws.sh;mkdir -p bak;"
    	
    	TESTOK="1"
		while [ "$TESTOK" == "1" ]; do
			sleep 1
			. $BASE/bin/test-run.sh "$host" "$testCmd" "$testResult" 
			echo -e ".\c"
		done
		ssh -p $sshPort $host "mv $service bak/$service-$date"
    fi
    
    echo "ssh -p $sshPort $host \"tar zxf ${service}-*.tar.gz;rm -rf ${service}-*.tar.gz;\""
    ssh -p $sshPort $host "tar zxf ${service}-*.tar.gz;rm -rf ${service}-*.tar.gz;"
    
    echo "ssh -p $sshPort $host \"bash ~/$service/bin/startws.sh;\""
    ssh -p $sshPort $host "bash ~/$service/bin/startws.sh;"
fi