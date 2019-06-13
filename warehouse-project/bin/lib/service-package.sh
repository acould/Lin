#!/bin/bash
pushd `dirname $0`/../.. > /dev/null
BASE=`pwd`
popd > /dev/null
cd $BASE

if [ "x${CONF_PATH}x" == "xx" ];then
	CONF_PATH="$BASE/bin/conf"
fi

service=$1

serviceConf=$(grep -i "^${service}\s*=" $BASE/bin/conf/service.properties | cut -d= -f 2| sed 's/^\s*//;s/\s*$//')

bash $BASE/bin/lib/initconf.sh

if [ "x${serviceConf}x" != "xx" ]; then
    serviceNamePath=$(grep -i "^service-name-path\s*=" $CONF_PATH/system.properties | cut -d= -f 2| sed 's/^\s*//;s/\s*$//')
	serviceProfile=$(grep -i "^service-profile\s*=" $CONF_PATH/system.properties | cut -d= -f 2| sed 's/^\s*//;s/\s*$//')
		
	gitVersion=$(git rev-parse HEAD)
	
	PL="${serviceNamePath}:${service}";
	
	otherProfile=""
	if [ "x${serviceProfile}x" != "xx" ];then
		otherProfile="-P${serviceProfile}"
	fi
	
	mvn -U -am clean install -pl $PL $otherProfile,publish -Dgit.version=${gitVersion}
fi
