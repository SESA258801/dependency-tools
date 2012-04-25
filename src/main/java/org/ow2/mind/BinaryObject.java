package org.ow2.mind;

import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import java.util.HashSet;

/**
 * 
 * @author Julien TOUS
 * References information about .o files.
 * Mainly : name, defined Symbols and undefined Symbols
 * and eventually object file resolving the undefined Symbols 
 *
 * This class does not provide a Constructor, nor a way to edit the contained information.
 * Those should be defined into inherited class.
 * 
 */
abstract public class BinaryObject {

	public String name;
	public String source;
	public Set<Symbol> defined = new HashSet<Symbol>();
	public Map<Symbol,BinaryObject> undefined = new HashMap<Symbol,BinaryObject>();

	
	/**
	 * resolveAgainst : try to resolve undefined symbols by comparing with another BinaryObject
	 * @param obj : The object into which defined symbols are searched for.
	 */
	public void resolveAgainst(BinaryObject obj) {
		for (Symbol usym : undefined.keySet()) {
			if (undefined.get(usym) == null) {
				for (Symbol dsym : obj.defined) {
					if ( usym.name.equals(dsym.name)) {
						undefined.put(usym,obj);
						break;
					}				
				}
			}
		}
	}

	/**
	 * Readable representation of the object instances.
	 */
	@Override 
	public String toString() {
		StringBuilder result = new StringBuilder();
		String NEW_LINE = System.getProperty("line.separator");

		result.append( name + " {" + NEW_LINE);
		result.append(" Defined symbols :" + NEW_LINE);
		for (Symbol s : defined) {
			result.append( "  " + s.name + " " + s.type + NEW_LINE);
		}
		result.append(" Undefined symbols :" + NEW_LINE );
		for (Symbol s : undefined.keySet()) {
			if ( undefined.get(s) == null) {
				result.append( "  " + s.name + " unresolved "  + NEW_LINE );
			} else {
				result.append( "  " + s.name + " resolved by " + undefined.get(s).name + NEW_LINE );
			}
		}
		result.append("}");
		return result.toString();
	}
}
