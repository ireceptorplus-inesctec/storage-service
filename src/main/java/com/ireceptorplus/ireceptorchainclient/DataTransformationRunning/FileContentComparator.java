package com.ireceptorplus.ireceptorchainclient.DataTransformationRunning;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

public class FileContentComparator
{

    private String file1, file2;

    public FileContentComparator(String file1, String file2)
    {
        this.file1 = file1;
        this.file2 = file2;
    }

    public boolean compare() throws IOException
    {
        File file1_ = new File(file1);
        File file2_ = new File(file2);
        return FileUtils.contentEquals(file1_, file2_);
    }
}