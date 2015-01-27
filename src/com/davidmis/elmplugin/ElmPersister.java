package com.davidmis.elmplugin;

import com.intellij.ide.util.PropertiesComponent;

/**
 * Created by david on 1/26/15.
 */
public class ElmPersister {
    public static ElmPersister instance = new ElmPersister();

    private ElmPersister() {}

    private String pathToElmMake;

    public String getPathToElmMake() {
        if(pathToElmMake == null) {
            pathToElmMake = PropertiesComponent.getInstance().getValue("com.davidmis.elmplugin.pathToElmMake", "elm-make");
        }

        return pathToElmMake;
    }

    public void setPathToElmMake(String path) {
        pathToElmMake = path;
        PropertiesComponent.getInstance().setValue("com.davidmis.elmplugin.pathToElmMake", path);
    }

}
