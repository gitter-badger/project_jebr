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


import java.util.ArrayList;
import java.util.List;
import javax.swing.text.BadLocationException;

import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.Token;
import org.fife.ui.rsyntaxtextarea.TokenMaker;
import org.fife.ui.rsyntaxtextarea.folding.Fold;
import org.fife.ui.rsyntaxtextarea.folding.FoldParser;
import org.fife.ui.rsyntaxtextarea.folding.FoldType;


public class CurlyFoldParser implements FoldParser {

	/**
	 * Whether to scan for C-style multi-line comments and make them foldable.
	 */
	private boolean foldableMultiLineComments;

	/**
	 * Whether this parser is folding Java.
	 */
	private final boolean java;

	/**
	 * Used to find import statements when folding Java code.
	 */
	private static final char[] KEYWORD_IMPORT = "import".toCharArray();

	/**
	 * Ending of a multi-line comment in C, C++, Java, etc.
	 */
	protected static final char[] C_MLC_END = "*/".toCharArray();


	/**
	 * Creates a fold parser that identifies foldable regions via curly braces
	 * as well as C-style multi-line comments.
	 */
	public CurlyFoldParser() {
		this(true, false);
	}


	/**
	 * Constructor.
	 *
	 * @param cStyleMultiLineComments Whether to scan for C-style multi-line
	 *        comments and make them foldable.
	 * @param java Whether this parser is folding Java.  This adds extra
	 *        parsing rules, such as grouping all import statements into a
	 *        fold section.
	 */
	public CurlyFoldParser(boolean cStyleMultiLineComments, boolean java) {
		this.foldableMultiLineComments = cStyleMultiLineComments;
		this.java = java;
	}


	/**
	 * Returns whether multi-line comments are foldable with this parser.
	 *
	 * @return Whether multi-line comments are foldable.
	 * @see #setFoldableMultiLineComments(boolean)
	 */
	public boolean getFoldableMultiLineComments() {
		return foldableMultiLineComments;
	}


