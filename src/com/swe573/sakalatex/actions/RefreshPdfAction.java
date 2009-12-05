package com.swe573.sakalatex.actions;

import org.eclipse.core.resources.IFile;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IEditorDescriptor;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.ui.texteditor.AbstractTextEditor;
import org.eclipse.ui.texteditor.ITextEditor;

import com.swe573.sakalatex.Activator;

public class RefreshPdfAction implements IWorkbenchWindowActionDelegate {

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
		String filename = "sakala.pdf";

		IWorkbenchPage[] pages = window.getPages();
		IWorkbenchPage page = pages[0];
	    IFile ifile =  Activator.getCurrentProject().getFile(filename);
		IEditorDescriptor desc = PlatformUI.getWorkbench().getEditorRegistry().getDefaultEditor(filename);
		try {
			IEditorPart editpart = page.openEditor(new FileEditorInput(ifile), desc.getId());
			page.closeEditor(editpart, false);
			page.openEditor(new FileEditorInput(ifile), desc.getId());
		} catch (PartInitException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void selectionChanged(IAction action, ISelection selection) {
		// TODO Auto-generated method stub
		
	}


	

}
