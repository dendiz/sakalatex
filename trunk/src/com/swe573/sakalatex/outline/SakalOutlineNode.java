package com.swe573.sakalatex.outline;

import java.util.ArrayList;

import org.eclipse.core.resources.IFile;
import org.eclipse.jface.text.Position;

public class SakalOutlineNode {
    
    // These should be allocated between -1 to 100
    public static final int TYPE_DOCUMENT = -1;
    public static final int TYPE_PART = 0;
    public static final int TYPE_CHAPTER = 1;
    public static final int TYPE_SECTION = 2;
    public static final int TYPE_SUBSECTION = 3;
    public static final int TYPE_SUBSUBSECTION = 4;
    public static final int TYPE_PARAGRAPH = 5;
        
    public static final int TYPE_ENVIRONMENT = 13;
    public static final int TYPE_PREAMBLE = 14;
    //public static final int TYPE_ERROR = 99;
    public static final int TYPE_INPUT = 45;
    
    private String name;
    private int type;
    private int beginLine=-1, endLine;
    private int offsetOnLine;
    private int declarationLength;
    private SakalOutlineNode parent;
    private ArrayList children;
    private Position position;
    private IFile file;

    public SakalOutlineNode(String name, int type, int beginLine, SakalOutlineNode parent) {
        this.name = name;
        this.type = type;
        this.beginLine = beginLine;
        this.parent = parent;
    }
    
    public SakalOutlineNode(String name, int type, int beginLine) {
        this.name = name;
        this.type = type;
        this.beginLine = beginLine;
        //this.parent = parent;
    }

    public SakalOutlineNode(String name, int type, int beginLine, int offset, int length) {
        this.name = name;
        this.type = type;
        this.beginLine = beginLine;
        this.offsetOnLine = offset;
        this.declarationLength = length;
    }

    public SakalOutlineNode copy(IFile texFile) {
        SakalOutlineNode on = new SakalOutlineNode(name, type, beginLine, offsetOnLine, declarationLength);
        on.endLine = endLine;
        on.position = position;
        on.file = texFile;
        return on;
    }
    
    public void addChild(SakalOutlineNode child) {
        if (this.children == null)
            this.children = new ArrayList();
        this.children.add(child);
    }

    public void addChild(SakalOutlineNode child, int index) {
        if (this.children == null)
            this.children = new ArrayList();
        this.children.add(index, child);
    }
    
    public boolean deleteChild (SakalOutlineNode child) {
        return this.children.remove(child);
    }
    
    public ArrayList getChildren() {
        return children;
    }
    public void setChildren(ArrayList children) {
        this.children = children;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public SakalOutlineNode getParent() {
        return parent;
    }
    public void setParent(SakalOutlineNode parent) {
        this.parent = parent;
    }
    public int getType() {
        return type;
    }
    public void setType(int type) {
        this.type = type;
    }
    public int getBeginLine() {
        return beginLine;
    }
    public void setBeginLine(int beginLine) {
        this.beginLine = beginLine;
    }
    public int getEndLine() {
        return endLine;
    }
    public void setEndLine(int endLine) {
        this.endLine = endLine;
    }
    public Position getPosition() {
        return position;
    }
    public void setPosition(Position position) {
        this.position = position;
    }
    
    public IFile getIFile() {
        return file;
    }
    
    public void setIFile(IFile file) {
        this.file = file;
    }

    public int getDeclarationLength() {
        return declarationLength;
    }

    public int getOffsetOnLine() {
        return offsetOnLine;
    }

    public String toString() {
    	return this.getType() + " " + this.getName() + " " + 
        		this.getPosition().getOffset() + " " + this.getPosition().getLength() +
				super.toString();
    }

    public static int getSmallerType(int type) {
        if (type <= TYPE_PARAGRAPH) {
            return type - 1;
        }
        switch (type) {
        case TYPE_ENVIRONMENT:
            return TYPE_PARAGRAPH;
        case TYPE_PREAMBLE:
            return TYPE_ENVIRONMENT;
        case TYPE_INPUT:
            return TYPE_PREAMBLE;
        }
        return TYPE_DOCUMENT;
    }
    
}
