package com.swe573.sakalatex;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.jobs.ISchedulingRule;
import org.eclipse.jface.text.IDocumentExtension;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.console.ConsolePlugin;
import org.eclipse.ui.console.IConsole;
import org.eclipse.ui.console.IConsoleManager;
import org.eclipse.ui.console.MessageConsole;
import org.eclipse.ui.part.WorkbenchPart;
import org.eclipse.ui.texteditor.ITextEditor;

public class PDFBuilder extends IncrementalProjectBuilder {
	
	public PDFBuilder() {
		System.out.println("creating pdf builder");
	}
   private MessageConsole findConsole(String name) {
	      ConsolePlugin plugin = ConsolePlugin.getDefault();
	      IConsoleManager conMan = plugin.getConsoleManager();
	      IConsole[] existing = conMan.getConsoles();
	      for (int i = 0; i < existing.length; i++)
	         if (name.equals(existing[i].getName()))
	            return (MessageConsole) existing[i];
	      //no console found, so create a new one
	      MessageConsole myConsole = new MessageConsole(name, null);
	      conMan.addConsoles(new IConsole[]{myConsole});
	      return myConsole;
	   }

	@Override
	protected IProject[] build(int kind, Map args, IProgressMonitor monitor)
			throws CoreException {
		// TODO Auto-generated method stub
		super.forgetLastBuiltState();
		MessageConsole console = findConsole("sakalatexconsole");
		console.newMessageStream().println("build in progress");
		System.out.println("build in progress");
		ArrayList<String> cmds = new ArrayList<String>();
		String name = System.getProperty("os.name").toLowerCase();
		String pdflatex = null;
		pdflatex = name.indexOf("windows") >= 0 ? "pdflatex.exe" : "pdflatex";
		cmds.add(pdflatex);
		cmds.add("-interaction=nonstopmode");
		ResourcesPlugin.getWorkspace().getRoot();
		IPath pt2 = ResourcesPlugin.getWorkspace().getRoot().getLocation();
		IPath proj = super.getProject().getFullPath();
		String fullpath = pt2.toOSString() + proj.addTrailingSeparator().toOSString();
		cmds.add(fullpath + "sakala.tex");
		
		ProcessBuilder launcher = new ProcessBuilder(cmds);
		//Map<String, String> environment = launcher.environment();
		launcher.redirectErrorStream(true);
		launcher.directory(new File(fullpath));
		Process p;
		try {
			p = launcher.start();
			BufferedReader output = new BufferedReader(new InputStreamReader(p.getInputStream()));
		    String line;
		    while ((line = output.readLine()) != null)
		      console.newMessageStream().println(line);

		    // The process should be done now, but wait to be sure.
//		    p.waitFor();
		    System.out.println("programm terminated");

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		super.getProject().refreshLocal(IProject.DEPTH_INFINITE, monitor);
		
		
		
		return null;
	}
	@Override
	public ISchedulingRule getRule() {
		// TODO Auto-generated method stub
		return super.getRule();
	}
	@Override
	public void setInitializationData(IConfigurationElement config,
			String propertyName, Object data) throws CoreException {
		// TODO Auto-generated method stub
		System.out.println("pdfbuilder set init data");
		super.setInitializationData(config, propertyName, data);
	}
	

}
