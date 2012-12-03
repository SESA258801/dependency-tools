package org.ow2.mind;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

public class ProcessBuildFolder {
	static NMBinaryComponentSet components = null;
	
	public static void main(String[] args) {
		File file = new File(args[0]);
		file = file.getAbsoluteFile();
		String directory = file.getParentFile().getPath();
			try {
				components = new NMBinaryComponentSet(file);
			} catch (FileNotFoundException e) {
				System.out.println("File not found : " + file);
			}
			components.resolve();
			components.stripInternalOnly();
			try {
			BufferedWriter dot_out = new BufferedWriter(new FileWriter( directory + File.separatorChar + "HidenDeps.dot"));
			components.createDotDependencyFile(dot_out);
			dot_out.flush();
			dot_out.close();
			
			BufferedWriter csv_out = new BufferedWriter(new FileWriter( directory + File.separatorChar + "HidenDeps.csv"));
			components.createCamCSVFile(csv_out);
			csv_out.flush();
			csv_out.close();
			
			BufferedWriter text_out = new BufferedWriter(new FileWriter( directory + File.separatorChar + "HidenDeps.txt"));

			for (BinaryObject comp : components) {
				(text_out).newLine();
				(text_out).write(comp.toString());
				(text_out).newLine();
			}
			text_out.flush();
			text_out.close();
			
		} catch (IOException e) {
				
		}
	}
}
