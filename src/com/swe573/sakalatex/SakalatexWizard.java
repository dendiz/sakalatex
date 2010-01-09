package com.swe573.sakalatex;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExecutableExtension;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;

/**
 * New com.swe573.sakalatex.outline.ex project creation wizard dialogs
 * Fired after File->new->projects->others->SakalatexProject
 *
 */
public class SakalatexWizard extends Wizard implements INewWizard, IExecutableExtension {

	/**
	 * set the wizard title
	 */
	public SakalatexWizard() {
		super();
		setDialogSettings(Activator.getDefault().getDialogSettings());
		setWindowTitle("Sakalatex Project creation");		
	}

	/**
	 * add pages of the wizard. Current pages are:
	 * 1. project name page.
	 */
	public void addPages() {
		super.addPages();
		addPage(new SakalatexWizardPage("first page"));
	}
	
	/**
	 * Actions to perform after the user presses the finish button.
	 * @see ProjectCreationOperation
	 */
	@Override
	public boolean performFinish() {
		// TODO Auto-generated method stub
		ProjectCreationOperation runnable = new ProjectCreationOperation();
		boolean res = true;
		try {
			getContainer().run(true, true, runnable);
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			res = false;
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			res = false;
			e.printStackTrace();
		}
		
		return res;
	}

	@Override
	public void init(IWorkbench workbench, IStructuredSelection selection) {
		// TODO Auto-generated method stub
		

	}

	@Override
	public void setInitializationData(IConfigurationElement config,
			String propertyName, Object data) throws CoreException {
		// TODO Auto-generated method stub
		
	}

}
