package com.swe573.sakalatex;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.editors.text.TextEditor;
import org.eclipse.ui.texteditor.AbstractDecoratedTextEditorPreferenceConstants;
import org.eclipse.ui.views.contentoutline.IContentOutlinePage;

import sakalatex.OutlineViewPart;
import sakalatex.SakalatexContentOutlinePage;



/**
 * basic text editor to edit the source docs.
 * .tex extension is associated with this editor.
 * @author dendiz
 *
 */
public class SakalEditor extends TextEditor {

	SakalatexContentOutlinePage outlinePage = null;
	public SakalEditor() {
		super();
		setSourceViewerConfiguration(new SakalatextEditorConfiguration(this));
		this.getPreferenceStore().setValue(AbstractDecoratedTextEditorPreferenceConstants.EDITOR_LINE_NUMBER_RULER, true);
		
	}
	public IWorkbenchPage myPage(){
		return getSite().getPage();
	}

	public void dispose() {
		super.dispose();
	}
	
	@Override
	public void doSave(IProgressMonitor monitor ){
		super.doSave(monitor);
		outlinePage.update();
		
	}
	
	public Object getAdapter(Class required){
		if(IContentOutlinePage.class.equals(required)){
			if(outlinePage == null){
				outlinePage =new SakalatexContentOutlinePage(); 
				return outlinePage;
			}
		}
		return super.getAdapter(required);
		
	}
}
