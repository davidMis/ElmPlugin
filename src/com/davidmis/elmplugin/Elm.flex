package com.davidmis.elmplugin;

import com.intellij.lexer.FlexLexer;
import com.intellij.psi.tree.IElementType;
import com.davidmis.elmplugin.psi.ElmTypes;
import com.intellij.psi.TokenType;

%%

%class ElmLexer
%implements FlexLexer
%unicode
%function advance
%type IElementType
%eof{  return;
%eof}

CRLF = \n|\r|\r\n
SINGLE_LINE_COMMENT = ("--") [^\r\n]*
COMMENT = {SINGLE_LINE_COMMENT}

%%

<YYINITIAL> {COMMENT}                                       { yybegin(YYINITIAL); return ElmTypes.COMMENT; }

{CRLF}                                                      { yybegin(YYINITIAL); return ElmTypes.CRLF; }

.*                                                          { return TokenType.BAD_CHARACTER; }