package com.swe573.sakalatex.outline.ex;

import org.eclipse.jface.viewers.IBaseLabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.swt.graphics.Image;

public class OutlineLabelProvider implements IBaseLabelProvider {

	
	public OutlineLabelProvider()
	{
		super();
	}

	public Image getImage(Object element)
	{
		return null;
	}

	public String getText(Object element)
	{

		return "element";
	}

	public void addListener(ILabelProviderListener listener)
	{
	}

	public void dispose()
	{
	}

	public boolean isLabelProperty(Object element, String property)
	{
		return false;
	}

	public void removeListener(ILabelProviderListener listener)
	{
	}

}
