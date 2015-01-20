package com.davidmis.elmplugin.psi;

import com.davidmis.elmplugin.ElmFileType;
import com.davidmis.elmplugin.ElmLanguage;
import com.intellij.extapi.psi.PsiFileBase;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.psi.FileViewProvider;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

public class ElmFile extends PsiFileBase {
    public ElmFile(@NotNull FileViewProvider viewProvider) {
        super(viewProvider, ElmLanguage.INSTANCE);
    }

    @NotNull
    @Override
    public FileType getFileType() {
        return ElmFileType.INSTANCE;
    }

    @Override
    public String toString() {
        return "Elm Language File";
    }

    @Override
    public Icon getIcon(int flags) {
        return super.getIcon(flags);
    }
}