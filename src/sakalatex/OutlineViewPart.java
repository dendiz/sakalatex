package sakalatex;

import java.util.ArrayList;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IAdaptable;
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
import org.eclipse.ui.part.ViewPart;

public class OutlineViewPart extends ViewPart {

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
			return (TreeObject[]) children.toArray(new TreeObject[children
					.size()]);
		}

		public boolean hasChildren() {
			return children.size() > 0;
		}

	}

	class ViewContentProvider implements ITreeContentProvider {

		public void inputChanged(Viewer v, Object oldInput, Object newInput) {
		}

		public void dispose() {
		}

		public Object[] getElements(Object parent) {
			if (parent.equals(getViewSite())) {
				if (invisibleRoot == null)
					initialize();

				return getChildren(invisibleRoot);
			}

			return getChildren(parent);
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
		TreeParent root = new TreeParent("WorkSpace Property Files");
		try {

			for (int i = 0; i < 5; i++) {

				TreeObject obj = new TreeObject("subsection");

				root.addChild(obj);

			}
		} catch (Exception e) {
			// log exception
		}
		invisibleRoot = new TreeParent("");
		invisibleRoot.addChild(root);
	}

	public OutlineViewPart() {
	}

	public void createPartControl(Composite parent) {
		viewer = new TreeViewer(parent, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL);
		viewer.setContentProvider(new ViewContentProvider());
		viewer.setLabelProvider(new ViewLabelProvider());
		viewer.setInput(getViewSite());
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

}
