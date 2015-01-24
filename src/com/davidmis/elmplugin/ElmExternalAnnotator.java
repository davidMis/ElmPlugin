package com.davidmis.elmplugin;

import com.intellij.lang.annotation.AnnotationHolder;
import com.intellij.lang.annotation.ExternalAnnotator;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiFile;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;


public class ElmExternalAnnotator extends ExternalAnnotator<ElmExternalAnnotator.InitialInfo, List<ElmError>> {
    public class InitialInfo {
        public Document document;
        public String path;

        public InitialInfo(Document d, String p) {
            document = d;
            path = p;
        }
    }

    @Nullable
    public InitialInfo collectInformation(@NotNull PsiFile file, @NotNull Editor editor, boolean hasErrors) {
        return hasErrors ? null : new InitialInfo(editor.getDocument(), file.getVirtualFile().getPath());
    }

    @Nullable
    public List<ElmError> doAnnotate(InitialInfo info) {
        System.out.println("Checking: " + info.path + "    ---------------------------------------");
        System.out.println(ElmChecker.instance.getCompilerOutput(info.path));
        System.out.println("------------------------------------------------------");

        List<ElmError> errors = new LinkedList<ElmError>();

        ElmError testError = new ElmError(2,3,5,"Test error");
        testError.setIndecis(info.document);

        errors.add(testError);

        return errors;
    }

    public void apply(@NotNull PsiFile file, List<ElmError> errors, @NotNull AnnotationHolder holder) {
        ListIterator<ElmError> iter = errors.listIterator();
        while(iter.hasNext()) {
            ElmError err = iter.next();
            holder.createErrorAnnotation(TextRange.create(err.getStartIndex(), err.getEndIndex()), err.getMessage());
        }
    }
}
