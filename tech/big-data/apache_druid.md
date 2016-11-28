star schema
Focus on OLAP (not search)

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
vs ES: don't need to support complicated search that supported in Lucene and ES, it is for analytics, so it just need to support bool query.
