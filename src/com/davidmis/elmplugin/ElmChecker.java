package com.davidmis.elmplugin;

import com.intellij.notification.Notification;
import com.intellij.notification.NotificationType;
import com.intellij.notification.Notifications;

import java.io.*;
import java.util.Arrays;

public class ElmChecker {
    public static ElmChecker instance = new ElmChecker();

    private ElmChecker() {}

    private boolean errorShownOnce = false;

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
            if(!errorShownOnce) {
                errorShownOnce = true;
                Notifications.Bus.notify(new Notification("External Executables Critical Failure", "Error running elm-make",
                        "Could not run elm-make. Error checking disabled. Is the path configured in Settings > Other Settings > Elm Language?",
                        NotificationType.ERROR));
            }
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return "";
    }
}
