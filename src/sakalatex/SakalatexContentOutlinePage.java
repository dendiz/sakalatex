package sakalatex;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.texteditor.IDocumentProvider;
import org.eclipse.ui.texteditor.ITextEditor;
import org.eclipse.ui.views.contentoutline.ContentOutlinePage;



import com.swe573.sakalatex.Activator;

public class SakalatexContentOutlinePage extends ContentOutlinePage {
	private TreeViewer viewer;
	private TreeParent invisibleRoot;

	class TreeObject implements IAdaptable {

		private String name;
		private TreeParent parent;
		private IResource resouce;

		public TreeObject(String name) {
			this.name = name;
		}

		public String getName() {
			return name;
		}

		public void setParent(TreeParent parent) {
			this.parent = parent;
		}

		public TreeParent getParent() {
			return parent;
		}

		public String toString() {
			return getName();
		}

		public Object getAdapter(Class key) {
			return null;
		}

		protected IResource getResouce() {
			return resouce;
		}

		protected void setResouce(IResource resouce) {
			this.resouce = resouce;
		}
	}

	class TreeParent extends TreeObject {
		private ArrayList children;

		public TreeParent(String name) {
			super(name);
			children = new ArrayList();
		}

		public void addChild(TreeObject child) {
			children.add(child);
			child.setParent(this);
		}

		public void removeChild(TreeObject child) {
			children.remove(child);
			child.setParent(null);
		}

		public TreeObject[] getChildren() {
			return (TreeObject[]) children.toArray(new TreeObject[children.size()]);
		}

		public boolean hasChildren() {
			return children.size() > 0;
		}

	}

	class ViewContentProvider implements ITreeContentProvider {

		public void inputChanged(Viewer v, Object oldInput, Object newInput) {
			initialize();
		}

		public void dispose() {
		}

		public Object[] getElements(Object parent) {
			//if (parent.equals(getViewSite())) {
				if (invisibleRoot == null)
					initialize();

				return getChildren(invisibleRoot);
			//}

			//return getChildren(parent);
		}

		public Object getParent(Object child) {
			if (child instanceof TreeObject) {
				return ((TreeObject) child).getParent();
			}

			return null;
		}

		public Object[] getChildren(Object parent) {

			if (parent instanceof TreeParent) {
				return ((TreeParent) parent).getChildren();
			}

			return new Object[0];
		}

		public boolean hasChildren(Object parent) {
			if (parent instanceof TreeParent)
				return ((TreeParent) parent).hasChildren();
			return false;
		}

	}

	class ViewLabelProvider extends LabelProvider {
		public String getText(Object obj) {
			return obj.toString();
		}

		public Image getImage(Object obj) {
			// String imageKey = ISharedImages.IMG_OBJ_ELEMENT;
			//
			// if (obj instanceof TreeParent)
			// imageKey = ISharedImages.IMG_OBJ_FOLDER;
			// return
			// PlatformUI.getWorkbench().getSharedImages().getImage(imageKey);
			return null;
		}

	}

	public void initialize() {
		TreeParent root = new TreeParent("Sakalatex Document Outline");
		try {
			IEditorPart ep= Activator.getCurrentView().getActivePage().getActiveEditor();
			ITextEditor editor = (ITextEditor)ep;
			IDocumentProvider dp = editor.getDocumentProvider();
			IDocument doc = dp.getDocument(editor.getEditorInput());
			String content= doc.get();
			String[] stra = content.split("\\n");
			
			 Pattern SECTION_RE = Pattern.compile("\\\\section\\{([a-zA-Z]*)\\}");
			 
			for (int i = 0; i < stra.length; i++) {
				Matcher matcher = SECTION_RE.matcher(stra[i]);
				if (matcher.find()) {
					TreeObject obj = new TreeObject(matcher.group(1));
					root.addChild(obj);
					
				}

			}
		} catch (Exception e) {
			// log exception
		}
		invisibleRoot = new TreeParent("");
		invisibleRoot.addChild(root);
	}

	public SakalatexContentOutlinePage() {
	}

	public void createControl(Composite parent) {
		viewer = new TreeViewer(parent, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL);
		ViewContentProvider vcp = new ViewContentProvider();
		viewer.setContentProvider(vcp);
		viewer.setLabelProvider(new ViewLabelProvider());

		viewer.setInput(vcp.getChildren(invisibleRoot));
		
		hookContextMenu();
		hookDoubleCLickAction();
	}

	private void hookDoubleCLickAction() {
		viewer.addDoubleClickListener(new IDoubleClickListener() {
			public void doubleClick(DoubleClickEvent event) {
				ISelection selection = event.getSelection();
				Object obj = ((IStructuredSelection) selection)
						.getFirstElement();
				if (!(obj instanceof TreeObject)) {
					return;
				} else {
					return;

				}
			};
		});
	}

	private void hookContextMenu() {
		// MenuManager menuMgr = new MenuManager("#PopupMenu");
		// Menu menu = menuMgr.createContextMenu(viewer.getControl());
		// viewer.getControl().setMenu(menu);
		// Action refresh =new Action() {
		// public void run() {
		// initialize();
		// viewer.refresh();
		// }
		// };
		// refresh.setText("Refresh");
		// menuMgr.add(refresh);
	}

	public void setFocus() {
		viewer.getControl().setFocus();
	}

	
	public void update(){
		initialize();
		
	}
}
