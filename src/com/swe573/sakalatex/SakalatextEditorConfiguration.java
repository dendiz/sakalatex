package com.swe573.sakalatex;

import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.contentassist.ContentAssistant;
import org.eclipse.jface.text.contentassist.IContentAssistProcessor;
import org.eclipse.jface.text.contentassist.IContentAssistant;
import org.eclipse.jface.text.presentation.IPresentationReconciler;
import org.eclipse.jface.text.presentation.PresentationReconciler;
import org.eclipse.jface.text.rules.DefaultDamagerRepairer;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.jface.text.source.SourceViewerConfiguration;

public class SakalatextEditorConfiguration extends SourceViewerConfiguration {

	SakalEditor editor = null;
	public SakalatextEditorConfiguration(SakalEditor ed) {
		this.editor = ed;
	}
	@Override
	public IPresentationReconciler getPresentationReconciler(
			ISourceViewer sourceViewer) {
		// TODO Auto-generated method stub
		PresentationReconciler pr = new PresentationReconciler();
		pr.setDocumentPartitioning(getConfiguredDocumentPartitioning(sourceViewer));
		
		DefaultDamagerRepairer ddr = new DefaultDamagerRepairer(new SakalTokenScanner());
        pr.setRepairer(ddr, IDocument.DEFAULT_CONTENT_TYPE);
        pr.setDamager(ddr, IDocument.DEFAULT_CONTENT_TYPE);
        return pr;
	}
	
	
	
	

}
