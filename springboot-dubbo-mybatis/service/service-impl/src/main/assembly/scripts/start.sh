#!/bin/bash
# 	running  shell
#		20160718
echo -e " running  shell by sccot.feng"
cd `dirname $0`
BIN_DIR=`pwd`
cd ..
DEPLOY_DIR=`pwd`

# classpath 
CLASSPATH=$DEPLOY_DIR/classes
CLASSPATH="$CLASSPATH":"$DEPLOY_DIR"/lib/*


#jmx_port=4100
APP_MAINCLASS="com.lifeng.ServiceImplApplication"

SERVER_NAME='springboot-dubbo-mybatis-service-impl'
#SERVER_PORT=20880

PIDS=`ps -ef | grep java | grep "$DEPLOY_DIR" |awk '{print $2}'`
if [ -n "$PIDS" ]; then
    echo "ERROR: The $SERVER_NAME already started!"
    echo "PID: $PIDS"
    exit 1
fi

LOGS_DIR=$DEPLOY_DIR/nohup/

if [ ! -d $LOGS_DIR ]; then
    mkdir $LOGS_DIR
fi
STDOUT_FILE=$LOGS_DIR/nohup.log


#JAVA_JMX_OPTS=" -Dcom.sun.management.jmxremote.port=$jmx_port -Dcom.sun.management.jmxremote.ssl=false -Dcom.sun.management.jmxremote.authenticate=false "

JAVA_OPTS=" -server -Xms1g -Xmx1g -Xmn256m -Xss900k -XX:MetaspaceSize=512m -XX:MaxMetaspaceSize=512m -XX:+ExplicitGCInvokesConcurrent -verbosegc  -XX:+UseParNewGC -XX:+UseConcMarkSweepGC -XX:CMSInitiatingOccupancyFraction=80  -XX:+PrintGCDetails  -XX:MaxTenuringThreshold=15 -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=$LOGS_DIR -Xloggc:$LOGS_DIR/jvm.log -Djava.security.egd=file:/dev/./urandom"

#JAVA_BIZ_OPTS=" -Denv=dev -Ddev_meta=http://120.76.141.19:8092 "
#echo $JAVA_OPTS

echo -e "Starting the $SERVER_NAME ...\c"
#nohup $JAVA_HOME/bin/java $JAVA_OPTS $JAVA_BIZ_OPTS $JAVA_JMX_OPTS  -classpath $CLASSPATH $APP_MAINCLASS  > $STDOUT_FILE 2>&1 &

nohup $JAVA_HOME/bin/java $JAVA_OPTS $JAVA_BIZ_OPTS -classpath $CLASSPATH $APP_MAINCLASS  > $STDOUT_FILE 2>&1 &

PIDS=`ps -ef | grep java | grep "$DEPLOY_DIR" | awk '{print $2}'`
if [ -n "$PIDS" ]; then
    echo "OK!"
    echo "PID: $PIDS"
fi
echo "STDOUT: $STDOUT_FILE"
