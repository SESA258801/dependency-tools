package org.ow2.mind;

/**
 * 
 * @author Julien TOUS
 * 
 * This class holds a basic representation of .o referenced symbols 
 *
 */

public class Symbol {
	/**
	 * Symbol name.
	 */
	public String name;
	
	/**
	 * File name where the symbol is referenced 
	 */
	public String origin;
	
	/**
	 * Category of the symbol 
	 */
	public enum Type { text, data, bss, undef } 
	public Type type;

	/**
	 * Constructor
	 * @param N : Name
	 * @param O : Origin
	 * @param T : Type
	 */
	Symbol(String N, String O, Type T) {
		name = N;
		origin = O;
		type = T;
	}
}
