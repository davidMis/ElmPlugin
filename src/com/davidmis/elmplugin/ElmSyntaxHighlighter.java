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
import org.apache.commons.lang.ArrayUtils;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.io.Reader;

import static com.intellij.openapi.editor.colors.TextAttributesKey.createTextAttributesKey;

public class ElmSyntaxHighlighter extends SyntaxHighlighterBase {
    public static final TextAttributesKey COMMENT = createTextAttributesKey("ELM_COMMENT", DefaultLanguageHighlighterColors.LINE_COMMENT);
    public static final TextAttributesKey IDENTIFIER = createTextAttributesKey("ELM_IDENTIFIER", DefaultLanguageHighlighterColors.IDENTIFIER);
    public static final TextAttributesKey NUMBER = createTextAttributesKey("ELM_NUMBER", DefaultLanguageHighlighterColors.NUMBER);
    public static final TextAttributesKey STRING = createTextAttributesKey("ELM_STRING", DefaultLanguageHighlighterColors.STRING);


    public static final TextAttributesKey IF = createTextAttributesKey("ELM_IF", DefaultLanguageHighlighterColors.KEYWORD);
    public static final TextAttributesKey THEN = createTextAttributesKey("ELM_THEN", DefaultLanguageHighlighterColors.KEYWORD);
    public static final TextAttributesKey ELSE = createTextAttributesKey("ELM_ELSE", DefaultLanguageHighlighterColors.KEYWORD);
    public static final TextAttributesKey CASE = createTextAttributesKey("ELM_IF", DefaultLanguageHighlighterColors.KEYWORD);
    public static final TextAttributesKey OF = createTextAttributesKey("ELM_OF", DefaultLanguageHighlighterColors.KEYWORD);
    public static final TextAttributesKey LET = createTextAttributesKey("ELM_LET", DefaultLanguageHighlighterColors.KEYWORD);
    public static final TextAttributesKey IN = createTextAttributesKey("ELM_IN", DefaultLanguageHighlighterColors.KEYWORD);
    public static final TextAttributesKey DATA = createTextAttributesKey("ELM_DATA", DefaultLanguageHighlighterColors.KEYWORD);
    public static final TextAttributesKey TYPE = createTextAttributesKey("ELM_TYPE", DefaultLanguageHighlighterColors.KEYWORD);
    public static final TextAttributesKey MODULE = createTextAttributesKey("ELM_MODULE", DefaultLanguageHighlighterColors.KEYWORD);
    public static final TextAttributesKey WHERE = createTextAttributesKey("ELM_WHERE", DefaultLanguageHighlighterColors.KEYWORD);
    public static final TextAttributesKey IMPORT = createTextAttributesKey("ELM_IMPORT", DefaultLanguageHighlighterColors.KEYWORD);
    public static final TextAttributesKey AS = createTextAttributesKey("ELM_AS", DefaultLanguageHighlighterColors.KEYWORD);
    public static final TextAttributesKey HIDING = createTextAttributesKey("ELM_HIDING", DefaultLanguageHighlighterColors.KEYWORD);
    public static final TextAttributesKey OPEN = createTextAttributesKey("ELM_OPEN", DefaultLanguageHighlighterColors.KEYWORD);
    public static final TextAttributesKey EXPORT = createTextAttributesKey("ELM_EXPORT", DefaultLanguageHighlighterColors.KEYWORD);
    public static final TextAttributesKey FOREIGN = createTextAttributesKey("ELM_FOREIGN", DefaultLanguageHighlighterColors.KEYWORD);

    public static final TextAttributesKey COLON = createTextAttributesKey("ELM_COLON", DefaultLanguageHighlighterColors.PREDEFINED_SYMBOL);
    public static final TextAttributesKey EQUALS = createTextAttributesKey("ELM_EQUALS", DefaultLanguageHighlighterColors.PREDEFINED_SYMBOL);
    public static final TextAttributesKey DOTDOT = createTextAttributesKey("ELM_DOTDOT", DefaultLanguageHighlighterColors.PREDEFINED_SYMBOL);
    public static final TextAttributesKey LPAREN = createTextAttributesKey("ELM_LPAREN", DefaultLanguageHighlighterColors.PREDEFINED_SYMBOL);
    public static final TextAttributesKey RPAREN = createTextAttributesKey("ELM_RPAREN", DefaultLanguageHighlighterColors.PREDEFINED_SYMBOL);
    public static final TextAttributesKey DOT = createTextAttributesKey("ELM_DOT", DefaultLanguageHighlighterColors.PREDEFINED_SYMBOL);


