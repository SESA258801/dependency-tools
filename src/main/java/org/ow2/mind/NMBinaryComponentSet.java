package org.ow2.mind;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
//import java.io.BufferedReader;
//import java.io.InputStreamReader;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.DirectoryFileFilter;
import org.apache.commons.io.filefilter.RegexFileFilter;

import java.util.ArrayList;
import java.util.Collection;

public class NMBinaryComponentSet extends BinaryObjectSet {
	private static final long serialVersionUID = 1L;

	NMBinaryComponentSet(File buildFolder, String nmCommand) throws FileNotFoundException {
		String buildFolderPath = buildFolder.getAbsolutePath();
		try {
			String[] output=find(buildFolderPath,"*.o");
			for (String objAbsPath : output) {
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
							String objName;
							if (pkg.equals(""))
								objName = objRelPath.substring(pkg.length()).split("\\.")[0];
							else
								objName = objRelPath.substring(pkg.length()+1).split("\\.")[0];
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
								this.add(new NMBinaryComponent(defName, new File(objAbsPath), nmCommand));
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
	
	public String[] find(String path, String pattern) throws IOException{
		
		@SuppressWarnings("unchecked")
		Collection<File> files = FileUtils.listFiles(
				  new File(path), 
				  new RegexFileFilter("(.*.\\.o)"), 
				  DirectoryFileFilter.DIRECTORY
				);
		ArrayList<String> stringFiles = new ArrayList<String>();
		for (File f : files) {
			stringFiles.add(f.getAbsolutePath());
		}
		return stringFiles.toArray(new String[stringFiles.size()]);
		
//		String objAbsPath;
//		Runtime runtime = Runtime.getRuntime();
//		Process find;
//		ArrayList<String> files = new ArrayList<String>();
//		String[] findCmd = {"find", path,"-name","*.o"};
//		find = runtime.exec(findCmd);
//		BufferedReader input = new BufferedReader(new InputStreamReader(find.getInputStream()));
//		while((objAbsPath = input.readLine())!=null) {
//			files.add(objAbsPath);
//		}
//		return files.toArray(new String[files.size()]);
	}
}

