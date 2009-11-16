package com.swe573.sakalatex;

import org.eclipse.jface.text.TextAttribute;
import org.eclipse.jface.text.rules.EndOfLineRule;
import org.eclipse.jface.text.rules.IRule;
import org.eclipse.jface.text.rules.IWhitespaceDetector;
import org.eclipse.jface.text.rules.IWordDetector;
import org.eclipse.jface.text.rules.MultiLineRule;
import org.eclipse.jface.text.rules.NumberRule;
import org.eclipse.jface.text.rules.RuleBasedScanner;
import org.eclipse.jface.text.rules.SingleLineRule;
import org.eclipse.jface.text.rules.Token;
import org.eclipse.jface.text.rules.WhitespaceRule;
import org.eclipse.jface.text.rules.WordRule;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Display;

public class SakalTokenScanner extends RuleBasedScanner {

	public SakalTokenScanner() {
         Token string = new Token(new TextAttribute(new Color(Display.getCurrent(), 0,0,255)));
         Token bracketToken = new Token(new TextAttribute(new Color(Display.getCurrent(), 0,255,255)));
         Token squareToken = new Token(new TextAttribute(new Color(Display.getCurrent(), 0,0,255)));
         Token numberToken = new Token(new TextAttribute(new Color(Display.getCurrent(), 0,0,255)));
         Token commentToken = new Token(new TextAttribute(new Color(Display.getCurrent(), 0,0,255)));
         Token commandToken = new Token(new TextAttribute(new Color(Display.getCurrent(), 255,0,255)));
         //add tokens for each reserved word
//         for (int n = 0; n < Parser.KEYWORDS.length; n++) {
//            rule.addWord(Parser.KEYWORDS[n], keyword);
//         }
         setRules(new IRule[] {
	        new WordRule(new IWordDetector() {
	        	public boolean isWordStart(char c) {
	        		return c == '\\'? true:false;
	        	}

				@Override
				public boolean isWordPart(char c) {
					return Character.isLetter(c) ? true:false;
				}
	        	
	        }, commandToken),
	        new NumberRule(numberToken),
	        new MultiLineRule("{", "}", bracketToken, '\\'),
	        new MultiLineRule("[", "]", squareToken, '\\'),
	        new EndOfLineRule("%", commentToken, '\\'),	 
            new SingleLineRule("\\", "\\", string, '\\'),
            new SingleLineRule("'", "'", string, '\\'),
            new WhitespaceRule(new IWhitespaceDetector() {
               public boolean isWhitespace(char c) {
                  return Character.isWhitespace(c);
               }
            }),
         });

	}
}
