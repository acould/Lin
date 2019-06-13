#!/bin/sh

BASE=$PROJECT_PATH

if [ -z "$PROJECT_PATH" ] ; then
	cd `dirname $0`/..
	BASE=`pwd`
fi

SYSTEM_ROOT=/usr/local

if [ -d $BASE/conf ];then
	CONF_PATH=$BASE/conf
	LOG_PATH="$BASE/../logs/${BASE##*/}"
else
	CONF_PATH=$BASE/target/conf
	LOG_PATH="$BASE/logs"
fi

HTTPPID=$BASE/logs/pids.log/httpd.pid

LOGPID=$BASE/logs/pids.log/log.pid
ERRPID=$BASE/logs/pids.log/err.pid

mkdir -p $LOG_PATH/access.log

LOGFILE=$LOG_PATH/access.log/log.pipe
ERRORFILE=$LOG_PATH/access.log/err.pipe

ERROR=0
ARGV="$@"
if [ "x$ARGV" = "x" ] ; then 
	ARGS="help"
fi

for ARG in $@ $ARGS
do
	if [ -f $HTTPPID ] ; then
		PID=`cat $HTTPPID`
		if [ "x$PID" != "x" ] && kill -0 $PID 2>/dev/null ; then
			STATUS="httpd (pid $PID) running"
			RUNNING=1
		else
			STATUS="httpd (pid $PID?) not running"
			RUNNING=0
		fi
	else
		STATUS="httpd (no pid file) not running"
		RUNNING=0
	fi
	
	case $ARG in
	start)
		if [ $RUNNING -eq 1 ]; then
			echo "$0 $ARG: httpd (pid $PID) already running"
			continue
		fi
		rm -rf $LOGFILE $ERRORFILE
		mkfifo $LOGFILE
		mkfifo $ERRORFILE
		$SYSTEM_ROOT/sbin/cronolog $LOG_PATH/access.log/log_%Y%m%d.log < $LOGFILE &
		echo $! > $LOGPID
		$SYSTEM_ROOT/sbin/cronolog $LOG_PATH/access.log/err_%Y%m%d.log < $ERRORFILE &
		echo $! > $ERRPID
		
		echo "Start HTTPD..."
		
		$SYSTEM_ROOT/nginx/sbin/nginx -c $CONF_PATH/httpd.conf -p $BASE/
	;;
	stop)
		if [ $RUNNING -eq 0 ]; then
		    echo "$0 $ARG: $STATUS"
		    continue
		fi
		
		echo "Stop HTTPD..."
		$SYSTEM_ROOT/nginx/sbin/nginx -c $CONF_PATH/httpd.conf -p $BASE/ -s stop
		kill -9 `cat "$LOGPID"` >/dev/null 2>&1
		kill -9 `cat "$ERRPID"` >/dev/null 2>&1
		rm -rf $LOGPID
		rm -rf $ERRPID
	;;
	restart)
		if [ $RUNNING -eq 0 ]; then
			echo "$0 $ARG: httpd not running, trying to start"
			$BASE/bin/httpd.sh start
		else
			echo "Restart HTTPD..."
		
			$SYSTEM_ROOT/nginx/sbin/nginx -c $CONF_PATH/httpd.conf -p $BASE/ -s reload
		fi
	;;
	test)
		$SYSTEM_ROOT/nginx/sbin/nginx -c $CONF_PATH/httpd.conf -p $BASE/ -t
	;;
	*)
	echo "usage: $0 (start|stop|restart|test|help)"
	cat <<EOF

start			- start httpd
stop			- stop httpd
restart		- restart httpd if running by sending a SIGHUP or start if not running
test			- do a configuration syntax test
help			- this screen

EOF
	ERROR=2
    ;;
    
    esac
    
done

exit $ERROR
