package com.pucho.repository;

import java.io.InputStream;

public interface FileRepository
{
    public Boolean saveFile(InputStream uploadedInputStream, String serverLocation);
}
