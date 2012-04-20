package org.ow2.mind;


import java.util.HashSet;
/**
 * 
 * @author Julien TOUS
 * 
 *  Implements a Set of BinaryObject, together with methods to resolve undefined symbols between them.
 *  Resolved symbols are handled by Symbols objects them-self, but the class holds a flag to keep track of the state of the status of the undefined symbols   
 *
 */
public class BinaryObjectSet extends HashSet<BinaryObject> {

	private static final long serialVersionUID = 1L;
	
	/**
	 * Flag to keep track of the "resolved-ness" of the Set
	 * and it's accessor.
	 */
	protected boolean resolved = true;
	public boolean isResolved() {
		return resolved;
	}
	
	/**
	 * add a new BinaryObject to the Set.
	 * Does not try to resolve undefined symbols in either direction and so set the resolved flag to false 
	 */
	@Override
	public boolean add(BinaryObject newObj) {
		boolean isAdded = super.add(newObj);
		if (isAdded) resolved=false;
		return isAdded;
	}
	/**
	 * add a new BinaryObject to the Set, and resolve undefined symbols related to the new object in both direction.
	 * If the Set was already resolved, the new Set will still be resolved. 
	 */
	public boolean addResolve(BinaryObject newObj) {
		boolean isAdded = super.add(newObj);
		for (BinaryObject o: this) {
			o.resolveAgainst(newObj);
			newObj.resolveAgainst(o);
		}
		return isAdded;
	}
	
	/**
	 * Try to resolve all undefined symbols in the Set. 
	 */
	public void resolve() {
		for (BinaryObject uobj : this) {
			for (BinaryObject dobj : this ) {
				uobj.resolveAgainst(dobj);
			}
		}
		resolved = true;
	}
}	

