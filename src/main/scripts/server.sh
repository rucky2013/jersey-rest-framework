#!/bin/bash
 
#全局变量
BASE_PATH=${BASE_PATH:-`cd "$(dirname "$0")"; pwd`}
APP_PATH=${APP_PATH:-`dirname "$BASE_PATH"`}

#设置java运行参数
#DEFAULT_JAVA_OPTS=" -server -Xmx1g -Xms256m  -XX:PermSize=128m -Xss256k -XX:+DisableExplicitGC -XX:+UseConcMarkSweepGC -XX:+CMSParallelRemarkEnabled -XX:+UseCMSCompactAtFullCollection -XX:LargePageSizeInBytes=128m -XX:+UseFastAccessorMethods -XX:+UseCMSInitiatingOccupancyOnly -XX:CMSInitiatingOccupancyFraction=70 -Xdebug -Xnoagent -Xrunjdwp:transport=dt_socket,address=11088,server=y,suspend=n"
GC_OPTS=" -server -Xms512m -Xmx1g -Xmn128m -XX:PermSize=128m -Xss256k -XX:+DisableExplicitGC -XX:+UseConcMarkSweepGC -XX:+CMSParallelRemarkEnabled -XX:+UseCMSCompactAtFullCollection -XX:+UseCMSInitiatingOccupancyOnly -XX:CMSInitiatingOccupancyFraction=60"
GC_LOG_OPTS=" -verbose:gc  -XX:+PrintGCDateStamps -XX:+PrintTenuringDistribution  -XX:+PrintGCDetails -Xloggc:./logs/gc.log"

#应用客户化变量
. $APP_PATH/conf/server.properties

DEFAULT_JAVA_OPTS="$GC_OPTS $GC_LOG_OPTS $CUST_OPT"


#Java变量
CLASS_PATH=${CLASS_PATH:-$APP_PATH/extlib}
JAVA_OPTS=${JAVA_OPTS:-$DEFAULT_JAVA_OPTS}
MAIN_CLASS=${MAIN_CLASS:-"./lib/$package-$version.jar $port $contextPath"}


exist(){
			if test $( pgrep -f "$MAIN_CLASS" | wc -l ) -eq 0 
			then
				return 1
			else
				return 0
			fi
}

start(){
		
		echo "apppath: $APP_PATH"
		if exist; then
				echo "$package is already running."
				exit 1
		else
	    	cd $APP_PATH
	    	echo "Current path:$APP_PATH"
	    	echo "Startup Command: nohup java $JAVA_OPTS -cp $CLASS_PATH -jar $MAIN_CLASS 2> /dev/null &"
			nohup java $JAVA_OPTS -cp $CLASS_PATH -jar $MAIN_CLASS 2> /dev/null & 
			echo "$package is started."
		fi
}

stop(){
		runningPID=`pgrep -f "$MAIN_CLASS"`
		if [ "$runningPID" ]; then
				echo "$package pid: $runningPID"
        count=0
        kwait=5
        echo "$package is stopping, please wait..."
        kill -15 $runningPID
					until [ `ps --pid $runningPID 2> /dev/null | grep -c $runningPID 2> /dev/null` -eq '0' ] || [ $count -gt $kwait ]
		        do
		            sleep 1
		            let count=$count+1;
		        done

	        if [ $count -gt $kwait ]; then
	            kill -9 $runningPID
	        fi
        clear
        echo "$package is stopped."
    else
    		echo "$package has not been started."
    fi
}

check(){
   if exist; then
   	 echo "$package is alive."
   	 exit 0
   else
   	 echo "$package is dead."
   	 exit -1
   fi
}

restart(){
        stop
        start
}

case "$1" in

start)
        start
;;
stop)
        stop
;;
restart)
        restart
;;
check)
        check
;;
*)
        echo "available operations: [start|stop|restart|check]"
        exit 1
;;
esac