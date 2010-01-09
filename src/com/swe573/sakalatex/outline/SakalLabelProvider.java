package com.swe573.sakalatex.outline;

import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;


public class SakalLabelProvider extends LabelProvider {
	public Image getImage(Object element) {
		return null;
	}

	public String getText(Object element) {
		return ((SakalOutlineNode)element).getName();
	}
    
    public boolean isLabelProperty(Object element, String property) {
        return false;
    }
}
