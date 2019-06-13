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

	webDir=$(grep -i "^web-dir\s*=" $CONF_PATH/system.properties | cut -d= -f 2| sed 's/^\s*//;s/\s*$//')
	sshPort=$(grep -i "^ssh-port\s*=" $CONF_PATH/system.properties | cut -d= -f 2| sed 's/^\s*//;s/\s*$//')
	
	cd "${BASE}${webDir}${web}"
	
	git pull

	ssh -p $sshPort $host "mkdir -p ~/web/$web"

	rsync -avvP --exclude .svn -e "ssh -p $sshPort" web $host:~/web/$web
fi
