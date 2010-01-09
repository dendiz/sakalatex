package com.swe573.sakalatex.outline;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SakalOutlineInput {

	private List<SakalOutlineNode> rootNodes;
	private int treeDepth;
	private Map<Integer, List<SakalOutlineNode>> typeLists;
	
	public SakalOutlineInput(List<SakalOutlineNode> rootNodes) {
		this.rootNodes = rootNodes;
		typeLists = new HashMap<Integer, List<SakalOutlineNode>>();
        treeDepth = -1;
	}
	
	public void addNode(SakalOutlineNode node) {
		// get list for this type
		List<SakalOutlineNode> typeList = typeLists.get(node.getType());
		
		// if no list for this type exists yet, create one
		if (typeList == null) {
			typeList = new ArrayList<SakalOutlineNode>();
			typeLists.put(node.getType(), typeList);
		}
		
		// add node to type list
		typeList.add(node);
	}
	
	public List<SakalOutlineNode> getTypeList(int nodeType) {
		if (typeLists.containsKey(nodeType)) {
			return typeLists.get(nodeType);
		} else {
			return null;
		}
	}

	public List<SakalOutlineNode> getRootNodes() {
		return rootNodes;
	}
	
	public void setRootNodes(List<SakalOutlineNode> rootNodes) {
		this.rootNodes = rootNodes;
	}

	public int getTreeDepth() {
        //Calculate TreeDepth on the fly
		if (treeDepth == -1)
            calculateTreeDepth();
        return treeDepth;
	}

	public void setTreeDepth(int treeDepth) {
		this.treeDepth = treeDepth;
	}
    
    public void calculateTreeDepth() {
        treeDepth = 0;
        for (SakalOutlineNode node : rootNodes) {
            int localDepth = handleNode(node, 0);
            if (localDepth > treeDepth)
                treeDepth = localDepth;            
        }
    }
    
    private int handleNode(SakalOutlineNode node, int parentDepth) {

        // iterate through the children
        List<SakalOutlineNode> children = node.getChildren();
        int maxDepth = parentDepth + 1;
        if (children != null) {
            for (SakalOutlineNode child : children) {
                int localDepth = handleNode(child, parentDepth + 1);
                if (localDepth > maxDepth) {
                    maxDepth = localDepth;
                }
            }
        }
        return maxDepth;
    }

}
