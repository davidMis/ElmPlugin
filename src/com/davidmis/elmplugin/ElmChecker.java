package com.davidmis.elmplugin;

import java.io.*;
import java.util.Arrays;

public class ElmChecker {
    public static ElmChecker instance = new ElmChecker();

    private ElmChecker() {}

    private boolean runElmMake = true;

    public String getCompilerOutput(String filename) {
        return getCompilerOutput(ElmPersister.instance.getPathToElmMake(), filename);
    }

    private String getCompilerOutput(String pathToElm, String filename) {
        try {
            ProcessBuilder builder = new ProcessBuilder(pathToElm, filename, "--yes");
    //        builder.directory(new File(System.getProperty("java.io.tmpdir")));
            Process process = builder.start();
            process.waitFor();

            BufferedReader br = new BufferedReader(new InputStreamReader(process.getErrorStream()));
            String line;
            StringBuilder output = new StringBuilder();

            while ((line = br.readLine()) != null) {
                output.append(line + "\n");
            }

            return output.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return "";
    }
}
