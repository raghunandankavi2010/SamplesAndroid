package com.pucho.service;

import java.io.InputStream;

public interface FileService
{
    public Boolean saveFile( InputStream uploadedInputStream, String fileName, String type);
}