	/**
	 * {@inheritDoc}
	 */
	public List<Fold> getFolds(RSyntaxTextArea textArea) {

		List<Fold> folds = new ArrayList<Fold>();

		Fold currentFold = null;
		int lineCount = textArea.getLineCount();
		boolean inMLC = false;
		int mlcStart = 0;
		int importStartLine = -1;
		int lastSeenImportLine = -1;
		int importGroupStartOffs = -1;
		int importGroupEndOffs = -1;

		try {

			for (int line=0; line<lineCount; line++) {

				Token t = textArea.getTokenListForLine(line);
				while (t!=null && t.isPaintable()) {

					if (getFoldableMultiLineComments() && t.isComment()) {

						// Java-specific stuff
						if (java) {

							if (importStartLine>-1) {
								if (lastSeenImportLine>importStartLine) {
									Fold fold = null;
									// Any imports found *should* be a top-level fold,
									// but we're extra lenient here and allow groups
									// of them anywhere to keep our parser better-behaved
									// if they have random "imports" throughout code.
									if (currentFold==null) {
										fold = new Fold(FoldType.IMPORTS,
												textArea, importGroupStartOffs);
										folds.add(fold);
									}
									else {
										fold = currentFold.createChild(FoldType.IMPORTS,
												importGroupStartOffs);
									}
									fold.setEndOffset(importGroupEndOffs);
								}
								importStartLine = lastSeenImportLine =
								importGroupStartOffs = importGroupEndOffs = -1;
							}

						}

						if (inMLC) {
							// If we found the end of an MLC that started
							// on a previous line...
							if (t.endsWith(C_MLC_END)) {
								int mlcEnd = t.getEndOffset() - 1;
								if (currentFold==null) {
									currentFold = new Fold(FoldType.COMMENT, textArea, mlcStart);
									currentFold.setEndOffset(mlcEnd);
									folds.add(currentFold);
									currentFold = null;
								}
								else {
									currentFold = currentFold.createChild(FoldType.COMMENT, mlcStart);
									currentFold.setEndOffset(mlcEnd);
									currentFold = currentFold.getParent();
								}
								//System.out.println("Ending MLC at: " + mlcEnd + ", parent==" + currentFold);
								inMLC = false;
								mlcStart = 0;
							}
							// Otherwise, this MLC is continuing on to yet
							// another line.
						}
						else {
							// If we're an MLC that ends on a later line...
							if (t.getType()!=Token.COMMENT_EOL && !t.endsWith(C_MLC_END)) {
								//System.out.println("Starting MLC at: " + t.offset);
								inMLC = true;
								mlcStart = t.getOffset();
							}
						}

					}

					else if (isLeftCurly(t)) {

						// Java-specific stuff
						if (java) {

							if (importStartLine>-1) {
								if (lastSeenImportLine>importStartLine) {
									Fold fold = null;
									// Any imports found *should* be a top-level fold,
									// but we're extra lenient here and allow groups
									// of them anywhere to keep our parser better-behaved
									// if they have random "imports" throughout code.
									if (currentFold==null) {
										fold = new Fold(FoldType.IMPORTS,
												textArea, importGroupStartOffs);
										folds.add(fold);
									}
									else {
										fold = currentFold.createChild(FoldType.IMPORTS,
												importGroupStartOffs);
									}
									fold.setEndOffset(importGroupEndOffs);
								}
								importStartLine = lastSeenImportLine =
								importGroupStartOffs = importGroupEndOffs = -1;
							}

						}

						if (currentFold==null) {
							currentFold = new Fold(FoldType.CODE, textArea, t.getOffset());
							folds.add(currentFold);
						}
						else {
							currentFold = currentFold.createChild(FoldType.CODE, t.getOffset());
						}

					}

					else if (isRightCurly(t)) {

						if (currentFold!=null) {
							currentFold.setEndOffset(t.getOffset());
							Fold parentFold = currentFold.getParent();
							//System.out.println("... Adding regular fold at " + t.offset + ", parent==" + parentFold);
							// Don't add fold markers for single-line blocks
							if (currentFold.isOnSingleLine()) {
								if (!currentFold.removeFromParent()) {
									folds.remove(folds.size()-1);
								}
							}
							currentFold = parentFold;
						}

					}

					// Java-specific folding rules
					else if (java) {

						if (t.is(Token.RESERVED_WORD, KEYWORD_IMPORT)) {
							if (importStartLine==-1) {
								importStartLine = line;
								importGroupStartOffs = t.getOffset();
								importGroupEndOffs = t.getOffset();
							}
							lastSeenImportLine = line;
						}

						else if (importStartLine>-1 &&
								t.isIdentifier() &&//SEPARATOR &&
								t.isSingleChar(';')) {
							importGroupEndOffs = t.getOffset();
						}

					}

					t = t.getNextToken();

				}

			}

		} catch (BadLocationException ble) { // Should never happen
			ble.printStackTrace();
		}

		return folds;

	}


	/**
	 * Returns whether the token is a left curly brace.  This method exists
	 * so subclasses can provide their own curly brace definition.
	 *
	 * @param t The token.
	 * @return Whether it is a left curly brace.
	 * @see #isRightCurly(Token)
	 */
	public boolean isLeftCurly(Token t) {
		return t.isLeftCurly();
	}


	/**
	 * Returns whether the token is a right curly brace.  This method exists
	 * so subclasses can provide their own curly brace definition.
	 *
	 * @param t The token.
	 * @return Whether it is a right curly brace.
	 * @see #isLeftCurly(Token)
	 */
	public boolean isRightCurly(Token t) {
            
		return t.isRightCurly();
	}


	/**
	 * Sets whether multi-line comments are foldable with this parser.
	 *
	 * @param foldable Whether multi-line comments are foldable.
	 * @see #getFoldableMultiLineComments()
	 */
	public void setFoldableMultiLineComments(boolean foldable) {
		this.foldableMultiLineComments = foldable;
	}


}
