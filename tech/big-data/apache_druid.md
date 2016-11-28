star schema
Focus on OLAP (not search)

Druid是Rollup，列存储加索引，倒排索引以及Bitmap索引

realtime+batch, Lambda architecture

realtime node or indexing service: LSM approach, cache indexes in memory and spill to disk after it is full.
rollup when data importing/streaming
data ingestion is very fast

design for rt MOLAP analytics but don't support standard sql(imply plysql)

Segment: inverted index, bitmap index

historical/batch: slice by time lines; mmap

vs. k-v store: index and column storage;
faster than spark
faster than sql-on-hadoop(MPP solution): sql-on-hadoop has serde issue, hdfs bottleneck, 
vs. ES: don't need to support complicated search that supported in Lucene and ES, it is for analytics, so it just need to support bool query.
vs. Kylin: druid star schema; kylin可以拉成wide table.
vs. presto: 在查询成本上，Presto是最好的，因为几乎不需要做什么特殊的处理，基本上Hive能读的数据Presto也都能读，所以这个成本非常低。Druid和Kylin的成本相对较高，因为都需要提前的预计算，尤其是Kylin如果维度数特别多，而且不做特别优化的话，数据量还是很可观的。


Q: Druid不支持join，只好把表设计得很宽。假设有几千列的话，会不会对性能影响很大？有没有什么好的办法？
A:对 Druid适合星型模型，其lookup功能可以实现和维度表join。对Druid性能影响较大的是Rollup，维度的个数，维度的基数以及维度组合的稀疏决定了rollup的效果。举个例子，我们的报表中原始数据最多的有50列，但我只取16个维度，而且维度的基数不大，这样十几亿条数据，rollup以后只有500万左右。
