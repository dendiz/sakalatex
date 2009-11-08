package com.swe573.sakalatex;

import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
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
	protected SakalatexWizardPage(String pageName) {
		super(pageName);
		
		// TODO Auto-generated constructor stub
	}

	/**
	 * create the project name input and label.
	 * @param parent the container for the ui widgets
	 */
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
        label.setText("project name");
        label.setToolTipText("project name");
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
		setControl(composite);
	}
   private void validateProjectName(String text) {
   }
}
