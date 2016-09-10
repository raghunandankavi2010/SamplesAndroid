package com.pucho;


import com.pucho.configuration.PuchoConfiguration;
import com.pucho.core.filter.JpaContextRequestFilter;
import com.pucho.core.filter.JpaContextResponseFilter;
import com.pucho.core.instrumentation.MinnalBundle;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.activejpa.enhancer.ActiveJpaAgentLoader;
import org.activejpa.jpa.JPA;
import org.eclipse.jetty.servlets.CrossOriginFilter;
import org.glassfish.jersey.media.multipart.MultiPartFeature;

import ru.vyarus.dropwizard.guice.GuiceBundle;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration.Dynamic;
import java.text.SimpleDateFormat;
import java.util.EnumSet;
import java.util.TimeZone;


public class PuchoMainService extends Application<PuchoConfiguration>
{
    public static PuchoMainService puchoMainService;
    
    
    public static PuchoMainService getPuchoMainService() {
        return puchoMainService;
    }

    public static void main(String[] args) throws Exception
    {
        puchoMainService = new PuchoMainService();
        puchoMainService.run(args);
    }

    @Override
    public void initialize(Bootstrap<PuchoConfiguration> bootstrap)
    {
        ActiveJpaAgentLoader.instance().loadAgent();
        GuiceBundle<PuchoConfiguration> guiceBundle = GuiceBundle
            .<PuchoConfiguration> builder()
            .modules(new PuchoModule())
            .enableAutoConfig(getClass().getPackage().getName()).build();
        bootstrap.addBundle(guiceBundle);
        bootstrap.addBundle(new MinnalBundle(new String[] {getClass().getPackage().getName()}));
        bootstrap.getObjectMapper().setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
        bootstrap.getObjectMapper().setTimeZone(TimeZone.getTimeZone("Asia/Calcutta"));
    }
    
    private void configureCors(Environment environment) {
      Dynamic filter = environment.servlets().addFilter("CORS", CrossOriginFilter.class);
      filter.addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), true, "/*");
      filter.setInitParameter(CrossOriginFilter.ALLOWED_METHODS_PARAM, "GET,PUT,POST,DELETE,OPTIONS");
      filter.setInitParameter(CrossOriginFilter.ALLOWED_ORIGINS_PARAM, "*");
      filter.setInitParameter(CrossOriginFilter.ACCESS_CONTROL_ALLOW_ORIGIN_HEADER, "*");
      filter.setInitParameter("allowedHeaders", "Content-Type,Authorization,X-Requested-With,Content-Length,Accept,Origin");
      filter.setInitParameter("allowCredentials", "true");
    }

    @Override
    public void run(PuchoConfiguration configuration, Environment environment) throws Exception
    {
        JPA.instance.addPersistenceUnit("pucho", configuration.getDb(), true);
        configureCors(environment);
        environment.jersey().getResourceConfig().register(new JpaContextRequestFilter(configuration));
        environment.jersey().getResourceConfig().register(new JpaContextResponseFilter(configuration));
        environment.jersey().getResourceConfig().register(MultiPartFeature.class);

    }
}
