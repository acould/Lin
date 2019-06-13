#!/bin/bash
pushd `dirname $0`/../.. > /dev/null
BASE=`pwd`
popd > /dev/null
cd $BASE

if [ "x${CONF_PATH}x" == "xx" ];then
	CONF_PATH="$BASE/bin/conf"
fi

profile=$(grep -i "^service-profile\s*=" $CONF_PATH/system.properties | cut -d= -f 2| sed 's/^\s*//;s/\s*$//')

PRODUCT_FILE=$BASE/filter/filter-product.properties

if [ "$profile" == "runtime" ]; then
	PRODUCT_FILE=$BASE/filter/filter-run.properties
elif [ "$profile" == "test" ]; then
	PRODUCT_FILE=$BASE/filter/filter-test.properties
elif [ "$profile" == "test2" ]; then
	PRODUCT_FILE=$BASE/filter/filter-test2.properties
fi

path=$(grep -i "^config-path\s*=" $CONF_PATH/system.properties | cut -d= -f 2| sed 's/^\s*//;s/\s*$//')
testFile="$CONF_PATH/$path"

if [ "x${path}x" != "xx" ]; then
	git reset --hard
fi

git pull

if [ -f "$testFile" ]; then
	count=$(awk 'END{print NR}' $testFile)
	for ((i=1;i<=$count;i++));do
		line=`cat $testFile | sed -n ''$i'p' | sed 's/^\s*//;s/\s*$//'`
		if [ "x${line}x" != "xx" ]; then
			gc=$(echo $line|awk -F'=' '{ print NF }')
			if [ $gc -ge 2 ]; then
				value=$(echo ${line#*=}|sed -e 's/^\s*//;s/\s*$//;s#&#\\&#g')
				field=$(echo ${line%%=*}|sed 's/^\s*//;s/\s*$//')
				#echo sed -i \"s#^$field=.*#$field=$value#\" $PRODUCT_FILE
				sed -i "s#^$field=.*#$field=$value#" $PRODUCT_FILE
			fi
		fi
	done
	dos2unix $PRODUCT_FILE
	echo "config is replace ok!"
else
	echo "config is ok!"
fi
