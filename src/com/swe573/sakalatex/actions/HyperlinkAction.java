package com.swe573.sakalatex.actions;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import org.eclipse.ui.texteditor.AbstractTextEditor;
import org.eclipse.ui.texteditor.ITextEditor;

public class HyperlinkAction implements IWorkbenchWindowActionDelegate {

	private IWorkbenchWindow window;

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

	@Override
	public void init(IWorkbenchWindow window) {
		// TODO Auto-generated method stub
		this.window = window;

	}

	@Override
	public void run(IAction action) {
		IWorkbenchPage[] pages = window.getPages();
		IWorkbenchPage page = pages[0];
		IEditorPart part = page.getActiveEditor();
		if (! (part instanceof AbstractTextEditor)) {
			System.out.println("active workbench part was not an instance of text editor.");
			return;
		}
		ITextEditor editor = (ITextEditor) part;
		IDocument doc = editor.getDocumentProvider().getDocument(editor.getEditorInput());
		ISelection selection  = editor.getSelectionProvider().getSelection();
		if(!(selection instanceof ITextSelection)) {
			System.out.println("selection is not text");
			return;
		}
		ITextSelection textselection = (ITextSelection) selection;
		if (textselection.isEmpty()) System.out.println("selection was empty, probably a one liner");
		 try {
			 doc.replace(textselection.getOffset() +textselection.getLength(),0, "}");
			 doc.replace(textselection.getOffset(), 0, "\\href{}{");
		 } catch (BadLocationException e) {
			 e.printStackTrace();
		 }
	}

	@Override
	public void selectionChanged(IAction action, ISelection selection) {
		// TODO Auto-generated method stub
		
	}
}
