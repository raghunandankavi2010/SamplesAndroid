package com.pucho.resources;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.pucho.configuration.PuchoConfiguration;
import com.pucho.domain.Text;
import com.pucho.domain.TextSpeechFileData;
import com.pucho.service.FileService;
import io.dropwizard.jackson.JsonSnakeCase;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dinesh.rathore on 13/12/15.
 */
@Path("/speechCollection")
@JsonSnakeCase
@Produces({"application/json"})
@Singleton
public class SpeechCollectionResource {

    FileService fileService;
    PuchoConfiguration puchoConfiguration;

    @Inject
    public SpeechCollectionResource(FileService fileService,
                       PuchoConfiguration puchoConfiguration) {
        this.fileService = fileService;
        this.puchoConfiguration = puchoConfiguration;
    }

    @POST
    @Path("/{textId}/upload")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Boolean uploadFileForText(@PathParam("textId") Long textId,
        @FormDataParam("file") final InputStream fileInputStream,
        @FormDataParam("file") FormDataContentDisposition contentDisposition) {


        String fileName = contentDisposition.getFileName() ;
        String fileDetails [] = fileName.split("\\.");

        Boolean result = fileService.saveFile(fileInputStream, fileName,
            fileDetails[fileDetails.length - 1]);

        TextSpeechFileData textSpeechFileData = new TextSpeechFileData();
        textSpeechFileData.setFilename(fileName);
        textSpeechFileData.setTextId(textId);
        textSpeechFileData.persist();

        return result;
    }

    @GET
    @Path("/listSentences")
    public List<Text> getTexts(@QueryParam("count") Long count){
        List<Text> texts = new ArrayList<Text>();
        texts = Text.all();
        return texts;
    }



}
