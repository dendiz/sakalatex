package com.swe573.sakalatex.outline;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.Position;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.dnd.Clipboard;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.views.contentoutline.ContentOutlinePage;

import com.swe573.sakalatex.Activator;
import com.swe573.sakalatex.SakalEditor;
import com.swe573.sakalatex.SakalParser;


public class SakalOutlinePage extends ContentOutlinePage {
    
    private static final String ACTION_COPY = "copy";
    private static final String ACTION_CUT = "cut";
    private static final String ACTION_PASTE = "paste";
    private static final String ACTION_DELETE = "delete";
    private static final String ACTION_UPDATE = "update";
    private static final String ACTION_COLLAPSE = "collapse";
    private static final String ACTION_EXPAND = "expand";
    private static final String ACTION_HIDE_SEC = "hideSec";
    private static final String ACTION_HIDE_SUBSEC = "hideSubSec";
    private static final String ACTION_HIDE_SUBSUBSEC = "hideSubSubSec";
    private static final String ACTION_HIDE_PARAGRAPH = "hidePara";
    private static final String ACTION_HIDE_FLOAT = "hideFloat";

    public static final String OUTLINE_PREAMBLE = "outlinePreamble";
    public static final String OUTLINE_PART = "outlinePart";
    public static final String OUTLINE_CHAPTER = "outlineChapter";
    
    public static final String OUTLINE_SECTION = "outlineSection";
    public static final String OUTLINE_SUBSECTION = "outlineSubSection";
    public static final String OUTLINE_SUBSUBSECTION = "outlineSubSubSection";
    
    public static final String OUTLINE_PARAGRAPH = "outlineParagraph";
    public static final String OUTLINE_ENVS = "outlineEnvs";

    
    private SakalOutlineInput input;
    private SakalEditor editor;
    //private TexOutlineFilter filter;
    private Clipboard clipboard;
    private int expandLevel;
    private Map<String, IAction> outlineActions;
    //private Set outlineProperties;
    
    /**
     * The constructor.
     * 
     * @param texEditor the editor associated with this page
     */
    public SakalOutlinePage(SakalEditor texEditor) {
        super();
        this.editor = texEditor;
        expandLevel = 1;
        this.outlineActions = new HashMap<String, IAction>();
                
        
        Activator.getDefault().getPreferenceStore().addPropertyChangeListener(new  
                IPropertyChangeListener() {
            
            public void propertyChange(PropertyChangeEvent event) {
                
                String property = event.getProperty();
                if (OUTLINE_PART.equals(property) || 
                	OUTLINE_CHAPTER.equals(property) ||
					OUTLINE_SECTION.equals(property) ||
					OUTLINE_SUBSECTION.equals(property) ||
					OUTLINE_SUBSUBSECTION.equals(property) ||
					OUTLINE_PARAGRAPH.equals(property) ||
					OUTLINE_ENVS.equals(property)) {
                	//getOutlinePreferences();
                    //resetToolbarButtons();
                    TreeViewer viewer = getTreeViewer();
                    if (viewer != null) {
                        Control control= viewer.getControl();
                        if (control != null && !control.isDisposed()) {
                        	viewer.refresh();
                        }
                    }	
                }
            }	
        });    
        
    }  
    
    public void createControl(Composite parent) {
        super.createControl(parent);
        
        // create the context actions
        //createActions();
        
        // initialize the tree viewer
        TreeViewer viewer = getTreeViewer();		
        viewer.setContentProvider(new SakalContentProvider());
        viewer.setLabelProvider(new SakalLabelProvider());
        //viewer.setComparer(new TexOutlineNodeComparer());
        //this.filter = new TexOutlineFilter();
        
        // get and apply the preferences
        //this.getOutlinePreferences();
        //viewer.addFilter(filter);
        
        // set the selection listener
        viewer.addSelectionChangedListener(this);
        
        // enable copy-paste
        //initCopyPaste(viewer);
        
        // create the menu bar and the context menu
        //createToolbar();
        //resetToolbarButtons();
        //createContextMenu();
        setInput();
        
    }

    private void setInput(){
        List<SakalOutlineNode> ll = SakalParser.parse(Activator.getActiveFileFullPath());
        
        this.input = new SakalOutlineInput(ll);
        this.input.setRootNodes(ll);
        getTreeViewer().setInput(this.input.getRootNodes());
    }
    
    public void update() {
    	setInput();
    }
    
