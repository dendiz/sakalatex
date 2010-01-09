/*
 */
package com.swe573.sakalatex.outline;

import java.util.List;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;


public class SakalContentProvider implements ITreeContentProvider {

    
	private List rootElements = null;	
	
	public Object[] getChildren(Object parentElement) {
		SakalOutlineNode node = (SakalOutlineNode) parentElement;
		List children = node.getChildren();
		if (children != null && children.size() != 0) {
			return children.toArray();
		} else {
			return null;
		}
	}

	public Object getParent(Object element) {
		return ((SakalOutlineNode)element).getParent(); 
	}

	public boolean hasChildren(Object element) {
		
		SakalOutlineNode node = (SakalOutlineNode) element;
		List children = node.getChildren();
		
		if (children != null && children.size() != 0) {
			return true;
		} else {
			return false;
		}
	}

	public Object[] getElements(Object inputElement) {
		return this.rootElements.toArray();
	}

	public void dispose() {
	    this.rootElements = null;
    }

	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		this.rootElements = (List)newInput;
		
	}
}
