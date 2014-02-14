页面渲染不要在后台做，后台只做业务逻辑，利用各层cache的优势来优化效率
前后台分工更加明确
哪些数据cache到browser(压缩,cookie，这块不太熟悉),哪些到dns, cdn,哪些到app cache server?
web server的改造
jvm/os的改造, 比如firewall, ddos, memory

cache需要统一的接口，按business来切分服务器，或者其他。
提供monitoring(同一接入层,core), manage, performance tuning, cache hit rate的功能
提供subscribe给外部系统提供search, big data, statistics的功能，核心业务与外围系统分离

识别需求，比如是否sharding，建立index，对数据的evction策略，常常会影响到performance

async/web service/json的问题在于，数据膨胀，设计合适的数据结构非常重要，基于business优化， 同时又要兼顾generic的需求
cache json还是直接cache整个页面
web service的设计问题，可能太多，管理上带来问题，历史遗留问题，同一页面数据变化后因为web call过多导致的数据显示不一致的问题

哪些数据该进cache，哪些update得比较频繁，哪些数据之间有关联，这样都可以平衡各个jvm的perfomance和resource的应用

jdbc接口是否需要?

如何scale out? scale up? sharding, 一致性hash

如何fail over?

Qos, flow control, defensive/protect yourself/DDOS, entitlement, self service...

网络瓶颈：多机房，跨地区? 延时如何处理？如何同步? 万兆网卡＋就近原则(配置文件)
传输问题：序列化优化，压缩，自定义序列化，多版本的设计
磁盘：ssd，异步读写，块读写
oracle数据库的问题，连接不够，cpu过高，索引问题，数据过多，维护的问题
memory的问题：黑盒子，heap，优化，streaming api缓解内存使用，引用代替new，压缩指针
事务的问题：无事务，软肋。
search：如何做索引（选择性的），
展示：数据的读取，多表数据的处理，cache，web service，前台的统一框架(gwt, ext js, easy ui,bootstrap, flex...)
