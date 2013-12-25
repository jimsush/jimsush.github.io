## 模式与连接URL  
[H2数据库使用](http://blog.csdn.net/yixiaoping/article/details/9801397)讲得挺详细

### In-memory mode 不持久化
不建磁盘文件，也不可以跨进程共享数据  
jdbc:h2:tcp://localhost/mem:dbname

### Embedded mode 单进程模型
jdbc:h2:yourpathOfDB, jdbc:h2:~/dbname...  

### Client-server mode 多进程模型  
jdbc:h2:tcp://hostname:port/~/dbname   
还支持ssl/tls, jdbc:h2:ssl//hostname:port/~/dbname    
集群工具CreateCluster,需要启动多个实例和CreateCluster进程。    


## Server
### TCP server(native H2 database server protocol)   
允许有多个数据库，多个客户端，同一个数据库可以有多个客户端。  
```java
Server.createTcpServer(new String[] { "-tcpPort", "12345" }).start(); 
```

### Web server(H2 web console)
org.h2.server.web.WebServlet 

###PG server(PostgreSQL protocol)


## 启动说明与持久化文件
in-memory/embedded mode下，即使没启动任何tcp等server，依然是可以进行数据库操作的，但不能在多个进程间使用同一个数据库jdbc连接字符串。    
每个embedded mode数据库，都会自动创建yourdb.h2.db和yourdb.trace.db两个文件。这里可以保存数据库的schema和持久化数据，通过file lock防止在embedded mode下多进程都打开同一数据库。

