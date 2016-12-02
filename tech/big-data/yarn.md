
http://blog.csdn.net/iloveyin/article/details/30060017
yarn (coraca)
mesos DCOS（数据中心操作系统）
torca Typhoon
omega google (G3)
Linux container
borg (google) http://www.infoq.com/cn/articles/docker-container-cluster-management-part-01
kubernetes 容器管理平台, borg同源

YARN:
The ResourceManager has two main components: Scheduler and ApplicationsManager.
scheduler: allocating resources; capacityscheduler多队列, fairscheduler, pluggable
AM: job-submission; 
资源进行调度和隔离; 支持内存和CPU两种资源的调度
ResourceManager中的调度器负责 资源的分配，而NodeManager则负责资源的供给和隔离。ResourceManager将某个NodeManager上资源分配给任务后，NodeManager需按照要求为任务提供相应的资源，甚至保证这些资源应具有独占性，为任务运行提供基础的保证，这就是所谓的 资源隔离。
YARN采用了线程监控的方法判断任务是否超量使用内存,这比cgroups更灵活.
http://blog.csdn.net/sunmenggmail/article/details/42418221
Container是YARN中的资源抽象，它封装了某个节点上的多维度资源，如内存、CPU、磁盘、网络等， 他不同于MRv1的map slot/reduce slot这种划分，是动态的。
YARN资源隔离使用的是cgroups.




[图片]
第一代是独立的集群(MRv1 job tracker)
第二代是两层调度（mesos,YARN）
第三带是共享状态调度omega


Hortonworks在去年启动的项目kubernetes-yarn
将kubernetes的scheduler改造成一个的YARN unmanaged AM，每次为pod分配机器都转向YARN RM提交Application。YARN为Application分配了什么机器，kubernetes scheduler就为pod返回什么机器，其他流程都跟native kubernetes保持一致.
实际pod的部署是由kubernetes来负责，不再经过YARN，所以需要向YARN提交一个fake的Application来hold住资源。这里用一个简单的sleep死循环来执行该Application，即实际上不占资源，只是逻辑上防止YARN把资源回收回去

kubernetes宣布1.2版本已经可以支持1000+节点的群集
[图片]
Kubernetes通过一组规则，为每一个未调度的Pod选择一个主机，如调度流程中介绍，Kubernetes的调度算法主要包括两个方面，过滤主机和主机打分. LeastRequestedPriority, BalancedResourceAllocation, SelectorSpreadPriority, CalculateAntiAffinityPriority, ImageLocalityPriority, NodeAffinityPriority, 
Kubernetes包含多种资源限制，用来控制Container、Pod和多租户级别的资源共享; Resource Quota是在命名空间级别设置的资源限制.
Kubernetes正在开发中的QoS借鉴Borg的成熟经验，重点关注提高集群资源利用率的问题.

像Borg、Kubernetes以及Mesos这类项目，它们把系统中所有需要对象都抽象成了一种“资源”保存在各自的分布式键值存储中，而管理员则使用如上所示的“资源描述文件”来进行这些对象的创建和更新。这样，整个系统的运行都是围绕着“资源”的增删改查来完成的，各组件的主循环遵循着“检查对象”、“对象变化”、“触发事件”、“处理事件”这样的周期来完成用户的请求。这样的系统有着一个明显的特点就是它们一般都没有引入一个消息系统来进行事件流的协作，而是使用“ectd”或者“Zookeeper”作为事件系统的核心部分。
Kubernetes与Borg从表面上看非常相似：相同的架构，相似的调度算法，当然还有同一伙开发人员。但是一旦我们去深入一些细节就会发现，在某些重要的设计和实现上，Borg似乎有着和Kubernetes截然不同的认识：比如完全相反的资源汇报方向，复杂度根本不在一个水平上的Master实现（集群VS单点），对batch job的支持（Kubernetes目前不支持batch job），对于任务优先级和资源抢占的看法等等。
