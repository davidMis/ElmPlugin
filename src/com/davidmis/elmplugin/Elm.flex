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
WHITESPACE = ({CRLF} | [ \t\f])*

/* Comments */
SINGLE_LINE_COMMENT = ("--") [^\r\n]*
START_COMMENT = ("{--")
END_COMMENT = ("--}")

/* Strings */
STRING = {QUOTED_STRING} | {TRIPLE_QUOTED_STRING}
QUOTED_STRING = "\"" [^\r\n]* [^\\]"\""
TRIPLE_QUOTED_STRING = "\"\"\"" ~"\"\"\""

/* Expressions */

/* Declaration */

/* General */
IDENTIFIER = [:letter:] [A-Za-z0-9'_]*
NUMBER = [0-9]* | ([0-9]* [.]+ [0-9]+)



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
<YYINITIAL> "case"                                      { return ElmTypes.CASE; }
<YYINITIAL> "of"                                      { return ElmTypes.OF; }
<YYINITIAL> "in"                                      { return ElmTypes.IN; }
<YYINITIAL> "data"                                      { return ElmTypes.DATA; }
<YYINITIAL> "type"                                      { return ElmTypes.TYPE; }
<YYINITIAL> "module"                                      { return ElmTypes.MODULE; }
<YYINITIAL> "where"                                      { return ElmTypes.WHERE; }
<YYINITIAL> "import"                                      { return ElmTypes.IMPORT; }
<YYINITIAL> "as"                                      { return ElmTypes.AS; }
<YYINITIAL> "hiding"                                      { return ElmTypes.HIDING; }
<YYINITIAL> "open"                                      { return ElmTypes.OPEN; }
<YYINITIAL> "export"                                      { return ElmTypes.EXPORT; }
<YYINITIAL> "foreign"                                      { return ElmTypes.FOREIGN; }


<YYINITIAL> "="                                        { return ElmTypes.EQUALS; }
<YYINITIAL> ":"                                        { return ElmTypes.COLON; }
<YYINITIAL> ".."                                        { return ElmTypes.DOTDOT; }
<YYINITIAL> "."                                        { return ElmTypes.DOT; }
<YYINITIAL> "("                                        { return ElmTypes.LPAREN; }
<YYINITIAL> ")"                                        { return ElmTypes.RPAREN; }

<YYINITIAL> {IDENTIFIER}                                    { return ElmTypes.IDENTIFIER; }

<YYINITIAL> {STRING}                                    { return ElmTypes.STRING; }

<YYINITIAL> {NUMBER}                                        { return ElmTypes.NUMBER; }

<YYINITIAL> .                                               { return TokenType.BAD_CHARACTER; }