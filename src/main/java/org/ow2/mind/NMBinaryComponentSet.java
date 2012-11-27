package org.ow2.mind;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class NMBinaryComponentSet extends BinaryObjectSet {
	private static final long serialVersionUID = 1L;

	NMBinaryComponentSet(File lstFile) throws FileNotFoundException {
		BufferedReader lst = new BufferedReader(new FileReader(lstFile));
		String line;
		try {
			while((line = lst.readLine())!=null) {
				String[] token = line.split(" ");
				int last = token.length -1;
				Boolean added = false;
//				Runtime runtime = Runtime.getRuntime();
//				Process find = runtime.exec("find -name " + token[last]);
//				BufferedReader input = new BufferedReader(new InputStreamReader(find.getInputStream()));
//				String objAbsPath = input.readLine();
				String objAbsPath = token[last];
				
				for (BinaryObject component : this) {
					if (component.name.equals(token[0])) 
					if ( objAbsPath != null ){
						((NMBinaryComponent)component).add(new File(objAbsPath));
						added=true;
						break;
					}
				}
				if (!added) {
					this.add(new NMBinaryComponent(token[0], new File(objAbsPath)));
					added=true;
				}
			}
			lst.close();
			for (BinaryObject component : this) {
				((NMBinaryComponent)component).resolve();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}			
	}
}
