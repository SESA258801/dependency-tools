package org.ow2.mind;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class ProcessImplementationFileListing {
	static NMBinaryComponentSet components = null;
	
	public static void main(String[] args) {
		try {
			components = new NMBinaryComponentSet(new File(args[0]));
			components.resolve();
			BufferedWriter out = new BufferedWriter(new FileWriter("comp.deps"));

			for (BinaryObject comp : components) {
				(out).write(comp.toString());				
			}
			out.flush();
			out.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
