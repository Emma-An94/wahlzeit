package org.wahlzeit_revisited;

import jakarta.ws.rs.core.UriBuilder;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.http.server.StaticHttpHandler;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.wahlzeit_revisited.main.DatabaseMain;
import org.wahlzeit_revisited.model.PhotoFactory;
import org.wahlzeit_revisited.model.UserFactory;
import org.wahlzeit_revisited.repository.PhotoRepository;
import org.wahlzeit_revisited.repository.UserRepository;
import org.wahlzeit_revisited.service.PhotoService;
import org.wahlzeit_revisited.service.Transformer;
import org.wahlzeit_revisited.service.UserService;
import org.wahlzeit_revisited.utils.SysConfig;
import org.wahlzeit_revisited.utils.WahlzeitConfig;

import java.net.URI;


public class Wahlzeit {

    private static final WahlzeitConfig sysConfig = new SysConfig(); // Global config

    private static class ServiceInjectBinder extends AbstractBinder {
        @Override
        protected void configure() {
            // Setup @inject annotation -> bind(InjectClass).to(ImplClass)
            bind(sysConfig).to(WahlzeitConfig.class);
            // factory
            bind(UserFactory.class).to(UserFactory.class);
            bind(PhotoFactory.class).to(PhotoFactory.class);
            // service
            bind(Transformer.class).to(Transformer.class);
            bind(PhotoService.class).to(PhotoService.class);
            bind(UserService.class).to(UserService.class);
            // repository
            bind(UserRepository.class).to(UserRepository.class);
            bind(PhotoRepository.class).to(PhotoRepository.class);
        }
    }

    public static void main(String[] args) throws Exception {
        // setup database-connection
        DatabaseMain databaseMain = new DatabaseMain(sysConfig);
        databaseMain.startUp();

        // setup endpoints/API
        ResourceConfig config = new ResourceConfig()
                .packages("org.wahlzeit_revisited.auth")
                .packages("org.wahlzeit_revisited.resource")
                .packages("org.wahlzeit_revisited.service");
        config.register(new ServiceInjectBinder());

        // setup server
        URI baseUri = UriBuilder.fromUri("http://[::]/").port(8080).build();
        HttpServer server = GrizzlyHttpServerFactory.createHttpServer(baseUri, config, false);

        // setup static file serving - needed for local saved photos
        StaticHttpHandler staticHandler = new StaticHttpHandler(sysConfig.getPhotosDir().getRootDir());
        server.getServerConfiguration().addHttpHandler(staticHandler, sysConfig.getStaticFileMappingPath());
        server.start();

        databaseMain.shutDown();
    }

}