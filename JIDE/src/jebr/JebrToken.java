/*
 * Code Name: Jebr.java
 * Created By: 'Umar A.Abu Baker, Fahed N.Shehab <http://www.geekybedouin.com>, <>
 * Copyright (c) 2014 'Umar A.Abu Baker and Fahed N.Shehab <umr.baker@gmail.com>, <>
 * 
 * 
 * License: 
 * This file is part of Project Jebr.

    Project Jebr is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License.

    Project Jebr is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program. If not, see <http://www.gnu.org/licenses/gpl-3.0.txt>.
 * 
 * 
 * 
 * 
 * Version: 1.04(Delta)
 * Description: This file contains the source of JIDE(Jebr IDE) which is the official IDE for the Jebr language.
 * 
 * 
 * Wanna edit the code? READ FIRST:
 * 
 * For the brave souls who get this far: You are the chosen ones,
 * the valiant knights of programming who toil away, without rest,
 * fixing our most awful code. To you, true saviors, kings of men,
 * I say this: never gonna give you up, never gonna let you down,
 * never gonna run around and desert you. Never gonna make you cry,
 * never gonna say goodbye. Never gonna tell a lie and hurt you.
 * 
 * Now, take the oath:
 * 
 * Errors gather, and now my maintenance begins. It shall not end until my death.
 * I shall take no wife, hold no lands, father no children. I shall wear no crowns and win no glory.
 * I shall live and die maintaining this code. I am the sword in the darkness. I am the maintainer of the code.
 * I am the fire that burns against the cold, the light that brings the dawn, the horn that wakes the sleepers,
 * the shield that guards the realms of men. I pledge my life and honor to maintain this code,
 * for this night and all the nights to come.
 */

package jebr;

import javax.swing.text.Segment;
import org.fife.ui.rsyntaxtextarea.AbstractTokenMaker;
import org.fife.ui.rsyntaxtextarea.RSyntaxUtilities;
import org.fife.ui.rsyntaxtextarea.Token;
import org.fife.ui.rsyntaxtextarea.TokenMap;


