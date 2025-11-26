package org.apache.camel.learn;

import io.undertow.Handlers;
import io.undertow.Undertow;
import io.undertow.server.handlers.PathHandler;
import io.undertow.servlet.Servlets;
import io.undertow.servlet.api.DeploymentInfo;
import io.undertow.servlet.api.DeploymentManager;
import jakarta.servlet.ServletException;
import org.apache.camel.component.servlet.CamelHttpTransportServlet;
import org.apache.camel.main.Main;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A Camel Application
 */
public class MainApp {
    private static final Logger log = LoggerFactory.getLogger(MainApp.class);

    /**
     * A main() so we can easily run these routing rules in our IDE
     */
    public static void main(String... args) throws Exception {
        startWebServer();
        setupTemporalWorkflow();
        setupTemporalWorker();
        startApacheCamel(args);

    }

    private static void setupTemporalWorkflow() {

    }

    private static void setupTemporalWorker() {

    }

    private static void startApacheCamel(String[] args) throws InterruptedException {
        final var main = getMain(args);
        while (!main.isStarted()) {
            log.info("Not started");
            Thread.sleep(1000L);
        }
        log.info("Finally started");
    }

    private static Main getMain(String[] args) {
        final var main = new Main();
        Thread.startVirtualThread(() -> {
            try (var c = main.configure()) {
                c.addRoutesBuilder(new MyRouteBuilder());
                main.run(args);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });
        return main;
    }

    private static void startWebServer() throws ServletException {
        DeploymentInfo servletBuilder = Servlets.deployment()
            .setClassLoader(MainApp.class.getClassLoader())
            .setContextPath("/myapp")
            .setDeploymentName("test.war")
            .addServlets(
                Servlets.servlet("MyCamelServlet", CamelHttpTransportServlet.class)
                    .addMapping("/services/*")
            );

        DeploymentManager manager = Servlets.defaultContainer().addDeployment(servletBuilder);
        manager.deploy();
        PathHandler path = Handlers.path(Handlers.redirect("/myapp"))
            .addPrefixPath("/myapp", manager.start());

        Undertow server = Undertow.builder()
            .addHttpListener(8080, "localhost")
            .setHandler(path)
            .build();
        server.start();
    }

}

