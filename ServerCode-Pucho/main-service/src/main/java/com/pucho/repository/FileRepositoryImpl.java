package com.pucho.repository;

import java.io.*;


public class FileRepositoryImpl implements FileRepository
{
    @Override
    public Boolean saveFile(InputStream uploadedInputStream, String serverLocation)
    {
        OutputStream outputStream = null ;
            try {
                 outputStream = new FileOutputStream(new File(serverLocation));
                int read = 0;
                byte[] bytes = new byte[1024];

                outputStream = new FileOutputStream(new File(serverLocation));
                while ((read = uploadedInputStream.read(bytes)) != -1) {
                    outputStream.write(bytes, 0, read);
                }

                return true;
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        finally
            {
                try
                {
                    if(outputStream!=null)
                    {
                        outputStream.flush();
                        outputStream.close();
                    }
                }
                catch (Exception e)
                {
                     e.printStackTrace();
                }
            }
    }

}
