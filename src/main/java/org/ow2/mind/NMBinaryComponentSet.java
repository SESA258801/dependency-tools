package org.ow2.mind;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.nio.file.Path;
import java.nio.file.Paths;

public class NMBinaryComponentSet extends BinaryObjectSet {
	private static final long serialVersionUID = 1L;

	NMBinaryComponentSet(File buildFolder) throws FileNotFoundException {
		String buildFolderPath = buildFolder.getAbsolutePath();
		String objAbsPath;
//		Runtime runtime = Runtime.getRuntime();
//		Process find;
//		String[] findCmd = {"find",buildFolderPath,"-name","*.o"};
		String output;
		try {
			output=Find.find(Paths.get(buildFolderPath),"*.o");
			//find = runtime.exec(findCmd);
			//BufferedReader input = new BufferedReader(new InputStreamReader(find.getInputStream()));
			BufferedReader input = new BufferedReader(new StringReader(output));
			while((objAbsPath = input.readLine())!=null) {
				//Skipping instances
				if ( !objAbsPath.contains("instances.")) {//Fixme instances should be permitted as a definition name
					//Now dealing with definitions only
					String objRelPath = objAbsPath.substring(buildFolderPath.length()+1);
					int lastSlash=objRelPath.lastIndexOf(File.separatorChar);
					String pkg;
					if ( lastSlash == -1 ) {
						pkg = "";
					} else {
						pkg = objRelPath.substring(0, lastSlash).replace(File.separatorChar, '.');
					}
					if (!pkg.equals("fractal.internal")) {
						if (!objRelPath.startsWith("Factory_tmpl_", pkg.length())) {
							String objName = objRelPath.substring(pkg.length()+1).split("\\.")[0];
							String[] underscored = objName.split("_");
							int j = 0;
							for (int i = underscored.length-1; i > 0; i--){
								if (underscored[i].matches("impl[0-9]+")) j=i;
								if (underscored[i].equals("ctrl")) j=i;
							}
							StringBuilder def = new StringBuilder();
							for (int i = 0; i < j-1; i++) {
								def.append(underscored[i]);
								def.append("_");
							}
							def.append(underscored[j-1]);
							String defName = def.toString();

							Boolean added = false;


							for (BinaryObject component : this) {
								if (component.name.equals(defName))
									if ( objAbsPath != null ){
										((NMBinaryComponent)component).add(new File(objAbsPath));
										added=true;
										break;
									}
							}
							if (!added) {
								this.add(new NMBinaryComponent(defName, new File(objAbsPath)));
								added=true;
							}
						}
					}
				}
			}
			for (BinaryObject component : this) {
				((NMBinaryComponent)component).resolve();
			}
		} catch (IOException e) {
			System.out.println("Trouble while running find !");
			e.printStackTrace();
		}
	}
}
