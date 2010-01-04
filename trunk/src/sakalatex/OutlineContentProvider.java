package sakalatex;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.ui.texteditor.IDocumentProvider;
public class OutlineContentProvider implements ITreeContentProvider
{


	public OutlineContentProvider(IDocumentProvider documentProvider) {
		// TODO Auto-generated constructor stub
		super();
	}

	@Override
	public Object[] getChildren(Object arg0) {
		
		return new String[]{"1","2"};
	}

	@Override
	public Object getParent(Object arg0) {
		// TODO Auto-generated method stub
		return new String("0");
	}

	@Override
	public boolean hasChildren(Object arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Object[] getElements(Object arg0) {
		// TODO Auto-generated method stub
		return new String[]{"1","2"};
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void inputChanged(Viewer arg0, Object arg1, Object arg2) {
		// TODO Auto-generated method stub
		
	}


}
