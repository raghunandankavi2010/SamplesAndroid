package com.pucho.service.impl;


import com.google.inject.Inject;
import com.pucho.configuration.PuchoConfiguration;
import com.pucho.repository.FileRepository;
import com.pucho.service.FileService;

import java.io.InputStream;

public class FileServiceImpl implements FileService
{
    FileRepository fileRepository;
    PuchoConfiguration puchoConfiguration;

    @Inject
    public FileServiceImpl(FileRepository fileRepository, PuchoConfiguration puchoConfiguration)
    {
       this.fileRepository = fileRepository;
       this.puchoConfiguration = puchoConfiguration;
    }


    @Override
    public Boolean saveFile(InputStream uploadedInputStream, String fileName, String type)
    {
        String storageFileName = System.currentTimeMillis()+"."+type;
        return fileRepository.saveFile(uploadedInputStream, puchoConfiguration.getFileStoragePath()+storageFileName);
    }


}
