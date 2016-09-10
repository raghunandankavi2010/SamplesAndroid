package com.pucho.core.filter;

import com.pucho.configuration.PuchoConfiguration;
import org.activejpa.jpa.JPA;
import org.activejpa.jpa.JPAContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import java.io.IOException;

public class JpaContextResponseFilter implements ContainerResponseFilter
{
    private ThreadLocal<Boolean> contextCreated = new ThreadLocal<Boolean>();
    private static final Logger logger = LoggerFactory.getLogger(JpaContextRequestFilter.class);
    PuchoConfiguration puchoConfiguration;

    public JpaContextResponseFilter(PuchoConfiguration puchoConfiguration) {
        this.puchoConfiguration = puchoConfiguration;
    }

    protected JPAContext getContext() {
        if(JPA.instance.getDefaultConfig()!=null)
            return JPA.instance.getDefaultConfig().getContext(false);
        return null;
    }

    @Override
    public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext) throws IOException
    {
        logger.info(" Destroy filter called");
        JPAContext context = getContext();
        if (context != null) {
            if (context.isTxnOpen()) {
                context.closeTxn(true);
            }
            if(context.getEntityManager()!=null)
                context.close();
            logger.info("Context Destroyed");
        }
    }
}
