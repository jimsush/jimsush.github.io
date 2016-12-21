bios boot from cd/dvd 
download ubuntu iso (the default linux.iso does not work at all)
network: NAT, virtual network editor, please note firewall and don't allow firewall to block the NAT driver

===============
start hadoop with 'hduser'

You have to format namenode when you run namenode at first time:
 $ hdfs namenode -format

namenode:  /home/tong/namenode/
datanode: /home/tong/datanode/

/usr/local/hadoop/bin

../sbin/hadoop-daemon.sh start namenode
datanode
../sbin/start-dfs.sh
../sbin/start-yarn.sh (resourcemanager, nodemanager)
jps


~/.bashrc
export JAVA_HOME=/usr/lib/jvm/java-8-openjdk-amd64/jre


/usr/local/bin/hadoop dfs -ls /test/data


sudo su hduser
/usr/local/hadoop/sbin/start-dfs.sh
/usr/local/hadoop/sbin/start-yarn.sh

sudo passwd (change root password)

1. HDFS
    1.NameNode  50070   http://192.168.88.129:50070
    2.SecondNameNode   50090
    3.TaskNode 50075

2. MapReduce
    1.JobTracker   50030
    2.TaskTracker  50060


sudo mysql -u root -p 
grant all privileges on *.* to 'root'@'localhost' identified by 'root';
mysql -u root -p

sudo service mysql start

insert into mysql.user(Host,User,authentication_string) values("localhost","hive",password("hive"));
create database hive;
grant all on hive.* to hive@'%'  identified by 'hive';
grant all on hive.* to hive@'localhost'  identified by 'hive';
flush privileges;


    <property>
        <name>javax.jdo.option.ConnectionURL</name>
        <value>jdbc:mysql://localhost:3306/hive?characterEncoding=UTF-8</value>
    </property>

    <property>
        <name>javax.jdo.option.ConnectionDriverName</name>
        <value>com.mysql.jdbc.Driver</value>
    </property>

    <property>
        <name>javax.jdo.option.ConnectionUserName</name>
        <value>hive</value>
    </property>

    <property>
        <name>javax.jdo.option.ConnectionPassword</name>
        <value>hive</value>
    </property>


export HIVE_HOME=/home/tong/hive
export PATH=$HIVE_HOME/bin:$PATH

sudo hduser
hive --service metastore
hive
$use default;
$create table test(id int, name string) ROW FORMAT DELIMITED FIELDS TERMINATED BY ',' STORED AS TEXTFILE;
$load data local inpath '/home/tong/files' into table test;
$select * from test;

