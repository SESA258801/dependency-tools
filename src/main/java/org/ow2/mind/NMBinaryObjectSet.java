package org.ow2.mind;

import java.io.File;
import java.util.Set;

/**
 * 
 * @author Julien TOUS
 * BinaryObjectSet constructable from a set of "nm readable" object (.o) Files 
 *
 */
public class NMBinaryObjectSet extends BinaryObjectSet {
	private static final long serialVersionUID = 1L;

	NMBinaryObjectSet(Set<File> files) {
		for (File f: files) {
			add(new NMBinaryObject(f));
		}
	}

	NMBinaryObjectSet(Set<File> files, String nmCommand) {
		for (File f: files) {
			add(new NMBinaryObject(f, nmCommand));
		}
	}
}