public class JebrToken extends AbstractTokenMaker {
    private int currentTokenStart;
    private int currentTokenType;
    
    
    
    
    @Override
    public TokenMap getWordsToHighlight() {
        TokenMap tokenMap = new TokenMap();
        
        
        tokenMap.put("character", Token.DATA_TYPE);//error
        tokenMap.put("integer", Token.DATA_TYPE);
        tokenMap.put("float", Token.DATA_TYPE);
        tokenMap.put("double", Token.DATA_TYPE);
        tokenMap.put("space", Token.DATA_TYPE);
        tokenMap.put("vector", Token.DATA_TYPE);
        tokenMap.put("long", Token.DATA_TYPE);
        tokenMap.put("array", Token.DATA_TYPE);
        tokenMap.put("matrix", Token.DATA_TYPE);
        
        tokenMap.put("@merge",  Token.RESERVED_WORD);
        tokenMap.put("if",   Token.RESERVED_WORD);
        tokenMap.put("else",    Token.RESERVED_WORD);
        tokenMap.put("while", Token.RESERVED_WORD);
        tokenMap.put("for",   Token.RESERVED_WORD);
        tokenMap.put("constant", Token.RESERVED_WORD);
        tokenMap.put("beginif", Token.RESERVED_WORD);
        tokenMap.put("endif", Token.RESERVED_WORD);
        tokenMap.put("beginwhile", Token.RESERVED_WORD);
        tokenMap.put("endwhile", Token.RESERVED_WORD);
        tokenMap.put("beginfor", Token.RESERVED_WORD);
        tokenMap.put("endfor", Token.RESERVED_WORD);
        tokenMap.put("print", Token.RESERVED_WORD);
        tokenMap.put("return", Token.RESERVED_WORD);
        tokenMap.put("break", Token.RESERVED_WORD);
        tokenMap.put("continue", Token.RESERVED_WORD);
        tokenMap.put("in", Token.RESERVED_WORD);
        tokenMap.put("out", Token.RESERVED_WORD);
        tokenMap.put("ino", Token.RESERVED_WORD);
        tokenMap.put("class", Token.RESERVED_WORD);
        tokenMap.put("sub_routine", Token.RESERVED_WORD);//error
        tokenMap.put("in",  Token.RESERVED_WORD);
        tokenMap.put("out",  Token.RESERVED_WORD);
        tokenMap.put("ino",  Token.RESERVED_WORD);
        
        
        
        tokenMap.put("=", Token.OPERATOR);
        tokenMap.put("=?", Token.OPERATOR);
        tokenMap.put("+", Token.OPERATOR);
        tokenMap.put("-", Token.OPERATOR);
        tokenMap.put("*", Token.OPERATOR);
        tokenMap.put("/", Token.OPERATOR);
        tokenMap.put("+=", Token.OPERATOR);
        tokenMap.put("-=", Token.OPERATOR);
        tokenMap.put("*=", Token.OPERATOR);
        tokenMap.put("/=", Token.OPERATOR);
        tokenMap.put(":=", Token.OPERATOR);
        tokenMap.put(":", Token.OPERATOR);// no need
        tokenMap.put("$", Token.OPERATOR);
        tokenMap.put("-$", Token.OPERATOR);
        tokenMap.put("~", Token.OPERATOR);
        tokenMap.put("#", Token.OPERATOR);
        tokenMap.put("^", Token.OPERATOR);
        tokenMap.put("?", Token.OPERATOR);
        tokenMap.put("!", Token.OPERATOR);
        tokenMap.put("!=", Token.OPERATOR); //error for us
        tokenMap.put("%", Token.OPERATOR);
        tokenMap.put("&&", Token.OPERATOR);
        tokenMap.put("||", Token.OPERATOR);
        
        tokenMap.put("openfile", Token.FUNCTION);//error
        tokenMap.put("readfile", Token.FUNCTION);//error
        tokenMap.put("writefile", Token.FUNCTION);
        tokenMap.put("read", Token.FUNCTION);
        tokenMap.put("print", Token.FUNCTION);
        tokenMap.put("closefiles", Token.FUNCTION);//error
        //with braces
        tokenMap.put("openfile(", Token.FUNCTION);//error
        tokenMap.put("readfile(", Token.FUNCTION);//error
        tokenMap.put("writefile(", Token.FUNCTION);
        tokenMap.put("read(", Token.FUNCTION);
        tokenMap.put("print(", Token.FUNCTION);
        tokenMap.put("closefiles(", Token.FUNCTION);//error
        tokenMap.put("}", Token.SEPARATOR);
        tokenMap.put("{", Token.SEPARATOR);
        tokenMap.put(")", Token.SEPARATOR);
        tokenMap.put("(", Token.SEPARATOR);/*
        /*tokenMap.put("}", Token.SEPARATOR);
        tokenMap.put("begin", Token.SEPARATOR);
        tokenMap.put("}", Token.SEPARATOR);
        tokenMap.put("end", Token.SEPARATOR);*/
        
        
        
   
   return tokenMap;
    }

