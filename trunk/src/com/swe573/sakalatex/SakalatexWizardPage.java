package com.swe573.sakalatex;

import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Enumeration;

import javax.swing.JComboBox;

import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
/**
 * Layout of the project name page. Gui stuff.
 * @author dendiz
 *
 */
public class SakalatexWizardPage extends WizardPage {
	private Text projectNameField;
	private Text templateNameField;
	protected SakalatexWizardPage(String pageName) {
		super(pageName);
		
		// TODO Auto-generated constructor stub
	}

	/**
	 * create the project name input and label.
	 * @param parent the container for the ui widgets
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void createControl(Composite parent) {
		// TODO Auto-generated method stub
		Composite composite = new Composite(parent, SWT.NONE);


		GridLayout gd = new GridLayout();
		gd.numColumns = 2;

		composite.setLayout(gd);
		composite.setLayoutData(new GridData(GridData.FILL_BOTH | GridData.GRAB_HORIZONTAL | GridData.GRAB_VERTICAL));
	      // add label
        Label label = new Label(composite, SWT.LEFT);
        label.setText("Project name");
        label.setToolTipText("Project name");
        label.setLayoutData(new GridData());

        // add text field
        projectNameField = new Text(composite, SWT.SINGLE | SWT.BORDER);
        projectNameField.setText("sakalatexdefault");
        projectNameField.setToolTipText("project");
        projectNameField.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        projectNameField.addModifyListener(new ModifyListener() {
            public void modifyText(ModifyEvent e) {
                Activator.projectName = projectNameField.getText();
            	if (!projectNameField.isDisposed()) {
                    validateProjectName(projectNameField.getText());
                }
            }
        });
        
        // add label
        Label labelTemplate = new Label(composite, SWT.LEFT);
        labelTemplate.setText("Template");
        labelTemplate.setToolTipText("Template");
        labelTemplate.setLayoutData(new GridData());

        // add text field
        final Combo templateCombo = new Combo(composite, SWT.SINGLE | SWT.BORDER);
        ArrayList list = new ArrayList<String>();
     // The list of files can also be retrieved as File objects
 
        Enumeration templateEnum = Activator.getDefault().getBundle().getEntryPaths("templates/");

        while (templateEnum.hasMoreElements()) {
            String path = (String) templateEnum.nextElement();
            if (path.endsWith(".tex_template")) {
                list.add(path.substring(path.lastIndexOf('/')+1, path.lastIndexOf('.')));
            }
        }
    
	
		templateCombo.add("Blank");
        
		for(int i = 0;i<list.size();i++){
        	templateCombo.add(list.get(i).toString());
        }
        
        
        templateCombo.setToolTipText("Templates");
        templateCombo.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        templateCombo.addModifyListener(new ModifyListener() {
            public void modifyText(ModifyEvent e) {
                Activator.templateName = templateCombo.getText();
            	if (!templateCombo.isDisposed()) {
                    validateProjectName(templateCombo.getText());
                }
            }
        });
        templateCombo.select(0);
		setControl(composite);
	}
   private void validateProjectName(String text) {
   }
}

