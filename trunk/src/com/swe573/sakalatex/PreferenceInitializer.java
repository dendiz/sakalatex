package com.swe573.sakalatex;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.PreferenceConverter;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Display;

import com.swe573.sakalatex.Activator;

/**
 * Class used to initialize default preference values.
 */
public class PreferenceInitializer extends AbstractPreferenceInitializer {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer#initializeDefaultPreferences()
	 */
	public void initializeDefaultPreferences() {
		IPreferenceStore store = Activator.getDefault().getPreferenceStore();
	       PreferenceConverter.setDefault(store, SakalatexColorPreferencePage.STRING, SakalatexColorPreferencePage.STRINGCOLOR);
	        PreferenceConverter.setDefault(store, SakalatexColorPreferencePage.COMMAND, SakalatexColorPreferencePage.COMMANDCOLOR);
	        PreferenceConverter.setDefault(store, SakalatexColorPreferencePage.BRACKETS, SakalatexColorPreferencePage.BRACKETSCOLOR);
	        PreferenceConverter.setDefault(store, SakalatexColorPreferencePage.SQUARE, SakalatexColorPreferencePage.SQUARECOLOR);
	        PreferenceConverter.setDefault(store, SakalatexColorPreferencePage.NUMBER, SakalatexColorPreferencePage.NUMBERCOLOR);
	        PreferenceConverter.setDefault(store, SakalatexColorPreferencePage.COMMENT, SakalatexColorPreferencePage.COMMENTCOLOR);

		
	}

}
