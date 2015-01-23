package com.davidmis.elmplugin;

import com.intellij.lexer.FlexLexer;
import com.intellij.psi.tree.IElementType;
import com.davidmis.elmplugin.psi.ElmTypes;
import com.intellij.psi.TokenType;

%%

%{
    int commentLevel = 0;
%}

%class ElmLexer
%implements FlexLexer
%unicode
%function advance
%type IElementType
%eof{  return;
%eof}

CRLF = \n|\r|\r\n
SINGLE_LINE_COMMENT = ("--") [^\r\n]*
START_COMMENT = ("{--")
END_COMMENT = ("--}")

%xstate INCOMMENT

%%

<YYINITIAL> {SINGLE_LINE_COMMENT}                           { return ElmTypes.COMMENT; }

<YYINITIAL, INCOMMENT> {START_COMMENT}                      { yybegin(INCOMMENT);
                                                              commentLevel += 1;
                                                              yypushback(2);
                                                              return ElmTypes.COMMENT; }

<INCOMMENT> . | {CRLF}                               { yybegin(INCOMMENT);return ElmTypes.COMMENT; }

<INCOMMENT> {END_COMMENT}                                   { yybegin(INCOMMENT);
                                                              commentLevel -= 1;
                                                              if(commentLevel <= 0) { yybegin(YYINITIAL); }
                                                              return ElmTypes.COMMENT; }



{CRLF}                                                      { yybegin(YYINITIAL); return ElmTypes.CRLF; }

.                                                           { return ElmTypes.WAITING; }