    public void update(SakalOutlineInput input) {
        this.input = input;
        
        TreeViewer viewer= getTreeViewer();
        if (viewer != null) {
            
            Control control= viewer.getControl();
            if (control != null && !control.isDisposed()) {
                control.setRedraw(false);
                // save viewer state
                //ISelection selection = viewer.getSelection();
                viewer.getTree().deselectAll();
                
                Object[] expandedElements = viewer.getExpandedElements();
                
                /*
                 ArrayList oldNodes = new ArrayList(expandedElements.length);
                 for (int i = 0; i < expandedElements.length; i++) {
                 oldNodes.add(expandedElements[i]);
                 }
                 */
                
                // set new input
                viewer.setInput(input.getRootNodes());
                
                // restore viewer state
                viewer.setExpandedElements(expandedElements);
                
                /*
                 ArrayList newNodes = new ArrayList();
                 SakalOutlineNode newNode;
                 for (Iterator iter = this.input.getRootNodes().iterator(); iter.hasNext();) {
                 newNode = (SakalOutlineNode)iter.next();
                 restoreExpandState(newNode, oldNodes, newNodes);
                 }
                 */
                control.setRedraw(true);
                
                // disable the refresh button, enable context stuff
                ((IAction)outlineActions.get(ACTION_UPDATE)).setEnabled(false);
                ((IAction)outlineActions.get(ACTION_COPY)).setEnabled(true);
                ((IAction)outlineActions.get(ACTION_CUT)).setEnabled(true);
                ((IAction)outlineActions.get(ACTION_PASTE)).setEnabled(true);
                ((IAction)outlineActions.get(ACTION_DELETE)).setEnabled(true);
            }
        }
    }
    
    /**
     * Focuses the editor to the text of the selected item.
     * 
     * @param event the selection event
     */
    public void selectionChanged(SelectionChangedEvent event){
        super.selectionChanged(event);
        
        int offset;
        
        ISelection selection = event.getSelection();
        if (selection.isEmpty()) {
            editor.resetHighlightRange();
        }
        
        else {
            SakalOutlineNode node = (SakalOutlineNode) ((IStructuredSelection) selection).getFirstElement();
            if (node.getBeginLine() != -1) {
                try {
                	offset = editor.getDocumentProvider().getDocument(editor.getEditorInput()).getLineOffset(node.getBeginLine());
                    editor.setHighlightRange(offset, node.getName().length(), true);
                    editor.getViewer().revealRange(offset, node.getName().length());
                } catch (Exception x) {
                    editor.resetHighlightRange();
                }
            } else {
                editor.resetHighlightRange();
            }
        }        
    }
    
    public String getSelectedText() {
        IStructuredSelection selection = (IStructuredSelection)getTreeViewer().getSelection();
        if (selection == null) {
            return null;
        }
        
        SakalOutlineNode node = (SakalOutlineNode)selection.getFirstElement();
        Position pos = node.getPosition();
        
        String text;
        try {
            text = this.editor.getDocumentProvider().getDocument(this.editor.getEditorInput()).get(pos.getOffset(), pos.getLength());
        } catch (BadLocationException e) {
            return null;
        }
        return text;
    }
    
    public void removeSelectedText() {
        IStructuredSelection selection = (IStructuredSelection)getTreeViewer().getSelection();
        if (selection == null) {
            return;
        }
        
        SakalOutlineNode node = (SakalOutlineNode)selection.getFirstElement();
        Position pos = node.getPosition();
        
        try {
            this.editor.getDocumentProvider().getDocument(this.editor.getEditorInput()).replace(pos.getOffset(), pos.getLength(), "");
        } catch (BadLocationException e) {
            return;
        }
        
        //this.editor.updateModelNow();
    }
    
    public void dispose() {
        super.dispose();
    }
    
    public boolean paste(String text) {
        // get selection
        IStructuredSelection selection = (IStructuredSelection)getTreeViewer().getSelection();
        if (selection == null) {
            return false;
        }
        SakalOutlineNode node = (SakalOutlineNode)selection.getFirstElement();
        Position pos = node.getPosition();
        
        try {
            this.editor.getDocumentProvider().getDocument(this.editor.getEditorInput()).replace(pos.getOffset() + pos.getLength(), 0, text);
        } catch (BadLocationException e) {
            return false;
        }
        
        //this.editor.updateModelNow();
        return true;
    }
        
    public SakalEditor getEditor() {
        return this.editor;
    }
    
    public Clipboard getClipboard() {
        return this.clipboard;
    }    
        
    private void revealNodes(int nodeType) {
        List<SakalOutlineNode> nodeList = input.getTypeList(nodeType);
        if (nodeList != null) {
            for(SakalOutlineNode node : nodeList) {
                getTreeViewer().reveal(node);
            }
        }
    }
    
}