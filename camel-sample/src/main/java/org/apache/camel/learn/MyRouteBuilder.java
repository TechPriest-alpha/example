package org.apache.camel.learn;

import org.apache.camel.Endpoint;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.bean.BeanEndpoint;
import org.apache.camel.component.http.HttpEndpoint;
import org.apache.camel.component.servlet.ServletEndpoint;

/**
 * A Camel Java DSL Router
 */
public class MyRouteBuilder extends RouteBuilder {

    /**
     * Let's configure the Camel routing rules using Java code...
     * to access via HTTP use GET <a href="http://localhost:8080/myapp/services/test">http://localhost:8080/myapp/services/test</a>
     */
    public void configure() {
        from("servlet:test?servletName=MyCamelServlet")
            .log("HTTP message")
            .multicast()
                .bean(MyPreprocessor.class)
                .to("file:target/messages")
                .to("direct:input1")
                .to("direct:input2")
            .end()
            .bean(MyPreprocessor.class)
            .to("file:target/messages1");

//        var endpoint = new BeanEndpoint();
//        endpoint.setBeanName("localSupplier");
//        endpoint.setMethod("supply");
        from("direct:input1")
            .log("Local input1 invoked")
            .bean(LocalProcessor1.class);

        from("direct:input2")
            .log("Local input2 invoked")
            .bean(LocalProcessor2.class);

        // here is a sample which processes the input files
        // (leaving them in place - see the 'noop' flag)
        // then performs content based routing on the message using XPath
//        from("file:src/data?noop=true")
//            .choice()
//                .when(xpath("/person/city = 'London'"))
//                    .log("UK message")
//                    .bean(MyPreprocessor.class)
//                    .to("file:target/messages/uk")
//                .otherwise()
//                    .log("Other message")
//                    .to("file:target/messages/others");
    }

}
