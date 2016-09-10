package com.pucho.core.instrumentation;



import io.dropwizard.setup.Environment;
import org.glassfish.jersey.server.ResourceConfig;
import org.minnal.instrument.ApplicationEnhancer;
import org.minnal.instrument.NamingStrategy;

public class DropwizardApplicationEnhancer extends ApplicationEnhancer {

    /**
     * @param environment
     * @param namingStrategy
     */
    public DropwizardApplicationEnhancer(Environment environment, String[] packagesToScan, NamingStrategy namingStrategy) {
        super(environment.jersey().getResourceConfig(), namingStrategy, packagesToScan, packagesToScan);
    }

    /**
     * @param resourceClass
     */
    protected void addResource(Class<?> resourceClass) {
      //  ( getApplication()).getClasses().add(resourceClass);
        ((ResourceConfig) getApplication()).register(resourceClass);
    }

}
