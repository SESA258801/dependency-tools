/**
 * Copyright (C) 2012 Schneider Electric
 *
 * This file is part of "Mind Compiler" is free software: you can redistribute 
 * it and/or modify it under the terms of the GNU Lesser General Public License 
 * as published by the Free Software Foundation, either version 3 of the 
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT 
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 * Contact: mind@ow2.org
 *
 * Authors: Julien TOUS
 * Contributors: 
 */

package org.ow2.mind;

import static org.testng.Assert.assertTrue;
import org.testng.annotations.Test;

import java.io.File;
import java.util.Set;
import java.util.HashSet;

public class TestDependencies {

	protected static File         buildDir = new File("target/build/");
	
	protected void cleanBuildDir() {
		if (buildDir.exists()) deleteDir(buildDir);
	}

	protected void deleteDir(final File f) {
		if (f.isDirectory()) {
			for (final File subFile : f.listFiles())
				deleteDir(subFile);
		}
		assertTrue(f.delete(), "Can't delete \"" + f + "\".");
	}
	
	@Test(groups = {"checkin"})
	public void testNMBinaryObjectImport() throws Exception {
		File obj = new File("src/test/ressources/NMBinaryObject/toto.o");
		if (obj.exists()) {
			BinaryObject toto = new NMBinaryObject(obj);
			System.out.println(toto);
		} else {
			System.out.println("File not found : " + obj.getAbsolutePath());
		}
	}
	
	@Test(groups = {"checkin"})
	public void testBinaryObjectResolveAgainst() throws Exception {
		BinaryObject toto = new NMBinaryObject( new File("src/test/ressources/BinaryObjectSet/toto.o"));
		BinaryObject tata = new NMBinaryObject( new File("src/test/ressources/BinaryObjectSet/tata.o"));
	    toto.resolveAgainst(tata);
	    tata.resolveAgainst(toto);
	    System.out.println(toto);
	    System.out.println(tata);
		
	}
	
	@Test(groups = {"checkin"})
	public void testBinaryObjectSet() throws Exception {
		BinaryObject toto = new NMBinaryObject( new File("src/test/ressources/BinaryObjectSet/toto.o"));
		BinaryObject tata = new NMBinaryObject( new File("src/test/ressources/BinaryObjectSet/tata.o"));
	    BinaryObjectSet objs = new BinaryObjectSet();
	    objs.add(toto);
	    objs.add(tata);
	    objs.resolve();
	    for (BinaryObject obj : objs) {
			System.out.println(obj);
		}
	}
	
	@Test(groups = {"checkin"})
	public void testNMBinaryObjectSet() throws Exception {
		File toto = new File("src/test/ressources/BinaryObjectSet/toto.o");
		File tata = new File("src/test/ressources/BinaryObjectSet/tata.o");
		Set<File> files = new HashSet<File>();
		files.add(toto);
		files.add(tata);
	    BinaryObjectSet objs = new NMBinaryObjectSet(files);

	    objs.resolve();
	    for (BinaryObject obj : objs) {
			System.out.println(obj);
		}
	}
	
	@Test(groups = {"checkin"})
	public void testCamCSVWriter() throws Exception {
	   String[] args = {"src/test/ressources/BinaryObjectSet/toto.o","src/test/ressources/BinaryObjectSet/tata.o", "--dsm=-"};
	   ObjDepSolver.main(args);	
	}
	
	@Test(groups = {"checkin"})
	public void testDotWriter() throws Exception {
	   String[] args = {"src/test/ressources/BinaryObjectSet/toto.o","src/test/ressources/BinaryObjectSet/tata.o", "--graph=-"};
	   ObjDepSolver.main(args);	
	}
	
	@Test(groups = {"checkin"})
	public void testNMBinaryComponent() throws Exception {
		File toto = new File("src/test/ressources/BinaryObjectSet/toto.o");
		File tata = new File("src/test/ressources/BinaryObjectSet/tata.o");
	    Set<File> files = new HashSet<File>();
	    files.add(toto);
	    files.add(tata);
	    BinaryComponent comp = new NMBinaryComponent("Comp", files);
		System.out.println(comp);
	}
	
	@Test(groups = {"checkin"})
	public void testBinaryComponent() throws Exception {
		File toto = new File("src/test/ressources/BinaryComponent/toto.o");
		File tata = new File("src/test/ressources/BinaryComponent/tata.o");
	    Set<File> files1 = new HashSet<File>();
	    files1.add(toto);
	    files1.add(tata);
	    
		File titi = new File("src/test/ressources/BinaryComponent/titi.o");
		File tutu = new File("src/test/ressources/BinaryComponent/tutu.o");
	    Set<File> files2 = new HashSet<File>();
	    files2.add(titi);
	    files2.add(tutu);
	    
	    BinaryComponent comp1 = new NMBinaryComponent("Comp1", files1);
	    BinaryComponent comp2 = new NMBinaryComponent("Comp2", files2);

	    BinaryObjectSet comps = new BinaryObjectSet();
	    comps.add(comp1);
	    comps.add(comp2);

	    comps.resolve();
	    
		System.out.println(comp1);
		System.out.println(comp2);
	}
	
}
