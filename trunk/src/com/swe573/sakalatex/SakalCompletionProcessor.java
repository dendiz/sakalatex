package com.swe573.sakalatex;

import java.util.ArrayList;

import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.jface.text.contentassist.ICompletionProposal;
import org.eclipse.jface.text.contentassist.IContentAssistProcessor;
import org.eclipse.jface.text.contentassist.IContextInformation;
import org.eclipse.jface.text.contentassist.IContextInformationValidator;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;

public class SakalCompletionProcessor implements IContentAssistProcessor {
    private final IContextInformation[] NO_CONTEXTS = new IContextInformation[0];
    private final char[] PROPOSAL_ACTIVATION_CHARS = new char[] { 's','f','p','n','m', };
    private ICompletionProposal[] NO_COMPLETIONS = new ICompletionProposal[0];

	@SuppressWarnings("unchecked")
	@Override
	public ICompletionProposal[] computeCompletionProposals(ITextViewer viewer,
			int offset) {
		// TODO Auto-generated method stub
        try {
            IDocument document = viewer.getDocument();
            ArrayList result = new ArrayList();
//            String prefix = lastWord(document, offset);
//            String indent = lastIndent(document, offset);
            result.add(new ICompletionProposal() {
				
				@Override
				public Point getSelection(IDocument document) {
					// TODO Auto-generated method stub
					return null;
				}
				
				@Override
				public Image getImage() {
					// TODO Auto-generated method stub
					return null;
				}
				
				@Override
				public String getDisplayString() {
					// TODO Auto-generated method stub
					return "hede";
				}
				
				@Override
				public IContextInformation getContextInformation() {
					// TODO Auto-generated method stub
					return null;
				}
				
				@Override
				public String getAdditionalProposalInfo() {
					// TODO Auto-generated method stub
					return null;
				}
				
				@Override
				public void apply(IDocument document) {
					// TODO Auto-generated method stub
					
				}
			});
            return (ICompletionProposal[]) result.toArray(new ICompletionProposal[result.size()]);
         } catch (Exception e) {
            // ... log the exception ...
            return NO_COMPLETIONS;
         }

	}
	private String lastWord(IDocument doc, int offset) {
        try {
           for (int n = offset-1; n >= 0; n--) {
             char c = doc.getChar(n);
             if (!Character.isJavaIdentifierPart(c))
               return doc.get(n + 1, offset-n-1);
           }
        } catch (BadLocationException e) {
           // ... log the exception ...
        }
        return "";
     }
	private String lastIndent(IDocument doc, int offset) {
        try {
           int start = offset-1; 
           while (start >= 0 && doc.getChar(start)!= '\n') start--;
           int end = start;
           while (end < offset && Character.isSpaceChar(doc.getChar(end))) end++;
           return doc.get(start+1, end-start-1);
        } catch (BadLocationException e) {
           e.printStackTrace();
        }
        return "";
     }

	@Override
	public IContextInformation[] computeContextInformation(ITextViewer viewer,
			int offset) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public char[] getCompletionProposalAutoActivationCharacters() {
		// TODO Auto-generated method stub
		return PROPOSAL_ACTIVATION_CHARS;
	}

	@Override
	public char[] getContextInformationAutoActivationCharacters() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IContextInformationValidator getContextInformationValidator() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getErrorMessage() {
		// TODO Auto-generated method stub
		return "auto complete error";
	}

}
