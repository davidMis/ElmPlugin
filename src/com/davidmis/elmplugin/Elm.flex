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
%caseless
%type IElementType
%eof{
    return;
%eof}

CRLF = (\n|\r|\r\n)+
WHITESPACE = {CRLF} | [ \t\f]*

/* Comments */
SINGLE_LINE_COMMENT = ("--") [^\r\n]*
START_COMMENT = ("{--")
END_COMMENT = ("--}")

/* Strings */

/* Expressions */

/* Declaration */

/* General */
IDENTIFIER = [:letter:] [A-Za-z0-9'_]*
NUMBER = [0-9]* | ([0-9]* [.]+ [0-9]+)
//RESERVED = "if"     | "then" | "else" | "case" | "of" |"let" | "in" |"data" | "type" | "module" |
//           "where"  |"import" |  "as" | "hiding" | "open" |"export" | "foreign"


%xstate INCOMMENT

%%

/* Comments */
<YYINITIAL> {SINGLE_LINE_COMMENT}                           { return ElmTypes.COMMENT; }

<YYINITIAL, INCOMMENT> {START_COMMENT}                      { yypushstate(INCOMMENT);
                                                              yypushback(2);
                                                              return ElmTypes.COMMENT; }

<INCOMMENT> [^\-\{\}]* | .                                  { return ElmTypes.COMMENT; }

<INCOMMENT> {END_COMMENT}                                   { yypopstate();
                                                              return ElmTypes.COMMENT; }


/* Strings */


/* General */

<YYINITIAL> {WHITESPACE}                                { return ElmTypes.WHITESPACE; }

<YYINITIAL> "if"                                      { return ElmTypes.IF; }
<YYINITIAL> "then"                                      { return ElmTypes.THEN; }
<YYINITIAL> "else"                                      { return ElmTypes.ELSE; }
<YYINITIAL> "="                                        { return ElmTypes.EQUALS; }
<YYINITIAL> ":"                                        { return ElmTypes.COLON; }

<YYINITIAL> {IDENTIFIER}                                    { return ElmTypes.IDENTIFIER; }

<YYINITIAL> {NUMBER}                                        { return ElmTypes.NUMBER; }

<YYINITIAL> {CRLF}                                          { return ElmTypes.CRLF; }

<YYINITIAL> .                                               { return ElmTypes.WAITING; }