    static final TextAttributesKey BAD_CHARACTER = createTextAttributesKey("ELM_BAD_CHARACTER",
            new TextAttributes(Color.RED, null, null, null, Font.BOLD));

    private static final TextAttributesKey[] BAD_CHAR_KEYS = new TextAttributesKey[]{BAD_CHARACTER};
    private static final TextAttributesKey[] COMMENT_KEYS = new TextAttributesKey[]{COMMENT};
    private static final TextAttributesKey[] STRING_KEYS = new TextAttributesKey[]{STRING};
    private static final TextAttributesKey[] IDENTIFIER_KEYS = new TextAttributesKey[]{IDENTIFIER};
    private static final TextAttributesKey[] NUMBER_KEYS = new TextAttributesKey[]{NUMBER};
    private static final TextAttributesKey[] RESERVED_KEYS = new TextAttributesKey[]{IF,THEN,ELSE,CASE,OF,LET,IN,DATA,TYPE,MODULE,
                                                                                     WHERE,IMPORT,AS,HIDING,OPEN,EXPORT,FOREIGN};
    private static final TextAttributesKey[] OPERATOR_KEYS = new TextAttributesKey[]{COLON,EQUALS,DOTDOT,LPAREN,RPAREN,DOT};
    private static final TextAttributesKey[] EMPTY_KEYS = new TextAttributesKey[0];

    @NotNull
    @Override
    public Lexer getHighlightingLexer() {
        return new FlexAdapter(new ElmLexer((Reader) null));
    }

    @NotNull
    @Override
    public TextAttributesKey[] getTokenHighlights(IElementType tokenType) {
        if (tokenType.equals(ElmTypes.COMMENT)) {
            return COMMENT_KEYS;
        } else if (tokenType.equals(TokenType.BAD_CHARACTER)) {
            return BAD_CHAR_KEYS;
        } else if (tokenType.equals(ElmTypes.NUMBER)) {
            return NUMBER_KEYS;
        } else if (tokenType.equals(ElmTypes.STRING)) {
            return STRING_KEYS;
        } else if (
                tokenType.equals(ElmTypes.COLON) || tokenType.equals(ElmTypes.EQUALS) || tokenType.equals(ElmTypes.DOTDOT) ||
                tokenType.equals(ElmTypes.LPAREN) || tokenType.equals(ElmTypes.RPAREN)|| tokenType.equals(ElmTypes.DOT)) {
            return OPERATOR_KEYS;
        } else if (
                tokenType.equals(ElmTypes.IF) || tokenType.equals(ElmTypes.THEN) || tokenType.equals(ElmTypes.ELSE) ||
                tokenType.equals(ElmTypes.CASE) || tokenType.equals(ElmTypes.OF) || tokenType.equals(ElmTypes.LET) ||
                tokenType.equals(ElmTypes.IN) || tokenType.equals(ElmTypes.DATA) || tokenType.equals(ElmTypes.TYPE) ||
                tokenType.equals(ElmTypes.MODULE) || tokenType.equals(ElmTypes.WHERE) || tokenType.equals(ElmTypes.IMPORT) ||
                tokenType.equals(ElmTypes.AS) || tokenType.equals(ElmTypes.HIDING) || tokenType.equals(ElmTypes.OPEN) ||
                tokenType.equals(ElmTypes.EXPORT) || tokenType.equals(ElmTypes.FOREIGN)) {
            return RESERVED_KEYS;
        } else if (tokenType.equals(ElmTypes.IDENTIFIER)) {
            return IDENTIFIER_KEYS;
        } else {
            return EMPTY_KEYS;
        }
    }
}
