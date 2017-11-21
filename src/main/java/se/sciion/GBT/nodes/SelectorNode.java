package se.sciion.GBT.nodes;

import java.util.ArrayList;
import java.util.List;

import se.sciion.GBT.BehaviorStatus;

public class SelectorNode extends CompositeNode{

	public SelectorNode(List<BehaviorNode> nodes) {
		super(nodes);
	}

	public SelectorNode(BehaviorNode... nodes) {
		super(nodes);
	}


	@Override
	protected BehaviorStatus onUpdate() {
		// Nothing to do
		if(children.isEmpty()) {
			return status;
		}
		
		// Check if we reached end of children
		if(currentChild < children.size()) {
			status = children.get(currentChild).tick();
			
			// We proceed until all children tested
			if(status == BehaviorStatus.FAILURE && currentChild < children.size()){
				currentChild++;
				status = onUpdate();
			}
		}
		return status;
	}

	
	@Override
	public BehaviorNode prototype() {
		List<BehaviorNode> nodes = new ArrayList<BehaviorNode>();
		for(BehaviorNode n: children){
			nodes.add(n.prototype());
		}
		return new SelectorNode(nodes);
	}

}