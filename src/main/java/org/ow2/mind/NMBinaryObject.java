package org.ow2.mind;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import org.ow2.mind.Symbol.Type;

/**
 * 
 * @author Julien TOUS
 * 
 * This class, adds a constructor to its parent BinaryObject
 * The constructor works by using "nm" (from the GNU binutils project) 
 * to extract symbol information from object files. 
 *
 */

public class NMBinaryObject extends BinaryObject {
	public enum State { start, addr, type } 

	NMBinaryObject(File file) {
		try {
			Runtime runtime = Runtime.getRuntime();
			Process nm = runtime.exec("nm " + file.getPath());
			BufferedReader input = new BufferedReader(new InputStreamReader(nm.getInputStream()));
			name=file.getName();
			String line;
			while ((line = input.readLine()) != null) {
				String[] fields = line.split("\\s+");
				State state= State.start;
				Symbol.Type type=null;
				for (String f : fields) {
					if (state == State.start) {		
						if (f.matches("[0-9a-fA-F]+")) {
							// Address : First field of defined symbol
							state = State.addr;
						} else if (f.matches("U")) {
							// Undefined symbol
							type=Type.undef;
							state=State.type;
						} 
					} else if (state == State.addr) {					
						if (f.matches("[Tt]")) {
							// Text symbol
							type = Type.text;
							state=State.type;
						} else if (f.matches("[SsBbC]")) {
							// BSS symbol
							type = Type.bss;
							state=State.type;
						} else if (f.matches("[RrDdGg]")) {
							// Data symbol
							type = Type.data;
							state=State.type;
						}
					} else if (type == Type.undef ) {
						undefined.put(new Symbol(f, name, type), null);
						state=State.start;
					} else if (type == Type.text || type == Type.data || type == Type.bss ) {
						defined.add(new Symbol(f, name, type));
						state=State.start;
					}
				}
		    }
		    input.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
