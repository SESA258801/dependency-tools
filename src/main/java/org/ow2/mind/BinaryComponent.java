package org.ow2.mind;

import java.io.File;
import java.util.HashSet;
import java.util.Iterator;

/**
 * 
 * @author Julien TOUS
 * 
 */

public abstract class BinaryComponent extends BinaryObject {
	public enum State { start, addr, type } 
	
	/**
	 * Flag to keep track of the "resolved-ness" of the Set
	 * and it's accessor.
	 */
	protected boolean resolved = true;
	public boolean isResolved() {
		return resolved;
	}
	
	/*
	 * Remove self referenced undefined symbols
	 */
	protected void clearResolved() {
		for (Iterator<Symbol> U = undefined.keySet().iterator(); U.hasNext();) {
			if (undefined.get(U.next()) == this) {
				U.remove();
			}
		}
	}
	
	public abstract void add(File file);
	
	void resolve() {
		clearDuplicate();
		/*
		 * resolveAgainst ourself as the component is made of several files.
		 * Inter files dependency are not resolved by the compiler so we need 
		 * track them here. 
		 */
		resolveAgainst(this);
		/*
		 * Once we resolved what could be resolved, lets remove unnecessary link. 
		 */
		clearResolved();
		resolved=true;
	}
	
	/*
	 * Remove duplicated referenced undefined symbols
	 */
	protected void clearDuplicate() {
		Symbol sym1;
		Symbol sym2;
		Iterator<Symbol> U1 = undefined.keySet().iterator();
		while ( U1.hasNext()) {
			sym1 = U1.next();
			Iterator<Symbol> U2 = (new HashSet<Symbol>( undefined.keySet()) ).iterator();
			while (U2.hasNext()) {
				sym2 = U2.next();
				if (sym1 != sym2) {
					if (sym1.name.equals(sym2.name)) {
						sym2.origin = sym2.origin + File.pathSeparator + sym1.origin;
						U1.remove();
						break;
					}
				}
			}
		}
	}
}
