package org.ow2.mind;


import java.io.BufferedWriter;
import java.io.IOException;
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
	
	public void createDotDependencyFile(BufferedWriter out) throws IOException {
		String NEW_LINE = System.getProperty("line.separator");
		out.write("digraph {" + NEW_LINE );
		for (BinaryObject from : this){
			for (BinaryObject to : from.undefined.values() ) {
				if (to !=null) out.write(from.name.replace(".","_") + "->" + to.name.replace(".","_") + NEW_LINE );
			}
		}
		out.write("}" + NEW_LINE );
	}
	
	public void createCamCSVFile(BufferedWriter out) throws IOException {
		String NEW_LINE = System.getProperty("line.separator");
		out.write("BINARY DSM EXPORTED FROM CAMBRIDGE ADVANCED MODELLER" + NEW_LINE  + NEW_LINE );
		out.write("\"\",\"Hierachy ID\"");
		for (int i=1; i <= this.size(); i++) out.write(",\"" + i +"\"");
		out.write( NEW_LINE );
		int i = 0;
		for (BinaryObject from : this){
			i++;
			out.write("\"" + from.name + "\"," + "\"" + i + "\"");
			for (BinaryObject to : this ) {
				boolean isWritten = false;
				for (BinaryObject dep : from.undefined.values()) {
					if (dep == to) {
						out.write(",\"1\"");
						isWritten = true;
						break;
					} 
				}
				if (isWritten == false)	out.write(",\"\"");
			}
			out.write( NEW_LINE );	
		}
	}
}	

