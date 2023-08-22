package com.sirmabc.bulkpayments;



import com.sirmabc.bulkpayments.resources.PropertiesResource;
import jakarta.ws.rs.ApplicationPath;
import org.glassfish.jersey.server.ResourceConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


@Component
@ApplicationPath("api")
public class JerseyConfig extends ResourceConfig {
    private static final Logger logger = LoggerFactory.getLogger(JerseyConfig.class);

    public JerseyConfig() {
        logger.debug("JerseyConfig()");

        register(PropertiesResource.class);

        //register(ExceptionResponse.class);
    }


}
