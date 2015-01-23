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

    static final TextAttributesKey BAD_CHARACTER = createTextAttributesKey("ELM_BAD_CHARACTER",
            new TextAttributes(Color.RED, null, null, null, Font.BOLD));

    private static final TextAttributesKey[] BAD_CHAR_KEYS = new TextAttributesKey[]{BAD_CHARACTER};
    private static final TextAttributesKey[] COMMENT_KEYS = new TextAttributesKey[]{COMMENT};
    private static final TextAttributesKey[] WAITING_KEYS = new TextAttributesKey[]{WAITING};
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
        } else if (tokenType.equals(ElmTypes.WAITING)) {
            return WAITING_KEYS;
        } else {
            return EMPTY_KEYS;
        }
    }
}
