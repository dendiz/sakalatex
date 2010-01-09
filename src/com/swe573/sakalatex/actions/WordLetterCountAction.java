package com.swe573.sakalatex.actions;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import org.eclipse.ui.texteditor.AbstractTextEditor;
import org.eclipse.ui.texteditor.ITextEditor;


public class WordLetterCountAction implements IWorkbenchWindowActionDelegate {
	private IWorkbenchWindow window;
	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

	@Override
	public void init(IWorkbenchWindow window) {
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
		
		int wc = 0;
		int wcInComments = 0;
		int lc = 0;
		int lcInComments = 0;
		boolean inCommentLine = false;
		
		try {
			String content = doc.get(0, doc.getLength());
			
			for (int i = 0; i <	doc.getNumberOfLines();i++){
				try {
					int offset = 0, length = 0;
					length = doc.getLineLength(i);
					offset = doc.getLineOffset(i);
					String line = content.substring(offset,offset + length).trim();
					if(line.isEmpty())continue;
					if(line.startsWith("%")){
						inCommentLine = true;
						line = line.replaceAll("%", "");
					}
					String[] s =line.split(" ");
					if(inCommentLine){
						wcInComments = s.length;
					}else{
					wc +=s.length;
					}
					for(int j = 0;j<s.length;j++){
						s[j] = s[j].trim();
						if(!s[j].isEmpty()){
							if(inCommentLine)
								lcInComments = lcInComments + s[j].length();
							else
								lc = lc + s[j].length();
						}
						else{
							if(inCommentLine){
								wcInComments--;
							}else{
							wc--;
							}
						}
					}
					inCommentLine =false;
				} catch (BadLocationException e) {
					e.printStackTrace();
				}
				
			}
		
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
		
		MessageDialog.openInformation(
				window.getShell(),
				"Word/Letter Count",
				"Word Count: " + wc + "\n Letter Count:" + lc +"\n\n" +
				"Including Comments \n" +
				"Word Count: " + (wc + wcInComments) + "\n Letter Count:" + (lc + lcInComments) +"\n\n" 
				);
		
	}

	@Override
	public void selectionChanged(IAction action, ISelection selection) {
		// TODO Auto-generated method stub

	}

}
