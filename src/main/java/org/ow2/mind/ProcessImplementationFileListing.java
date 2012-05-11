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

			BufferedWriter dot_out = new BufferedWriter(new FileWriter("comp.dot"));
			components.createDotDependencyFile(dot_out);
			dot_out.flush();
			dot_out.close();
			
			BufferedWriter csv_out = new BufferedWriter(new FileWriter("csv.dot"));
			components.createCamCSVFile(csv_out);
			csv_out.flush();
			csv_out.close();
			
			BufferedWriter text_out = new BufferedWriter(new FileWriter("comp.deps"));

			for (BinaryObject comp : components) {
				(text_out).write(comp.toString());				
			}
			text_out.flush();
			text_out.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
