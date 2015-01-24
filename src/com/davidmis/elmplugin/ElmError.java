package com.davidmis.elmplugin;

import com.intellij.openapi.editor.Document;

/**
 * Created by david on 1/24/15.
 */
public class ElmError {
    private int line;
    private int startCol;
    private int endCol;
    private String message;
    private int startIndex;
    private int endIndex;

    public ElmError(int line, int startCol, int endCol, String message, Document document) {
        this.line = line;
        this.startCol = startCol;
        this.endCol = endCol;
        this.message = message;

        setIndecis(document);
    }

    public int getLine() {
        return line;
    }

    public int getStartCol() {
        return startCol;
    }

    public int getEndCol() {
        return endCol;
    }

    public String getMessage() {
        return message;
    }

    public int getStartIndex() {
        return startIndex;
    }

    private void setStartIndex(Document document) {
        this.startIndex = document.getLineStartOffset(this.line) + this.startCol;
    }

    public int getEndIndex() {
        return endIndex;
    }

    private void setEndIndex(Document document) {
        this.endIndex = document.getLineStartOffset(this.line) + this.endCol;
    }

    private void setIndecis(Document document) {
        setStartIndex(document);
        setEndIndex(document);
    }
}
