
TableScanOperator (TS)扫描hive表数据ReduceSinkOperator (RS)创建将发送到Reducer端的<Key,Value>对JoinOperatorJoin两份数据SelectOperator选择输出列FileSinkOperator建立结果数据,输出至文件FilterOperator过滤输入数据GroupByOperatorGroupBy语句MapJoinOperator/*+mapjoin(t) */LimitOperatorLimit语句UnionOperatorUnion语句
Hive通过ExecMapper和ExecReducer执行MapReduce任务
CombineHiveInputFormat 
stage
合并减少多轮MR job
predicate pushdown
pruning

每个aggregation的实现细节:
groupby: hash 
join
distinct

Antlr , ast tree, queryblock, op tree, mr tasks
select: data evaluate

optimizer:
logical optimizer合并操作符，达到减少MapReduce Job，减少shuffle数据量
physical optimizer:
名称作用VectorizerHIVE-4160，将在0.13中发布SortMergeJoinResolver与bucket配合，类似于归并排序SamplingOptimizer并行order by优化器，在0.12中发布CommonJoinResolver + MapJoinResolverMapJoin优化器
在Map阶段将小表读入内存，顺序扫描大表完成Join

ReduceSinkOperator标示Map/Reduce的界限，多个Job间的界限
physical execution plan:
stage by RS
MapWork
ReduceWork
[图片]

执行计划分析:
explain
子查询

Hive 的启动方式
    hive  命令行模式，直接输入/hive/bin/hive的执行程序，或者输入 hive –service cli
    hive  web界面的启动方式，hive –service hwi  
    hive  远程服务 (端口号10000) 启动方式，nohup hive –service hiveserver  &

建表
    CREATE TABLE javabloger (foo INT, bar STRING);  
插入
    LOAD DATA LOCAL INPATH '/work/hive/examples/files/kv1.txt' OVERWRITE INTO TABLE javabloger;

Hive 与 JDBC 
导入hive\lib下的所有jar包到IDE的classpath里面，还有hadoop中的 hadoop-0.20.2-core.jar包
Class.forName("org.apache.hadoop.hive.jdbc.HiveDriver");
jdbc:hive2://localhost:10000/default

SQuirrel SQL Client的官网及下载地址为：http://squirrel-sql.sourceforge.net/
可以在http://squirrel-sql.sourceforge.net/#installation页面下载jar包squirrel-sql-3.7-standard.jar
下载后，双击squirrel-sql-3.7-standard.jar，即可启动安装程序

partition:
表中的一个 Partition 对应于表下的一个目录
单分区建表语句：create table day_table (id int, content string) partitioned by (dt string);单分区表，按天分区，在表结构中存在id，content，dt三列。
b、双分区(表文件夹下出现多文件夹嵌套模式)建表语句：create table day_hour_table (id int, content string) partitioned by (dt string, hour string);双分区表，按天和小时分区，在表结构中新增加了dt和hour两列。
表文件夹目录示意图（多分区表）：
[图片]
hive> show partitions day_hour_table;

Hive SQL运行状态监控（HiveSQLMonitor）
QueryStart、TaskStart、TaskProgress、TaskEnd（一个复杂的Query可能会产生多个Task）、QueryEnd覆盖整个查询的执行过程，通过对这些行日志的解析，我们就可以获取到Hive SQL的执行状态.
会话的日志文件存储在HiveServer的本地磁盘中，而实际应用中我们有多台HiveServer提供服务.
PreHook要求实现接口ExecuteWithHookContext对所有操作进行解析，监控.
QueryPlan, HiveConf等信息,查询语句，MR job信息... 
http://www.tuicool.com/articles/uY7F7f

安装与启动:
http://blog.csdn.net/xiao_qiang_/article/details/8443721
start hive server:hive --service hiveserver 50031（端口号）

hiveserver2是hiveserver的改进版本，相比而言，hiveserver2更加稳定，支持的功能更多
HiveServer不能处理多于一个客户端的并发请求，这是由于HiveServer使用的Thrift接口所导致的限制，不能通过修改HiveServer的代码修正。因此在Hive-0.11.0版本中重写了HiveServer代码得到了HiveServer2，进而解决了该问题。HiveServer2支持多客户端的并发和认证，为开放API客户端如JDBC、ODBC提供了更好的支持。
Hiveserver2允许在配置文件hive-site.xml中进行配置管理.
1	hive.server2.thrift.max.worker.threads – 最小工作线程数，默认为500。
2	hive.server2.thrift.port– TCP 的监听端口，默认为10000。
3	hive.server2.thrift.bind.host– TCP绑定的主机，默认为localhost。
http://www.88cto.com/997447/article/details/32399.html

start hive2: hive --service hiveserver2 --hiveconf hive.server2.thrift.port=10001

权限问题: hadoop用户来启动hive server2
timeout时间太短hive-site.xml  hive.server2.long.polling.timeout=5000
