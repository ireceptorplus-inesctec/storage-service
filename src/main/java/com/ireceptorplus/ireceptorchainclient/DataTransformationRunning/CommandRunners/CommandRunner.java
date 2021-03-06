package com.ireceptorplus.ireceptorchainclient.DataTransformationRunning.CommandRunners;

import com.ireceptorplus.ireceptorchainclient.BlockchainAPI.DataClasses.ReproducibilityData.File;
import com.ireceptorplus.ireceptorchainclient.DataTransformationRunning.Exceptions.ErrorRunningToolCommand;
import com.ireceptorplus.ireceptorchainclient.DataTransformationRunning.FileSystemManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * This class implements the logic for running a command.
 * The logic to run a command depends on the tool.
 * Therefore, there is one derived class for each Tool, according to the strategy pattern.
 */
public abstract class CommandRunner
{
    /**
     * The path to the directory that was allocated for this command to be run.
     */
    protected String dirPath;

    protected String inputsFolderPath;

    protected ArrayList<File> inputDatasets;

    protected String command;

    protected ArrayList<File> outputDatasets;

    FileSystemManager fileSystemManager;

    public CommandRunner(String dirPath, String inputsFolderPath,
                         ArrayList<File> inputDatasets, String command,
                         FileSystemManager fileSystemManager)
    {
        this.dirPath = dirPath;
        this.inputsFolderPath = inputsFolderPath;
        this.inputDatasets = inputDatasets;
        this.command = command;
        this.fileSystemManager = fileSystemManager;
        this.outputDatasets = new ArrayList<>();
    }

    public ArrayList<File> getOutputDatasets()
    {
        return outputDatasets;
    }

    /**
     * This method places the input files in the appropriate location that will then be fed to the tool.
     * By default, the method implementation provided by this class, places the files in directory allocated for this command to run, inside a folder named "data".
     * This method can be overridden by the derived classes to place the files elsewhere and implement additional procedure to get them inside the container, if necessary.
     */
    protected String organizeInputs()
    {
        String dataDirPath = dirPath + "/data/";
        new java.io.File(dataDirPath).mkdirs();
        for (File inputDataset : inputDatasets)
        {
            String inputDatasetPath = fileSystemManager.getInputRelativePath(inputsFolderPath, inputDataset);
            java.io.File source = new java.io.File(inputDatasetPath);
            String destinationPath = fileSystemManager.getPathOfFileRelativeToPath(dataDirPath, inputDataset);
            java.io.File destination = new java.io.File(destinationPath);
            source.renameTo(destination);
        }

        return dataDirPath;
    }

    /**
     * This method builds the processing tool command that will run and execute the processing.
     * This is method will be implemented by the derived classes because specific behavior implementation is necessary.
     * @return A String representing the command.
     */
    protected abstract String buildToolCommandString();

    /**
     * This method builds the command that will be executed on the host to run the tool.
     * In case the pipeline is to be run locally, the return of this method is equal to buildToolCommandString method.
     * In case the pipeline is to be run inside a container, this method returns the command that launches the container, for example.
     */
    protected abstract String buildHostCommandString(String dataPath);

    /**
     * This method returns the path in which the output datasets are stored.
     * It is implemented by the derived class since different tools might place the output in different folders.
     * @return A String representing the relative path of the output datasets.
     */
    protected abstract String getOutputsRelativePath();

    public ArrayList<java.io.File> getOutputDatasetFiles()
    {
        ArrayList<java.io.File> outputDatasetFiles = new ArrayList<>();
        for (File outputDataset : outputDatasets)
        {
            String outputsPath = fileSystemManager.getPathOfFileRelativeToPath(dirPath, getOutputsRelativePath());
            String datasetPath = fileSystemManager.getPathOfFileRelativeToPath(outputsPath, outputDataset);
            java.io.File datasetFile = new java.io.File(datasetPath);
            outputDatasetFiles.add(datasetFile);
        }

        return outputDatasetFiles;
    }

    public void executeCommand() throws ErrorRunningToolCommand
    {
        String inputsFolder = organizeInputs();
        runBashCommand(buildHostCommandString(inputsFolder));
    }

    /**
     * This method runs a bash command. It can be a command to call a bash or nextflow script.
     *
     * @param command A String representing the command to be run.
     */
    void runBashCommand(String command) throws ErrorRunningToolCommand
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

            StringBuilder output = getOutputFromProcess(process.getInputStream());
            StringBuilder errorOutput = getOutputFromProcess(process.getErrorStream());

            int exitVal = process.waitFor();
            if (exitVal == 0)
            {
                System.out.println("Success!");
                System.out.println(output);
            } else
            {
                System.out.println(command);
                System.out.println("Error running command");
                System.out.println(errorOutput);
                System.out.println(output);
                throw new ErrorRunningToolCommand("Error running command. Error was " + errorOutput);
            }

        } catch (IOException e)
        {
            throw new ErrorRunningToolCommand("Error running command: IOException. Reason: " + e.getMessage());
        } catch (InterruptedException e)
        {
            throw new ErrorRunningToolCommand("Error running command: InterruptedException. Reason: " + e.getMessage());
        }
    }

    private StringBuilder getOutputFromProcess(InputStream inputStream) throws IOException
    {
        StringBuilder output = new StringBuilder();

        BufferedReader reader = new BufferedReader(
                new InputStreamReader(inputStream));

        String line;
        while ((line = reader.readLine()) != null)
        {
            output.append(line + "\n");
        }
        return output;
    }
}
