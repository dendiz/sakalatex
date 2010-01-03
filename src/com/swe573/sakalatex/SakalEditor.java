package com.swe573.sakalatex;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.editors.text.TextEditor;
import org.eclipse.ui.texteditor.AbstractDecoratedTextEditorPreferenceConstants;

/**
 * basic text editor to edit the source docs.
 * .tex extension is associated with this editor.
 * @author dendiz
 *
 */
public class SakalEditor extends TextEditor {

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
}
