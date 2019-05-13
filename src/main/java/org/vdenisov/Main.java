package org.vdenisov;

import io.undertow.Handlers;
import io.undertow.Undertow;
import io.undertow.server.HttpHandler;
import io.undertow.server.handlers.PathHandler;
import io.undertow.servlet.Servlets;
import io.undertow.servlet.api.DeploymentInfo;
import io.undertow.servlet.api.DeploymentManager;
import org.apache.wicket.protocol.http.WicketFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.vdenisov.wicket.MyWicketApp;

import javax.servlet.DispatcherType;
import javax.servlet.ServletException;
import java.util.concurrent.atomic.AtomicLongArray;

import static io.undertow.servlet.Servlets.defaultContainer;
import static io.undertow.servlet.Servlets.deployment;

@SpringBootConfiguration
public class Main {

    private static boolean startUndertow = true;

    public static void main(final String[] args) throws ServletException {
        SpringApplication.run(Main.class);

        if (startUndertow) {
            startUndertow();
        } else {
            seeDecompiler();
        }
    }

    private static void seeDecompiler() {
        AtomicLongArray o;
        final var i = 0;
        final var j = 100;
        final var k = -1;
        final var z = 190;
        final Main m = new Main();
        final int a0 = 1;
        m.x(a0);
        System.out.println(a0);
        final MyObj o1 = new MyObj();
        m.y(o1);
        System.out.println(o1.y);
        final byte x = 127;
        System.out.println(x + k);
        System.out.println(x + z);
    }

    private static void startUndertow() throws ServletException {
        final DeploymentInfo servletBuilder = deployment()
            .setClassLoader(Main.class.getClassLoader())
            .setContextPath("/")
            .setDeploymentName("test-wicket.jar")
            .addFilter(Servlets.filter("WicketFilter", WicketFilter.class)
                .setAsyncSupported(true)
                .addInitParam("applicationClassName", MyWicketApp.class.getName())
                .addInitParam("filterMappingUrlPattern", "/*")
            )
            .setEagerFilterInit(true)
            .addFilterUrlMapping("WicketFilter", "/*", DispatcherType.REQUEST)
            .addFilterUrlMapping("WicketFilter", "/*", DispatcherType.FORWARD)
            .addFilterUrlMapping("WicketFilter", "/*", DispatcherType.ASYNC);

        final DeploymentManager manager = defaultContainer().addDeployment(servletBuilder);
        manager.deploy();

        final HttpHandler servletHandler = manager.start();
        final PathHandler path = Handlers.path(Handlers.redirect("/"))
            .addPrefixPath("/", servletHandler);
        final Undertow server = Undertow.builder()
            .addHttpListener(8080, "localhost")
            .setHandler(path)
            .build();
        server.start();
    }


    public void x(int a) {
        a = 5;
    }

    public void y(final MyObj obj) {
        obj.y = 5;
    }

    private static class MyObj {
        int y;
    }
}
