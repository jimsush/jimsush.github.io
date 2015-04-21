# `Su的记事本`

# <span id="top1">Index与CPU使用率的故事</span>

### 背景   
我们做的是一个基于内存的`NoSQL`分布式缓存系统，虽然是`NoSQL`的系统，我们依然支持二级索引，Index是个好东西，它能够大大加快查询的速度，一个设计良好的Index能够带来性能极大的提升，但有时不合适的Index也会让你的系统性能严重下降。

### Case 1: 有Index时，过于频繁的Update导致CPU奇高   
某些数据是实时高频交易数据，同一条记录在短时间内会更新很多次，此时我们系统的CPU非常高，仔细分析后发现CPU的大部分时间被消耗在某个Index的update上。这个字段记录交易的`status`，`字符型`，只有几个`枚举值`，我们的二级索引是基于`Map<String,List>`的，结果每次更新status时，都会把他从old status对应的`List`中遍历出来，并删除，然后放入map的new status对应的`List`中，因为update过于频繁，对这些list的操作也非常频繁，不停的遍历，删除，插入，导致CPU使用率非常高。因为这个status是枚举型的，离散度不高，建index没有太多必要，更为郁闷的是，根本就没有query要用到这个index field，也就是说以前建index,更新index都是白费的，而他们还导致了CPU使用率居高不下。后来去掉index后，CPU马上下来，也没影响到query。

### Case 2: 无Index时，过于频繁的Query导致CPU奇高  
最近一段时间，某个缓存程序的进程的CPU常常1800%（多核），看上去特别吓人，而且影响到其他线程，甚至其他进程。后来发现在这段时间有大量的`Query`,这类query语句`where`条件字段很多，但都不是index field，导致要`遍历所有数据`，每个都要进行`复杂的比较`，最后CPU一下就上去了。这类查询一执行完，CPU马上恢复正常。后来增加了index，当这些大量查询过来后，因为不再有全表扫描和遍历，所以CPU使用率就没怎么上去，一切表现得非常完美。

### 总结    
因为不是数据库，`二级索引`机制都要靠自己实现，在使用的过程中碰到了太多的坑，通过这些问题，也逐步加深了对index的理解，并不断完善index的实现，虽然有时也挺郁闷，但解决后感觉还是不错的。



Tags: `Index` `CPU Usage`


  
  
-------------     
  [Back to home](http://jimsush.github.io/index.html)


