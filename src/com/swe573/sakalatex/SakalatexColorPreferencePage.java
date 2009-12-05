package com.swe573.sakalatex;

import org.eclipse.jface.preference.ColorFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.FileFieldEditor;
import org.eclipse.jface.preference.PreferenceConverter;
import org.eclipse.jface.preference.StringFieldEditor;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

public class SakalatexColorPreferencePage extends FieldEditorPreferencePage implements
		IWorkbenchPreferencePage {

	public static final String STRING     = "stringColor";
	public static final String COMMAND    = "cmd.TexColor";
	public static final String BRACKETS = "bracketColor";
    public static final String SQUARE = "squareColor";
	public static final String NUMBER = "numberColor";
	public static final String COMMENT  = "commentColor";

	
	public static final RGB STRINGCOLOR     = new RGB(0,0,255);
	public static final RGB COMMANDCOLOR    = new RGB(255,0,255);
	public static final RGB BRACKETSCOLOR = new RGB(0,255,255);
    public static final RGB SQUARECOLOR =  new RGB( 0,0,255);
	public static final RGB NUMBERCOLOR = new RGB(0,0,255);
	public static final RGB COMMENTCOLOR  = new RGB( 0,0,255);
	
	
    
    
	public SakalatexColorPreferencePage() {
		super(GRID);
		setPreferenceStore(Activator.getDefault().getPreferenceStore());
		setDescription("Sakalatex preferences");
	}
	
	/**
	 * Creates the field editors. Field editors are abstractions of
	 * the common GUI blocks needed to manipulate various types
	 * of preferences. Each field editor knows how to save and
	 * restore itself.
	 */
	public void createFieldEditors() {
		addField(new ColorFieldEditor(STRING, "&String", getFieldEditorParent()));
		addField(new ColorFieldEditor(BRACKETS, "&Bracket", getFieldEditorParent()));
		addField(new ColorFieldEditor(SQUARE, "S&quare", getFieldEditorParent()));
		addField(new ColorFieldEditor(NUMBER, "&Number", getFieldEditorParent()));
		addField(new ColorFieldEditor(COMMENT, "&Comment", getFieldEditorParent()));
		addField(new ColorFieldEditor(COMMAND, "C&ommand", getFieldEditorParent()));
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.IWorkbenchPreferencePage#init(org.eclipse.ui.IWorkbench)
	 */
	public void init(IWorkbench workbench) {
	}
	
	
	public static Color getColor(String rgbColor) {
		return new Color(Display.getCurrent(),
                    PreferenceConverter.getColor(Activator.getDefault().getPreferenceStore(), rgbColor));
	}


}
