request file:

```xml
<?xml version="1.0" encoding="UTF-8"?> 
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:target="http://www.abc.com/ns"> 
    <soapenv:Body>
        <target:method1>
		    <arg0>aaa</arg0>
	</target:method1>
    </soapenv:Body>
</soapenv:Envelope>
```

Simulation class:

```scala
import scala.concurrent.duration._
import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._
import io.gatling.http.request._

class MyTest extends Simulation {

    val header = Map(
    "accept-encoding" -> "gzip, deflate", 
    "Content-Type" -> "text/xml;charset=UTF-8", 
    "accept-language" -> "en-US,en;q=0.8", 
    "user-agent" -> "Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 6.1; WOW64; Trident/7.0; SLCC2; .NET CLR 2.0.50727; .NET CLR 3.5.30729; .NET CLR 3.0.30729; Media Center PC 6.0; InfoPath.3; .NET4.0C; .NET4.0E)")

    val scn = scenario("MyTest")
            .exec(http("soap_post")
			.post("http://www.abc.com:8080/ws/rpc")
			.body(ElFileBody("soap.xml")).asXML
			.headers(header)
			.check(bodyString.saveAs("myres")))
			.exec(session => {
	   	       val myres1=session.get("myres").asOption[String]
			   println(myres1.getOrElse(""))
			   session
			})
			
    setUp(scn.inject(atOnceUsers(1)))
}
```

