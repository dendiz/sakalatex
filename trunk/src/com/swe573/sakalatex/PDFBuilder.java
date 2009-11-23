package com.swe573.sakalatex;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Map;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.ui.console.ConsolePlugin;
import org.eclipse.ui.console.IConsole;
import org.eclipse.ui.console.IConsoleManager;
import org.eclipse.ui.console.MessageConsole;

/**
 * The PDF Builder class converts the sakala.tex main tex file
 * in to a pdf file.
 * Windows uses pdflatex.exe and linux variants use pdflatex
 * Both OS need to have the executable in the path to execute them.
 * @author dendiz
 *
 */
public class PDFBuilder extends IncrementalProjectBuilder {
	
	public PDFBuilder() {
		System.out.println("creating pdf builder");
	}
	
	/**
	 * Get a message console to redirect the pdflatex executable output
	 * 
	 * @param name name of the console.
	 * @return MessageConsole instance
	 */
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

   /**
    * convert the source tex file into a pdf file using pdflatex
    */
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
		pdflatex = Activator.getDefault().getPreferenceStore().getString("prefPDFLatexPath");
		System.out.println("pdflatex executable:" + pdflatex);
		if (pdflatex == null || pdflatex.equals("")) {
			pdflatex = name.indexOf("windows") >= 0 ? "pdflatex.exe" : "pdflatex";
		}
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
}
