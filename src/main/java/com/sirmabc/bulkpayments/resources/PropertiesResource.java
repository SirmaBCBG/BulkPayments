package com.sirmabc.bulkpayments.resources;

import com.sirmabc.bulkpayments.exceptions.AppException;
import com.sirmabc.bulkpayments.persistance.repositories.PropertiesRepository;
import com.sirmabc.bulkpayments.util.Properties;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.DefaultSingletonBeanRegistry;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
@Path("properties")
public class PropertiesResource {

    @Autowired
    PropertiesRepository propertiesRepository;

    @Autowired
    private ApplicationContext applicationContext;

    @GET
    @Path("update")
    public String updateProperties() throws AppException {


        Properties properties = applicationContext.getBean(Properties.class);
        properties.postConstruct();

        return "Properties update, check logs for values.";

    }

}