    @Override
    public Token getTokenList(Segment text, int startTokenType, int startOffset) { 
        resetTokenList();

        char[] array = text.array;
        int offset = text.offset;
        int count = text.count;
        int end = offset + count;
   
         int newStartOffset = startOffset - offset;

         currentTokenStart = offset;
         currentTokenType  = startTokenType;

         for (int i=offset; i<end; i++) {

                    char c = array[i];

                    switch (currentTokenType) {

                           case Token.NULL:

                           currentTokenStart = i;   // Starting a new token here.

                    switch (c) {

                          case ' ':
                          case '\t':
                  currentTokenType = Token.WHITESPACE;
                  break;

               case '"':
                  currentTokenType = Token.LITERAL_STRING_DOUBLE_QUOTE;
                  break;
               
               case '#':
                  currentTokenType = Token.COMMENT_EOL;
                  break;

               default:
                  if (RSyntaxUtilities.isDigit(c)) {
                     currentTokenType = Token.LITERAL_NUMBER_DECIMAL_INT;
                     break;
                  }
                  else if (RSyntaxUtilities.isLetter(c) || c=='/' || c=='_') {
                     currentTokenType = Token.IDENTIFIER;
                     break;
                  }
                  
                  // Anything not currently handled - mark as an identifier
                  currentTokenType = Token.IDENTIFIER;
                  break;

            } // End of switch (c).

            break;

         case Token.WHITESPACE:

            switch (c) {

               case ' ':
               case '\t':
                  break;   // Still whitespace.

               case '"':
                  addToken(text, currentTokenStart,i-1, Token.WHITESPACE, newStartOffset+currentTokenStart);
                  currentTokenStart = i;
                  currentTokenType = Token.LITERAL_STRING_DOUBLE_QUOTE;
                  break;

               case '/':
                  addToken(text, currentTokenStart,i-1, Token.WHITESPACE, newStartOffset+currentTokenStart);
                  currentTokenStart = i;
                  currentTokenType = Token.COMMENT_EOL;
                  break;

               default:   // Add the whitespace token and start anew.

                  addToken(text, currentTokenStart,i-1, Token.WHITESPACE, newStartOffset+currentTokenStart);
                  currentTokenStart = i;

                  if (RSyntaxUtilities.isDigit(c)) {
                     currentTokenType = Token.LITERAL_NUMBER_DECIMAL_INT;
                     break;
                  }
                  else if (RSyntaxUtilities.isLetter(c) || c=='/' || c=='_') {
                     currentTokenType = Token.IDENTIFIER;
                     break;
                  }

                  // Anything not currently handled - mark as identifier
                  currentTokenType = Token.IDENTIFIER;

            } // End of switch (c)

            break;

         default: // Should never happen
         case Token.IDENTIFIER:

            switch (c) {

               case ' ':
               case '\t':
                  addToken(text, currentTokenStart,i-1, Token.IDENTIFIER, newStartOffset+currentTokenStart);
                  currentTokenStart = i;
                  currentTokenType = Token.WHITESPACE;
                  break;

               case '"':
                  addToken(text, currentTokenStart,i-1, Token.IDENTIFIER, newStartOffset+currentTokenStart);
                  currentTokenStart = i;
                  currentTokenType = Token.LITERAL_STRING_DOUBLE_QUOTE;
                  break;

               default:
                  if (RSyntaxUtilities.isLetterOrDigit(c) || c=='/' || c=='_') {
                     break;   // Still an identifier of some type.
                  }
                  // Otherwise, we're still an identifier (?).

            } // End of switch (c).

            break;

         case Token.LITERAL_NUMBER_DECIMAL_INT:

            switch (c) {

               case ' ':
               case '\t':
                  addToken(text, currentTokenStart,i-1, Token.LITERAL_NUMBER_DECIMAL_INT, newStartOffset+currentTokenStart);
                  currentTokenStart = i;
                  currentTokenType = Token.WHITESPACE;
                  break;

               case '"':
                  addToken(text, currentTokenStart,i-1, Token.LITERAL_NUMBER_DECIMAL_INT, newStartOffset+currentTokenStart);
                  currentTokenStart = i;
                  currentTokenType = Token.LITERAL_STRING_DOUBLE_QUOTE;
                  break;

               default:

                  if (RSyntaxUtilities.isDigit(c)) {
                     break;   // Still a literal number.
                  }

                  // Otherwise, remember this was a number and start over.
                  addToken(text, currentTokenStart,i-1, Token.LITERAL_NUMBER_DECIMAL_INT, newStartOffset+currentTokenStart);
                  i--;
                  currentTokenType = Token.NULL;

            } // End of switch (c).

            break;

         case Token.COMMENT_EOL:
            i = end - 1;
            addToken(text, currentTokenStart,i, currentTokenType, newStartOffset+currentTokenStart);
            // We need to set token type to null so at the bottom we don't add one more token.
            currentTokenType = Token.NULL;
            break;

         case Token.LITERAL_STRING_DOUBLE_QUOTE:
            if (c=='"') {
               addToken(text, currentTokenStart,i, Token.LITERAL_STRING_DOUBLE_QUOTE, newStartOffset+currentTokenStart);
               currentTokenType = Token.NULL;
            }
            break;

      } // End of switch (currentTokenType).

   } // End of for (int i=offset; i<end; i++).

   switch (currentTokenType) {

      // Remember what token type to begin the next line with.
      case Token.LITERAL_STRING_DOUBLE_QUOTE:
         addToken(text, currentTokenStart,end-1, currentTokenType, newStartOffset+currentTokenStart);
         break;

      // Do nothing if everything was okay.
      case Token.NULL:
         addNullToken();
         break;

      // All other token types don't continue to the next line...
      default:
         addToken(text, currentTokenStart,end-1, currentTokenType, newStartOffset+currentTokenStart);
         addNullToken();

   }

   // Return the first token in our linked list.
   return firstToken;
    }
    
    @Override
    public void addToken(Segment segment, int start, int end, int tokenType, int startOffset){
        // VERY IMPORTANT          This assumes all keywords, etc. were parsed as "identifiers."
        if (tokenType==Token.IDENTIFIER) {
            int value = wordsToHighlight.get(segment, start, end);
            if (value != -1) {
                              tokenType = value;
             }
         }
        super.addToken(segment, start, end, tokenType, startOffset);
        
    }
    
    
}
