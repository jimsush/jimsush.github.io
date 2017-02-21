Thread.interrupt() static方法是把current thread设置中断状态

thread.isInterrupted() non-static是判断指定thread是否设置过interrupt状态

thread.state:
 . New
 . blocked (wait for sync monitor)
 . waitting (sleep)
 . timed_waiting (park waiting for time)
 . Runnable
 . Terminated
 
 
