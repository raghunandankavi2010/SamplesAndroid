package com.pucho.core.filter;

import com.pucho.configuration.PuchoConfiguration;
import org.activejpa.jpa.JPA;
import org.activejpa.jpa.JPAContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import java.io.IOException;


public class JpaContextRequestFilter implements ContainerRequestFilter
{

    private static final Logger logger = LoggerFactory.getLogger(JpaContextRequestFilter.class);
    PuchoConfiguration puchoConfiguration;

    public JpaContextRequestFilter(PuchoConfiguration sevakConfiguration) {
          this.puchoConfiguration = sevakConfiguration;
    }


    protected JPAContext getContext() {
        if(JPA.instance.getDefaultConfig()!=null)
            return JPA.instance.getDefaultConfig().getContext(false);
        return null;
    }



    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException
    {
        //if (ironthroneConfiguration.isDbEnabled() == true)
        {
            JPAContext context = getContext();
            if (context != null) {
                context.getEntityManager();
                logger.info("Context Created");
            }
        }

    }
}
