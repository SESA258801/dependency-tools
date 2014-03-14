package org.ow2.mind;

public interface IBinaryObject {
	/**
	 * Get the name of the binary object
	 * @return the name of the binary object
	 */
	String getName();
	/**
	 * Set the name of the binary object
	 * @param name
	 */
	void setName(String name);
	/**
	 * Add a defined symbol to the binary object representation 
	 * @param defined : the defined Symbol to add.
	 */
	void addDefinedSymbol(Symbol defined);
	/**
	 * Add an undefined symbol to the binary object representation,
	 * the BinaryObject where the symbol is defined is set to null. 
	 * @param undefined : the undefined Symbol to add.
	 */
	void addUndefinedSymbol(Symbol undefined);
}
