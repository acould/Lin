#!/bin/bash

cd `dirname $0`/..
BASE=`pwd`

PROJECT_PATH=$BASE
export SERVICE_NAME=${BASE##*/}
echo "killws SERVICE_NAME=$SERVICE_NAME"

. "$BASE/bin/setenv.sh"

if [ "$ISRUN" == "1" ]; then
	kill $(cat $BASE/logs/pids.log/${SERVICE_NAME}.pid)
fi

echo "Stop ${SERVICE_NAME}..."
