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

/**
 * Our sample action implements workbench action delegate.
 * The action proxy will be created by the workbench and
 * shown in the UI. When the user tries to use the action,
 * this delegate will be created and execution will be 
 * delegated to it.
 * @see IWorkbenchWindowActionDelegate
 */
public class CommentOutAction implements IWorkbenchWindowActionDelegate {
	private IWorkbenchWindow window;
	/**
	 * The constructor.
	 */
	public CommentOutAction() {
	}

	/**
	 * The action has been activated. The argument of the
	 * method represents the 'real' action sitting
	 * in the workbench UI.
	 * @see IWorkbenchWindowActionDelegate#run
	 */
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
		int start = textselection.getStartLine();
		int end = textselection.getEndLine();
		try {
			int offset = doc.getLineOffset(start);
			int delta = Math.abs(end - start);
			for(int i = 0;i<=delta;i++) {
				offset = doc.getLineOffset(start+i);
				doc.replace(offset, 0, "%");
			}
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Selection in the workbench has been changed. We 
	 * can change the state of the 'real' action here
	 * if we want, but this can only happen after 
	 * the delegate has been created.
	 * @see IWorkbenchWindowActionDelegate#selectionChanged
	 */
	public void selectionChanged(IAction action, ISelection selection) {
	}

	/**
	 * We can use this method to dispose of any system
	 * resources we previously allocated.
	 * @see IWorkbenchWindowActionDelegate#dispose
	 */
	public void dispose() {
	}

	/**
	 * We will cache window object in order to
	 * be able to provide parent shell for the message dialog.
	 * @see IWorkbenchWindowActionDelegate#init
	 */
	public void init(IWorkbenchWindow window) {
		this.window = window;
	}
}