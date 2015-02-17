		数据倾斜data skew, uneven data distribution
		Shuffle:  shuffling is the process of transfering data from the mappers to the reducers
		Partition-wise join: 两个表使用同样的partition key和partition schema，当他们俩做join时，就可以直接在相同的bucket里面join, 没必要和其他的bucket的数据进行任何计算，因此效率更高.
		Boardcast/replicat/hash/bloom filter
		Salted table: 解决write hot point问题
		
		Map-Reduce(化简)
		
		
