package com.davidmis.elmplugin;

import com.intellij.lang.annotation.AnnotationHolder;
import com.intellij.lang.annotation.ExternalAnnotator;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.fileEditor.FileDocumentManager;
import com.intellij.openapi.util.TextRange;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiFile;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;


public class ElmExternalAnnotator extends ExternalAnnotator<ElmExternalAnnotator.InitialInfo, List<ElmError>> {
    public class InitialInfo {
        public Document document;
        public VirtualFile vfile;

        public InitialInfo(Document d, VirtualFile v) {
            document = d;
            vfile = v;
        }
    }

    @Nullable
    public InitialInfo collectInformation(@NotNull PsiFile file, @NotNull final Editor editor, boolean hasErrors) {
        ApplicationManager.getApplication().invokeLater(new Runnable() {
            @Override
            public void run() {
                FileDocumentManager.getInstance().saveDocument(editor.getDocument());
            }
        });

        return hasErrors ? null : new InitialInfo(editor.getDocument(), file.getVirtualFile());
    }

    @Nullable
    public List<ElmError> doAnnotate(InitialInfo info) {
        // TODO replace with Logger
        System.out.println("Checking: " + info.vfile.getPath() + "    ---------------------------------------");
        String compilerOutput = ElmChecker.instance.getCompilerOutput(info.vfile.getPath());
        System.out.println(compilerOutput);
        System.out.println("------------------------------------------------------");

        return getErrors(compilerOutput, info.document);
    }

    private List<ElmError> getErrors(String compilerOutput, Document document) {
        List<ElmError> errors = new LinkedList<ElmError>();

        String[] lines = compilerOutput.split("\n");
        for(int i = 0; i < lines.length; i++) {
            String line = lines[i];

            /* Handle "Could not find variable" Error */
            if(line.startsWith("Error on line")) {
                int errLine = Integer.parseInt(line.split("line ")[1].split(",")[0]) - 1;
                int errStartCol = Integer.parseInt(line.split("column ")[1].split(" to")[0]) - 1;
                int errEndCol = Integer.parseInt(line.split("to ")[1].split(":")[0]) - 1;
                String errMessage = lines[i+1];
                i += 1;

                errors.add(new ElmError(errLine, errStartCol, errEndCol, errMessage, document));
            }

            /* Handle "Could not find module" Error */
            if(line.contains("Could not find module")) {
                String moduleName = line.split("Could not find module '")[1].split("'")[0];
                String fileText = document.getText();
                int errStartIndex = fileText.indexOf(moduleName, fileText.indexOf("import"));
                int errEndIndex = errStartIndex + moduleName.length();
                String errMessage = lines[i];

                errors.add(new ElmError(errStartIndex, errEndIndex, errMessage));
            }
        }

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
