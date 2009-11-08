package com.swe573.sakalatex;

import org.eclipse.core.resources.ICommand;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IProjectNature;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.runtime.CoreException;

public class SakalatexProjectNature implements IProjectNature {

	private IProject project;
	@Override
	public void configure() throws CoreException {
		System.out.println("nature config");
		addBuilder("com.swe573.sakalatex.PDFBuilder");
	}
	   private boolean hasBuilder(ICommand[] commands, String id) {
	        for (int i = 0; i < commands.length; i++) {
	            if (commands[i].getBuilderName().equals(id)) {
	                return true;
	            }
	        }
	        return false;
	    }
	private void addBuilder(String id) throws CoreException {

        IProjectDescription desc = project.getDescription();
        ICommand[] commands = desc.getBuildSpec();

        if (!hasBuilder(commands, id)) {

            ICommand command = desc.newCommand();
//            command.setBuilding(IncrementalProjectBuilder.AUTO_BUILD, false);
            command.setBuilderName(id);
            ICommand[] newCommands = new ICommand[commands.length + 1];

            System.arraycopy(commands, 0, newCommands, 1, commands.length);
            newCommands[0] = command;
            desc.setBuildSpec(newCommands);

            project.setDescription(desc, null);
        }
    }
	@Override
	public void deconfigure() throws CoreException {
		// TODO Auto-generated method stub

	}

	@Override
	public IProject getProject() {
		// TODO Auto-generated method stub
		return project;
	}

	@Override
	public void setProject(IProject project) {
		// TODO Auto-generated method stub
		this.project = project;

	}

}