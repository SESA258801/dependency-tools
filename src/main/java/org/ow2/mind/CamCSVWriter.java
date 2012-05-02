package org.ow2.mind;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.HashSet;
import java.util.Set;

public class CamCSVWriter {

	private static void usage() {
		System.err.println("");
	}
	/**
	 *  Expects at least 2 arguments :
	 * @param args :
	 * args[1] : Path to the file to write. "-" means stdout
	 * args[2..] : Path to the .o object files  
	 */
	public static void main(String[] args) {
		try {
			if (args.length < 1) {
				System.err.println("Need at least 2 arguments");
				usage();
				return; 
			} 
			BufferedWriter output = null;
			if (args[0].equals("-")) {
				output = new BufferedWriter(new OutputStreamWriter(System.out));
			} else {
				File f = new File(args[0]);
				if (f.exists()) {
					System.err.println("OutputFile already exists");
					return;
				}
				output = new BufferedWriter( new FileWriter(f));
			}

			Set<File> files = new HashSet<File>();
			for (int i=1; i < args.length; i++) {
				File f = new File(args[i]);
				if (f.exists()) {
					files.add(f);
				} else {
					System.err.println("Warning : File " + args[i] + " does not exist !");
				}
			}
			BinaryObjectSet objs = new NMBinaryObjectSet(files);
			objs.resolve();
			objs.createCamCSVFile(output);
			output.close();
			return;

		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
	}
}
