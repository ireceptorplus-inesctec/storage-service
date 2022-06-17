package com.ireceptorplus.ireceptorchainclient.DataTransformationRunning;

import com.ireceptorplus.ireceptorchainclient.BlockchainAPI.DataClasses.ReproducibilityData.DownloadbleFile;
import com.ireceptorplus.ireceptorchainclient.BlockchainAPI.DataClasses.ReproducibilityData.File;
import com.ireceptorplus.ireceptorchainclient.DataTransformationRunning.Exceptions.TryingToDownloadFileWithoutUrl;
import com.ireceptorplus.ireceptorchainclient.iReceptorStorageServiceLogging;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.ArrayList;
import java.util.List;

public class FileDownloader
{

    List<DownloadbleFile> downloadbleFiles;
    String destinationPath;

    public FileDownloader(DownloadbleFile downloadbleFile, String destinationPath)
    {
        this.downloadbleFiles = new ArrayList<>();
        this.downloadbleFiles.add(downloadbleFile);
        this.destinationPath = destinationPath;
    }

    public FileDownloader(List<DownloadbleFile> downloadbleFiles, String destinationPath)
    {
        this.downloadbleFiles = downloadbleFiles;
        this.destinationPath = destinationPath;
    }

    public void downloadFilesToDir() throws TryingToDownloadFileWithoutUrl
    {
        for (DownloadbleFile downloadbleFile : downloadbleFiles)
        {
            try
            {
                URL url = new URL(downloadbleFile.getUrl());
                ReadableByteChannel readableByteChannel = Channels.newChannel(url.openStream());
                FileOutputStream fileOutputStream = new FileOutputStream(destinationPath + "/" + downloadbleFile.getUuid());
                fileOutputStream.getChannel().transferFrom(readableByteChannel, 0, Long.MAX_VALUE);
            } catch (MalformedURLException e)
            {
                String message = "Error downloading file from URL " + downloadbleFile.getUrl() + ", due to malformed URL exception. Reason: ";
                iReceptorStorageServiceLogging.writeLogMessages(e, message);
            } catch (IOException e)
            {
                String message = "Error downloading file from URL " + downloadbleFile.getUrl() + ", due to exception when writing to disk. Reason: ";
                iReceptorStorageServiceLogging.writeLogMessages(e, message);
            }
        }
    }
}
