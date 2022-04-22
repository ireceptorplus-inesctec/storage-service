package com.ireceptorplus.ireceptorchainclient.DataTransformationRunning;

import com.ireceptorplus.ireceptorchainclient.iReceptorStorageServiceLogging;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.ArrayList;

public class FileDownloader
{

    ArrayList<File> files;
    String destinationPath;

    public FileDownloader(File file)
    {
        this.files = new ArrayList<>();
        this.files.add(file);
    }

    public FileDownloader(ArrayList<File> files)
    {
        this.files = files;
    }

    public void downloadFilesToDir()
    {
        for (File file : files)
        {
            try
            {
                URL url = new URL(file.getUrl());
                ReadableByteChannel readableByteChannel = Channels.newChannel(url.openStream());
                FileOutputStream fileOutputStream = new FileOutputStream(destinationPath + "/" + file.getUuid());
                fileOutputStream.getChannel().transferFrom(readableByteChannel, 0, Long.MAX_VALUE);
            } catch (MalformedURLException e)
            {
                String message = "Error downloading file from URL " + file.getUrl() + ", due to malformed URL exception. Reason: ";
                iReceptorStorageServiceLogging.writeLogMessages(e, message);
            } catch (IOException e)
            {
                String message = "Error downloading file from URL " + file.getUrl() + ", due to exception when writing to disk. Reason: ";
                iReceptorStorageServiceLogging.writeLogMessages(e, message);
            }
        }
    }
}
