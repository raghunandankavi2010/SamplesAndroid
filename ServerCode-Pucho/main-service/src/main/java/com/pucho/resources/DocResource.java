package com.pucho.resources;

import com.google.inject.Inject;
import com.pucho.configuration.PuchoConfiguration;
import com.pucho.service.FileService;
//import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
//import org.glassfish.jersey.media.multipart.FormDataParam;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import java.io.InputStream;

/**
 * Created by dinesh.rathore on 23/08/15.
 */
public class DocResource {

    FileService fileService;
    PuchoConfiguration puchoConfiguration;

    @Inject
    public DocResource(FileService fileService,
        PuchoConfiguration puchoConfiguration) {
        this.fileService = fileService;
        this.puchoConfiguration = puchoConfiguration;
    }

//    @POST
//    @Path("/{namespace}/upload")
//    @Consumes(MediaType.MULTIPART_FORM_DATA)
//    public Boolean uploadFile(@PathParam("namespace") String namespace,
//        @FormDataParam("file") final InputStream fileInputStream,
//        @FormDataParam("file") FormDataContentDisposition contentDisposition) {
//
//
//        String fileName = contentDisposition.getFileName() ;
//        String fileDetails [] = fileName.split("\\.");
//
//        Boolean result = fileService.saveFile(fileInputStream, fileName,
//            fileDetails[fileDetails.length - 1]);
//
//
//        return result;
//    }
}
