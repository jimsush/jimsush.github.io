bios boot from cd/dvd 
download ubuntu iso (the default linux.iso does not work at all)
network: NAT, virtual network editor, please note firewall and don't allow firewall to block the NAT driver

===============
start hadoop with 'hduser'

第一次启动hadoop服务之前，必须执行格式化namenode

$ hdfs namenode -format

namenode:  /home/sufeng/namenode/
datanode: /home/sufeng/datanode/

/usr/local/hadoop/bin

../sbin/start-dfs.sh
start-yarn.sh
jps

~/.bashrc
export JAVA_HOME=/usr/lib/jvm/java-8-openjdk-amd64/jre


/usr/local/bin/hadoop dfs -ls /test/data


sudo su hduser
/usr/local/hadoop/sbin/start-dfs.sh
/usr/local/hadoop/sbin/start-yarn.sh

sudo passwd (修改root password)

一、HDFS
    1.NameNode 默认端口   50070   http://192.168.88.129:50070
    2.SecondNameNode 默认端口   50090
    3.TaskNode 默认端口   50075

二、MapReduce
    1.JobTracker 默认端口   50030
    2.TaskTracker 默认端口   50060
