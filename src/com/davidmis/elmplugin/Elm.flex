package com.davidmis.elmplugin;

import com.intellij.lexer.FlexLexer;
import com.intellij.psi.tree.IElementType;
import com.davidmis.elmplugin.psi.ElmTypes;
import com.intellij.psi.TokenType;
import java.util.LinkedList;

%%

%{
   private final LinkedList<Integer> states = new LinkedList();

   private void yypushstate(int state) {
       states.addFirst(yystate());
       yybegin(state);
   }
   private void yypopstate() {
       final int state = states.removeFirst();
       yybegin(state);
   }
%}

%class ElmLexer
%implements FlexLexer
%unicode
%function advance
%type IElementType
%eof{
    return;
%eof}

CRLF = \n|\r|\r\n
SINGLE_LINE_COMMENT = ("--") [^\r\n]*
START_COMMENT = ("{--")
END_COMMENT = ("--}")

%xstate INCOMMENT

%%

<YYINITIAL> {SINGLE_LINE_COMMENT}                           { yybegin(YYINITIAL); return ElmTypes.COMMENT; }

<YYINITIAL, INCOMMENT> {START_COMMENT}                      {  yypushstate(INCOMMENT);


                                                              yypushback(2);
                                                              return ElmTypes.COMMENT; }




<INCOMMENT> . | {CRLF}                               { yybegin(INCOMMENT);return ElmTypes.COMMENT; }

<INCOMMENT> {END_COMMENT}                                   {
                                                              yypopstate();
                                                              return ElmTypes.COMMENT; }



<YYINITIAL> {CRLF}                                                      { yybegin(YYINITIAL); return ElmTypes.CRLF; }

<YYINITIAL> .                                                           { yybegin(YYINITIAL); return ElmTypes.WAITING; }