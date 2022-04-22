package com.ireceptorplus.ireceptorchainclient.DataTransformationRunning;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class NextFlowScriptRunner extends DataTransformationRunner
{
    public NextFlowScriptRunner(ArrayList<DownloadbleFile> inputs, DownloadbleFile script)
    {
        super(inputs, script);
    }

    @Override
    void run()
    {

        NextFlowScript nextFlowScript = (NextFlowScript) scriptFile;
        BashCommandsRunner bashCommandsRunner = new BashCommandsRunner(inputs, scriptFile);
        bashCommandsRunner.runBashCommand(nextFlowScript.getContent());

    }

    @Override
    boolean verifyIfOutputsMatch(DatasetFile datasetFile)
    {
        return false;
    }

    public static void main(String args[])
    {
        String testNextFlowScriptContent = "#!/usr/bin/env nextflow\n" +
                "\n" +
                "params.in = \"$baseDir/data/sample.fa\"\n" +
                "\n" +
                "/*\n" +
                " * split a fasta file in multiple files\n" +
                " */\n" +
                "process splitSequences {\n" +
                "\n" +
                "    input:\n" +
                "    path 'input.fa' from params.in\n" +
                "\n" +
                "    output:\n" +
                "    path 'seq_*' into records\n" +
                "\n" +
                "    \"\"\"\n" +
                "    awk '/^>/{f=\"seq_\"++d} {print > f}' < input.fa\n" +
                "    \"\"\"\n" +
                "}\n" +
                "\n" +
                "/*\n" +
                " * Simple reverse the sequences\n" +
                " */\n" +
                "process reverse {\n" +
                "\n" +
                "    input:\n" +
                "    path x from records\n" +
                "\n" +
                "    output:\n" +
                "    stdout into result\n" +
                "\n" +
                "    \"\"\"\n" +
                "    cat $x | rev\n" +
                "    \"\"\"\n" +
                "}\n" +
                "\n" +
                "/*\n" +
                " * print the channel content\n" +
                " */\n" +
                "result.subscribe { println it }";
        NextFlowScript script = new NextFlowScript(testNextFlowScriptContent, "BasicPipeline");
        NextFlowScriptRunner runner = new NextFlowScriptRunner(null, script);
        runner.run();
    }
}
