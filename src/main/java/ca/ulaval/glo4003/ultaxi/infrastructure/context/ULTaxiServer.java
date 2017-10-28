package ca.ulaval.glo4003.ultaxi.infrastructure.context;

import ca.ulaval.glo4003.ultaxi.http.CORSResponseFilter;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer;

import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Application;
import java.net.URI;
import java.util.Set;

public class ULTaxiServer {

    private final Server server;

    public ULTaxiServer(Set<Object> resources, Set<ContainerRequestFilter> requestFilters, ULTaxiOptions options) {
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/api/");
        ResourceConfig resourceConfiguration = createResourceConfiguration(resources, requestFilters);

        ServletContainer servletContainer = new ServletContainer(resourceConfiguration);
        ServletHolder servletHolder = new ServletHolder(servletContainer);
        context.addServlet(servletHolder, "/*");

        ContextHandlerCollection contexts = new ContextHandlerCollection();
        contexts.setHandlers(new Handler[]{context});
        server = new Server(options.getServerPort());
        server.setHandler(contexts);
    }

    private ResourceConfig createResourceConfiguration(Set<Object> resources, Set<ContainerRequestFilter>
        requestFilters) {
        ResourceConfig resourceConfiguration = ResourceConfig.forApplication(new Application() {
            @Override
            public Set<Object> getSingletons() {
                return resources;
            }
        });
        resourceConfiguration.register(CORSResponseFilter.class);
        requestFilters.forEach(resourceConfiguration::register);

        return resourceConfiguration;
    }

    public String getBaseURI() {
        URI uri = server.getURI();
        return String.format("%s://%s", uri.getScheme(), uri.getHost());
    }

    public int getPort() {
        return server.getURI().getPort();
    }

    public void join() throws Exception {
        server.join();
    }

    public void start() throws Exception {
        server.start();
    }

    public void tearDown() throws Exception {
        server.stop();
        server.join();
        server.destroy();
    }
}
