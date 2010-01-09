package com.swe573.sakalatex;

import java.io.FileInputStream;
import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jface.operation.IRunnableWithProgress;


/**
 * Worker class to create a new com.swe573.sakalatex.outline.ex project. This class is called 
 * by the new Project wizard, and creates the default directories in the current workspace for the
 * project and the project main document sakala.tex
 */
public class ProjectCreationOperation implements IRunnableWithProgress {
	/**
	 * Run the project creation steps:
	 * 1. create a .project descriptor with the com.swe573.sakalatex.outline.ex builder and nature
	 * 2. create a working directory inside the workspace for the project with the projects name
	 * 
	 * @param monitor user interface progress bar
	 */
	@Override
	public void run(IProgressMonitor monitor) throws InvocationTargetException,
			InterruptedException {
			
		if (monitor == null) monitor = new NullProgressMonitor();
		try {
			
			monitor.beginTask("projectcreationTask", 3);
			IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
			String name = Activator.projectName;
			IProject project = root.getProject(name);
			monitor.worked(1);
			IProjectDescription desc = project.getWorkspace().newProjectDescription(project.getName());
			//IPath projectpath = new Path(project.getWorkspace().getRoot().getLocation().addTrailingSeparator().toOSString() + "com.swe573.sakalatex.outline.ex");
			
			desc.setName(project.getName());
			project.create(desc, monitor);
			monitor.worked(1);
			project.open(monitor);
			desc.setNatureIds(new String[] {SakalatexProjectNature.class.getName()+"Id"});
			project.setDescription(desc, monitor);
			monitor.worked(1);
			IPath pt2 = ResourcesPlugin.getWorkspace().getRoot().getLocation();
			IPath proj = project.getFullPath();
			String fullpath = pt2.toOSString() + proj.addTrailingSeparator().toOSString();
			
			
			// The list of files can also be retrieved as File objects
			String filePath = Activator.getDefault().getBundle().getLocation();
			filePath = filePath.substring(16)+"templates";
			String fullpath2 = filePath +"/"+ Activator.templateName+".tex_template";
			String template = "";
			if(Activator.templateName.equals("Blank")){
				template = "\\documentclass{article}\n\\begin{document}\n\n\\section{Title}\n\n\\end{document}";
			}else{
			 template = Activator.readStream(new FileInputStream(fullpath2));
			}
			Activator.writeFile(fullpath + "sakala.tex", template);
	        
	        
			project.refreshLocal(IProject.DEPTH_INFINITE, monitor);
			monitor.done();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
	}

}
