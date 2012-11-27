package org.ow2.mind;

import java.util.Comparator;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import java.util.HashSet;
import java.util.TreeMap;
import java.util.TreeSet;

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
	
	private static String padRight(String s, int n) {
	     return String.format("%1$-" + n + "s", s);  
	}

	/**
	 * Readable representation of the object instances.
	 */
	@Override 
	public String toString() {
	    TreeMap<Symbol,BinaryObject> undefined_sorted = new TreeMap<Symbol,BinaryObject>(new UndefinedComparator(undefined));
	    undefined_sorted.putAll(undefined);
	    
	    TreeSet<Symbol> defined_sorted = new TreeSet<Symbol>(new DefinedComparator(defined));
	    defined_sorted.addAll(defined);

	    StringBuilder result = new StringBuilder();
		String NEW_LINE = System.getProperty("line.separator");

		result.append( name + " {" + NEW_LINE);
		result.append(" Defined symbols :" + NEW_LINE);
		for (Symbol s : defined_sorted) {
			result.append( "  " + padRight(s.name,80) + " " + s.type + NEW_LINE);
		}
		result.append(" Undefined symbols :" + NEW_LINE );
		for (Symbol s : undefined_sorted.keySet()) {
			if ( undefined_sorted.get(s) == null) {
				result.append( "  " + padRight(s.name,80) + " unresolved "  + NEW_LINE );
			} else {
				result.append( "  " + padRight(s.name,80) + " resolved by " + undefined_sorted.get(s).name + NEW_LINE );
			}
		}
		result.append("}");
		return result.toString();
	}
}

class UndefinedComparator implements Comparator<Symbol> {

	Map<Symbol,BinaryObject> base;
	public UndefinedComparator(Map<Symbol,BinaryObject> base) {
		this.base = base;
	}

	public int compare(Symbol a, Symbol b) {
		BinaryObject A = base.get(a);
		BinaryObject B = base.get(b);
		if ((A==null)&&(B!=null)) {
			return 1;
		} else if ((B==null)&&(A!=null)) {
			return -1;
		} else if ((A==null)&&(B==null)) {
		} else {
			int resolved = A.name.compareTo( B.name );
			if (resolved!=0) return resolved;
		}
		return a.name.compareTo( b.name );
	}
}

class DefinedComparator implements Comparator<Symbol> {

	Set<Symbol> base;
	public DefinedComparator(Set<Symbol> base) {
		this.base = base;
	}

	public int compare(Symbol a, Symbol b) {
		if ((a==null)&&(b!=null)) {
			return 1;
		} else if ((a==null)&&(b!=null)) {
			return -1;
		} else if ((a==null)&&(b==null)) {
			return 0;
		} else {
			int type = a.type.compareTo(b.type);
			if (type!=0) return type;
		}
		return a.name.compareTo( b.name );
	}
}
