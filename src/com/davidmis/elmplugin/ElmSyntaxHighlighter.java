package com.davidmis.elmplugin;

import com.intellij.lexer.FlexAdapter;
import com.intellij.lexer.Lexer;
import com.intellij.openapi.editor.DefaultLanguageHighlighterColors;
import com.intellij.openapi.editor.colors.TextAttributesKey;
import com.intellij.openapi.editor.markup.TextAttributes;
import com.intellij.openapi.fileTypes.SyntaxHighlighterBase;
import com.intellij.psi.TokenType;
import com.intellij.psi.tree.IElementType;
import com.davidmis.elmplugin.psi.ElmTypes;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.io.Reader;

import static com.intellij.openapi.editor.colors.TextAttributesKey.createTextAttributesKey;

public class ElmSyntaxHighlighter extends SyntaxHighlighterBase {
    public static final TextAttributesKey COMMENT = createTextAttributesKey("ELM_COMMENT", DefaultLanguageHighlighterColors.LINE_COMMENT);
    public static final TextAttributesKey WAITING = createTextAttributesKey("ELM_WAITING", new TextAttributes(Color.WHITE, null, null, null, Font.PLAIN));
    public static final TextAttributesKey IDENTIFIER = createTextAttributesKey("ELM_IDENTIFIER", DefaultLanguageHighlighterColors.IDENTIFIER);
    public static final TextAttributesKey NUMBER = createTextAttributesKey("ELM_NUMBER", DefaultLanguageHighlighterColors.NUMBER);
    public static final TextAttributesKey IF = createTextAttributesKey("ELM_IF", DefaultLanguageHighlighterColors.KEYWORD);
    public static final TextAttributesKey THEN = createTextAttributesKey("ELM_THEN", DefaultLanguageHighlighterColors.KEYWORD);
    public static final TextAttributesKey ELSE = createTextAttributesKey("ELM_ELSE", DefaultLanguageHighlighterColors.KEYWORD);
    public static final TextAttributesKey COLON = createTextAttributesKey("ELM_COLON", DefaultLanguageHighlighterColors.OPERATION_SIGN);
    public static final TextAttributesKey EQUALS = createTextAttributesKey("ELM_EQUALS", DefaultLanguageHighlighterColors.OPERATION_SIGN);


    static final TextAttributesKey BAD_CHARACTER = createTextAttributesKey("ELM_BAD_CHARACTER",
            new TextAttributes(Color.RED, null, null, null, Font.BOLD));

    private static final TextAttributesKey[] BAD_CHAR_KEYS = new TextAttributesKey[]{BAD_CHARACTER};
    private static final TextAttributesKey[] COMMENT_KEYS = new TextAttributesKey[]{COMMENT};
    private static final TextAttributesKey[] WAITING_KEYS = new TextAttributesKey[]{WAITING};
    private static final TextAttributesKey[] IDENTIFIER_KEYS = new TextAttributesKey[]{IDENTIFIER};
    private static final TextAttributesKey[] NUMBER_KEYS = new TextAttributesKey[]{NUMBER};
    private static final TextAttributesKey[] RESERVED_KEYS = new TextAttributesKey[]{IF,THEN,ELSE};
    private static final TextAttributesKey[] OPERATOR_KEYS = new TextAttributesKey[]{COLON,EQUALS};
    private static final TextAttributesKey[] EMPTY_KEYS = new TextAttributesKey[0];

    @NotNull
    @Override
    public Lexer getHighlightingLexer() {
        return new FlexAdapter(new ElmLexer((Reader) null));
    }

    @NotNull
    @Override
    public TextAttributesKey[] getTokenHighlights(IElementType tokenType) {
        System.out.println("Token type: " + tokenType);
        if (tokenType.equals(ElmTypes.COMMENT)) {
            return COMMENT_KEYS;
        } else if (tokenType.equals(TokenType.BAD_CHARACTER)) {
            return BAD_CHAR_KEYS;
        } else if (tokenType.equals(ElmTypes.NUMBER)) {
            return NUMBER_KEYS;
        } else if (tokenType.equals(ElmTypes.COLON) || tokenType.equals(ElmTypes.EQUALS)) {
            return OPERATOR_KEYS;
        } else if (tokenType.equals(ElmTypes.IF) || tokenType.equals(ElmTypes.THEN) || tokenType.equals(ElmTypes.ELSE)) {
            return RESERVED_KEYS;
        } else if (tokenType.equals(ElmTypes.IDENTIFIER)) {
            return IDENTIFIER_KEYS;
        } else if (tokenType.equals(ElmTypes.WAITING)) {
            return WAITING_KEYS;
        } else {
            return EMPTY_KEYS;
        }
    }
}
