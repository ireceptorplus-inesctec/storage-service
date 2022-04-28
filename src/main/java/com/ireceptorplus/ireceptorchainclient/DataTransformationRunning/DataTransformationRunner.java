package com.ireceptorplus.ireceptorchainclient.DataTransformationRunning;

import com.ireceptorplus.ireceptorchainclient.BlockchainAPI.DataClasses.ReproducibilityData.DownloadbleFile;

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

    void run()
    {
        runBashCommand("./" + scriptFile.getUuid());
    }

    boolean verifyIfOutputsMatch(DatasetFile datasetFile)
    {
        //TODO
        return false;
    }

    protected void downloadDatasetsAndScriptToProcessingDir()
    {
        String processingFilesPath = "./" + scriptFile.getUuid();
        new File(processingFilesPath).mkdirs();
        FileDownloader inputsDownloader = new FileDownloader(inputs, processingFilesPath);
        inputsDownloader.downloadFilesToDir();
        FileDownloader outputsDownloader = new FileDownloader(outputs, processingFilesPath + "/outputs");
        outputsDownloader.downloadFilesToDir();
        FileDownloader scriptDownloader = new FileDownloader(scriptFile, processingFilesPath);
        scriptDownloader.downloadFilesToDir();
    }
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
