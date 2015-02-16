package com.davidmis.elmplugin;

import com.intellij.openapi.editor.Document;

public class ElmError {
    private int startLine;
    private int endLine;
    private int startCol;
    private int endCol;
    private String message;
    private int startIndex;
    private int endIndex;

    public ElmError(int line, int startCol, int endCol, String message, Document document) {
        this.startLine = line;
        this.endLine = line;
        this.startCol = startCol;
        this.endCol = endCol;
        this.message = message;

        setIndecis(document);
    }

    public ElmError(int startLine, int endLine, int startCol, int endCol, String message, Document document) {
        this.startLine = startLine;
        this.endLine = endLine;
        this.startCol = startCol;
        this.endCol = endCol;
        this.message = message;

        setIndecis(document);
    }

    public ElmError(int startIndex, int endIndex, String message) {
        this.startIndex = startIndex;
        this.endIndex = endIndex;
        this.message = message;
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
        this.startIndex = document.getLineStartOffset(this.startLine) + this.startCol;
    }

    public int getEndIndex() {
        return endIndex;
    }

    private void setEndIndex(Document document) {
        this.endIndex = document.getLineStartOffset(this.endLine) + this.endCol;
    }

    private void setIndecis(Document document) {
        setStartIndex(document);
        setEndIndex(document);
    }
}