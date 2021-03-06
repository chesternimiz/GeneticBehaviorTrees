package se.sciion.GBT.nodes;

import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import se.sciion.GBT.BehaviourStatus;
import se.sciion.GBT.Mutatable;
import se.sciion.GBT.Prototype;
import se.sciion.GBT.Prototypes;

/**
 * @author sciion
 *
 */
public abstract class BehaviourNode implements Mutatable, Prototype{
	
	protected BehaviourStatus status;
	private BehaviourNode parent;
	
	protected static int DEPTH_PRINT = 0;
	public static final int MAX_DEPTH = 16;
	
	public BehaviourNode() {
		status = BehaviourStatus.UNDEFINED;
		Prototypes.register(getClass().getSimpleName(), this);
	}

	protected void onStart() {
		status = BehaviourStatus.RUNNING;
	}
	
	protected abstract BehaviourStatus onUpdate();
	
	protected void onExit() {}
	
	public final BehaviourStatus tick() {
		
		// Run setup if not running
		if(status != BehaviourStatus.RUNNING)
			onStart();
		// Run update
		status = onUpdate();
		
		// Run exit if we succeeded or failed
		if(status != BehaviourStatus.RUNNING)
			onExit();
		
		return status;
	}
	
	// Get parent node, useful for sub-tree crossover.
	public BehaviourNode getParent(){
		return parent;
	}
	
	public void setParent(BehaviourNode parent){
		this.parent = parent;
	}
	
	/**
	 * Flattens the tree such that crossover can be done easier.
	 * @param nodes array where the nodes will be stored
	 */
	public abstract void  getNodes(List<BehaviourNode> nodes);
	// Only work with trivial classes
	public Element toXML(Document doc){
		Element e = doc.createElement(getClass().getSimpleName());
		return e;
	}
	
	// Only work with trivial classes
	public void fromXML(Element rootElement) {
	}

}
