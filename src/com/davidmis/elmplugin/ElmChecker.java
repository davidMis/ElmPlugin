package com.davidmis.elmplugin;

import java.io.*;
import java.util.Arrays;

/**
 * Created by david on 1/24/15.
 */
public class ElmChecker {
    public static ElmChecker instance = new ElmChecker();

    private ElmChecker() {}

    public String getCompilerOutput(String filename) {
        return getCompilerOutput("elm-make", filename);
    }

    public String getCompilerOutput(String pathToElm, String filename) {
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

    public static void main(String[] args) {
        String pathToElm = "elm-make";
        String filename = "/Users/david/Projects/Elm/Test/test-err.elm";

        ElmChecker checker = new ElmChecker();

        System.out.println(checker.getCompilerOutput(pathToElm, filename));
    }
}