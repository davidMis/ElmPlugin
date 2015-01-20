package com.davidmis.elmplugin.psi;

import com.intellij.psi.tree.IElementType;
import com.davidmis.elmplugin.ElmLanguage;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

public class ElmElementType extends IElementType {
    public ElmElementType(@NotNull @NonNls String debugName) {
        super(debugName, ElmLanguage.INSTANCE);
    }
}