package com.swe573.sakalatex;

import java.util.ArrayList;

import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.jface.text.contentassist.CompletionProposal;
import org.eclipse.jface.text.contentassist.ContextInformationValidator;
import org.eclipse.jface.text.contentassist.ICompletionProposal;
import org.eclipse.jface.text.contentassist.IContentAssistProcessor;
import org.eclipse.jface.text.contentassist.IContextInformation;
import org.eclipse.jface.text.contentassist.IContextInformationValidator;


public class SakalCompletionProcessor implements IContentAssistProcessor {
    private ICompletionProposal[] NO_COMPLETIONS = new ICompletionProposal[0];

    private int resolveCompletionStart(String doc, int offset) {
        while (offset > 0) {
            if (Character.isWhitespace(doc.charAt(offset))
                    || doc.charAt(offset) == '}' || doc.charAt(offset) == '{' || doc.charAt(offset) == '\\')
                break;
            offset--;
        }
        return offset;
    }
	@SuppressWarnings("unchecked")
	@Override
	public ICompletionProposal[] computeCompletionProposals(ITextViewer viewer,
			int offset) {
        IDocument doc = viewer.getDocument();
        String[] completions = new String[] {
        		"alpha","beta", "gamma","delta","epsilon","varepsi","zeta", "eta","theta","varthet","iota", "kappa","lambda","mu", "nu", "xi", "pi", "varpi","rho",
        		"varrho","sigma","varsigm","tau", "upsilon","phi","varphi","chi","psi","omega"
        };
        try {
        	ArrayList result = new ArrayList();
        	int lineStartOffset = doc.getLineOffset(doc.getLineOfOffset(offset));
            String lineStart = doc.get(lineStartOffset, offset - lineStartOffset);
            int seqStartIdx = resolveCompletionStart(lineStart, lineStart.length() - 1);
            String seqStart = lineStart.substring(seqStartIdx);
            String replacement = seqStart.substring(1);
            
            for (String c : completions) {
            	if (c.startsWith(replacement))
            		result.add(new CompletionProposal(c, offset-replacement.length(), replacement.length(), c.length()));
            }
            return (ICompletionProposal[]) result.toArray(new ICompletionProposal[result.size()]);
         } catch (Exception e) {
            return NO_COMPLETIONS;
         }

	}


	@Override
	public IContextInformation[] computeContextInformation(ITextViewer viewer,
			int offset) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public char[] getCompletionProposalAutoActivationCharacters() {
		return new char[] { '\\' };
	}

	@Override
	public char[] getContextInformationAutoActivationCharacters() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IContextInformationValidator getContextInformationValidator() {
		// TODO Auto-generated method stub
        return new ContextInformationValidator(this);
	}

	@Override
	public String getErrorMessage() {
		// TODO Auto-generated method stub
		return "auto complete error";
	}

}
