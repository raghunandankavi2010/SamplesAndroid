package com.pucho.core.instrumentation;

import com.google.common.collect.Lists;
import io.dropwizard.Bundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import lombok.NoArgsConstructor;
import org.activejpa.enhancer.ActiveJpaAgentLoader;
import org.minnal.instrument.ApplicationEnhancer;
import org.minnal.instrument.NamingStrategy;
import org.minnal.instrument.UnderscoreNamingStrategy;
import org.minnal.instrument.util.MinnalModule;

@NoArgsConstructor
public class MinnalBundle implements Bundle
{

    private String[] packagesToScan;

    private NamingStrategy namingStrategy = new UnderscoreNamingStrategy();

    /**
     * @param packagesToScan
     * @param namingStrategy
     */
    public MinnalBundle(String[] packagesToScan, NamingStrategy namingStrategy) {
        this.packagesToScan = packagesToScan;
        this.namingStrategy = namingStrategy;
    }

    /**
     * @param packagesToScan
     */
    public MinnalBundle(String[] packagesToScan) {
        this(packagesToScan, new UnderscoreNamingStrategy());
    }

    public void initialize(Bootstrap<?> bootstrap) {
        getActiveJpaAgentLoader().loadAgent();
    }

    protected ActiveJpaAgentLoader getActiveJpaAgentLoader() {
        return ActiveJpaAgentLoader.instance();
    }

    public void run(Environment environment) {
        org.glassfish.jersey.server.ResourceConfig config = environment.jersey().getResourceConfig();//environment.jersey().getResourceConfig();
      //  System.out.println(config.getMediaTypeMappings());

       config.register(new ResponseTransformationFilter(Lists.<String>newArrayList(), new UnderscoreNamingStrategy()));

       // config.getContainerResponseFilters().add(new ResponseTransformationFilter(Lists.<String>newArrayList(), new UnderscoreNamingStrategy()));
        environment.getObjectMapper().registerModule(new MinnalModule());
        createApplicationEnhancer(environment).enhance();
        //System.out.println(config.getMediaTypeMappings());

    }

    protected ApplicationEnhancer createApplicationEnhancer(Environment environment) {
        return new DropwizardApplicationEnhancer(environment, packagesToScan, namingStrategy);
    }

}
