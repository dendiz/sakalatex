package com.swe573.sakalatex.actions;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
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

import com.swe573.sakalatex.Activator;
import com.swe573.sakalatex.PDFBuilder;

public class PartialPreviewAction implements IWorkbenchWindowActionDelegate {

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
		ISelection selection  = editor.getSelectionProvider().getSelection();
		if(!(selection instanceof ITextSelection)) {
			System.out.println("selection is not text");
			return;
		}
		ITextSelection textselection = (ITextSelection) selection;
		if (textselection.isEmpty()) System.out.println("selection was empty, probably a one liner");
		String s = textselection.getText();
		if(s.indexOf("\\begin{")== -1){
			s = "\\begin{document}\n" +s;
		}
		if(s.indexOf("\\documentclass{") == -1)
		{
			s = "\\documentclass{article}\n" + s;
		}
		if(s.indexOf("\\end{")== -1){
			s = s + "\n\\end{document}" ;
		}

		IProject project =  Activator.getCurrentProject();
		IPath pt1 = project.getFullPath();
		IPath pt2 = ResourcesPlugin.getWorkspace().getRoot().getLocation();
	
		
		String fullpath = pt2.toOSString() + pt1.addTrailingSeparator().toOSString();
		String filename = editor.getEditorInput().getName();
		filename = filename.substring(0, filename.indexOf(".tex"));
		File tmp = new File(fullpath +  filename + ".partial.tex");
		try{
		 Writer output = new BufferedWriter(new FileWriter(tmp));
		      //FileWriter always assumes default encoding is OK!
		      output.write( s );
		      output.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			project.refreshLocal(IProject.DEPTH_INFINITE, null);
			Map map = new HashMap<String, String>();
			map.put("FileName", tmp.getName());
		project.build(IncrementalProjectBuilder.FULL_BUILD,"com.swe573.sakalatex.PDFBuilder",map , null);
		}catch (CoreException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void selectionChanged(IAction action, ISelection selection) {
		// TODO Auto-generated method stub

	}

}
