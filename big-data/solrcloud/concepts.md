		Shard: Collection的逻辑分片。每个Shard被化成一个或者多个replicas，通过选举确定哪个是Leader. 每个shard可以分配一定量的heap,但也不能太多否则会影响到其他process.
		
		Leader: 赢得选举的Shard replicas。每个Shard有多个Replicas.
		
		Zookeeper提供分布式锁功能，对SolrCloud是必须的
		
		Replica: Shard的一个拷贝。每个Replica存在于Solr的一个Core中。
		
		http://tech.uc.cn/?p=2387   分布式全文检索系统SolrCloud简介
		
		8 shards, each shard have 2 nodes, one is leader, other is active node.
