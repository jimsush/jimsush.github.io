* resultset is not closed properly.
-------------------------
*A SP returned a cursor as output parameter to java application, but java application didn't use this cursor/resultset, the database cursor leak is happened.


*validation:
 ** added a counter to track resultset usage.
 
 
 sp output出来的cursor必须关闭. application很容易忘记这点. 
