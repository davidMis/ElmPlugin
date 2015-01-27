package com.davidmis.elmplugin;

import com.intellij.openapi.options.Configurable;
import com.intellij.openapi.options.ConfigurationException;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

/**
 * Created by david on 1/26/15.
 */
public class ElmSettingsConfigurable implements Configurable {
    private ElmSettingsComponent component;

    @Nls
    @Override
    public String getDisplayName() {
        return "Elm language";
    }

    @Nullable
    @Override
    public String getHelpTopic() {
        return "Elm language";
    }

    @Nullable
    @Override
    public JComponent createComponent() {
        if (component == null) {
            component = new ElmSettingsComponent();
        }
        return component.mainPanel;
    }

    @Override
    public boolean isModified() {
        return true;
    }

    @Override
    public void apply() throws ConfigurationException {
        if(component == null) {
            return;
        }


        String newPath = component.pathField.getText() == null ? "" : component.pathField.getText();
        ElmPersister.instance.setPathToElmMake(newPath);
        System.out.println("Path to elm-make: " + newPath);

        Boolean errorCheckingEnabled = component.enableErrorCheckingCheckBox.isSelected();
        ElmPersister.instance.setEnableErrorChecking(errorCheckingEnabled);
        System.out.println("Error checking enabled " + errorCheckingEnabled);
    }

    @Override
    public void reset() {

    }

    @Override
    public void disposeUIResources() {

    }
}
