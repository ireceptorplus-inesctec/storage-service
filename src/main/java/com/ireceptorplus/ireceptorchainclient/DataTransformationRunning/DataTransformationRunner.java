package com.ireceptorplus.ireceptorchainclient.DataTransformationRunning;

import com.ireceptorplus.ireceptorchainclient.BlockchainAPI.DataClasses.ReproducibilityData.DownloadbleFile;
import com.ireceptorplus.ireceptorchainclient.DataTransformationRunning.Exceptions.ErrorComparingOutputs;
import com.ireceptorplus.ireceptorchainclient.iReceptorStorageServiceLogging;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class DataTransformationRunner
{
    /**
     * The inputs datasets that when applied the processing yield the outputs.
     */
    protected ArrayList<DownloadbleFile> inputs;

    /**
     * The script that when applied to the inputs yields the outputs.
     */
    protected DownloadbleFile scriptFile;

    /**
     * The outputs which are yield when the script is applied to the inputs.
     */
    protected ArrayList<DownloadbleFile> outputs;

    public DataTransformationRunner(ArrayList<DownloadbleFile> inputs, DownloadbleFile scriptFile,
                                    ArrayList<DownloadbleFile> outputs)
    {
        this.inputs = inputs;
        this.scriptFile = scriptFile;
        this.outputs = outputs;
    }

    public ArrayList<DownloadbleFile> getOutputs() {
        return outputs;
    }

    public void run()
    {
        runBashCommand("./" + scriptFile.getHashValue());
    }

    /**
     * This method verifies if the outputs of the data processing match.
     * It uses the logic and values defined in class FileSystemManager to determine the file system path structure.
     * @return A boolean value identifying whether the outputs match or not.
     * @throws ErrorComparingOutputs Exception thrown when an error comparing the outputs occurs.
     */
    public boolean verifyIfOutputsMatch() throws ErrorComparingOutputs
    {
        for (DownloadbleFile output : outputs)
        {
            FileSystemManager fileSystemManager = FileSystemManager.getInstance();
            String expectedOutputRelativePath = fileSystemManager.getExpectedOutputRelativePath(output);
            String processedOutputRelativePath = fileSystemManager.getProcessedOutputRelativePath(output);
            FileContentComparator comparator = new FileContentComparator(expectedOutputRelativePath, processedOutputRelativePath);
            try
            {
                return comparator.compare();
            } catch (IOException e)
            {
                throw new ErrorComparingOutputs();
            }
        }

        return false;
    }

    /**
     * This method downloads the datasets and the script necessary to run the data processing.
     * The scripts are downloaded from their respective URLs which should point to other peers, instances of the iReceptorChain Storage Service (this application).
     */
    protected void downloadDatasetsAndScriptToProcessingDir()
    {
        String processingFilesPath = "./" + scriptFile.getHashValue();
        new File(processingFilesPath).mkdirs();
        FileDownloader inputsDownloader = new FileDownloader(inputs, processingFilesPath);
        inputsDownloader.downloadFilesToDir();
        FileDownloader outputsDownloader = new FileDownloader(outputs, processingFilesPath + "/outputs");
        outputsDownloader.downloadFilesToDir();
        FileDownloader scriptDownloader = new FileDownloader(scriptFile, processingFilesPath);
        scriptDownloader.downloadFilesToDir();
    }

    /**
     * This method runs a bash command. It can be a command to call a bash or nextflow script.
     * @param command A String representing the command to be run.
     */
    void runBashCommand(String command)
    {
        try
        {

            // -- Linux --

            // Run a shell command
            // Process process = Runtime.getRuntime().exec("ls /home/mkyong/");

            // Run a shell script
            // Process process = Runtime.getRuntime().exec("path/to/hello.sh");

            // -- Windows --

            // Run a command
            //Process process = Runtime.getRuntime().exec("cmd /c dir C:\\Users\\mkyong");
            Process process;
            String operatingSystemName = System.getProperty("os.name");
            if (operatingSystemName.contains("Windows"))
            {
                process = Runtime.getRuntime().exec("cmd /c " + command);
            } else
            {
                process = Runtime.getRuntime().exec(command);
            }

            StringBuilder output = new StringBuilder();

            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(process.getInputStream()));

            String line;
            while ((line = reader.readLine()) != null)
            {
                output.append(line + "\n");
            }

            int exitVal = process.waitFor();
            if (exitVal == 0)
            {
                System.out.println("Success!");
                System.out.println(output);
                System.exit(0);
            } else
            {
                //abnormal...
            }

        } catch (IOException e)
        {
            e.printStackTrace();
        } catch (InterruptedException e)
        {
            e.printStackTrace();
        }
    }

}
