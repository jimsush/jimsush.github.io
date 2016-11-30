kylin source code
https://kylin.apache.org/cn/download/

realtime的处理有不少地方借鉴了druid, 从kafka extract message, 按时间片划分segment等，不过步子比druid快，不仅仅是支持inverted index, bitmap index,还支持mr/spark做很多其他的处理,并store到hbase.

