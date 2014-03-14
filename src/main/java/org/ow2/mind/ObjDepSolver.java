package org.ow2.mind;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.objectweb.fractal.adl.util.FractalADLLogManager;
import org.ow2.mind.cli.CmdArgument;
import org.ow2.mind.cli.CommandLine;
import org.ow2.mind.cli.CmdFlag;
import org.ow2.mind.cli.InvalidCommandLineException;
import org.ow2.mind.cli.Options;

public class ObjDepSolver {
	protected static final String PROGRAM_NAME_PROPERTY_NAME = "ObjDepSolver.launcher.name";
	protected static final String ID_PREFIX                  = "org.ow2.mind.dependency-tools";

	protected final CmdFlag       helpOpt                    = new CmdFlag(
			ID_PREFIX
			+ "Help",
			"h", "help",
			"Print this help and exit");

	protected final CmdFlag       versionOpt                 = new CmdFlag(
			ID_PREFIX
			+ "Version",
			"v", "version",
			"Print version number and exit");
	protected final CmdArgument       dsmArg                 = new CmdArgument(
			ID_PREFIX
			+ "DSM",
			null, "dsm",
			"Output a dependency structure matrix in .csv format",
			"The file to which the dsm will be outputed ('-' for standard output)");
	protected final CmdArgument       graphArg                 = new CmdArgument(
			ID_PREFIX
			+ "Graph",
			null, "graph",
			"Output a dependency graph in .gc format",	
			"The file to which the graph will be outputed ('-' for standard output)");

	protected final CmdArgument   nmCommandArg              = new CmdArgument(
			ID_PREFIX
			+ "NMCommand",
			"N", "nm-command",
			"Specify the command to invoke a nm like tool",
			"The path or name to a version of nm matching the binary object format",
			"nm", false);

	protected final Options       options                    = new Options();

	protected CommandLine cmdLine = null;

	protected void printHelp(final PrintStream ps) {
		System.err.println("");
	}

	protected void printVersion(final PrintStream ps) {
		ps.println(getProgramName() + " version " + getVersion());
	}

	protected String getVersion() {
		final String pkgVersion = this.getClass().getPackage()
				.getImplementationVersion();
		return (pkgVersion == null) ? "unknown" : pkgVersion;
	}

	protected String getProgramName() {
		return System.getProperty(PROGRAM_NAME_PROPERTY_NAME, getClass().getName());
	}

	protected void init(final String... args) throws InvalidCommandLineException {

		/****** Initialization of the PluginManager Component *******/

		options.addOptions(helpOpt, versionOpt, dsmArg, graphArg, nmCommandArg);
		// parse arguments to a CommandLine.
		cmdLine = CommandLine.parseArgs(options, false, args);

		// If help is asked, print it and exit.
		if (helpOpt.isPresent(cmdLine)) {
			printHelp(System.out);
			System.exit(0);
		}

		// If version is asked, print it and exit.
		if (versionOpt.isPresent(cmdLine)) {
			printVersion(System.out);
			System.exit(0);
		}   
	}

	protected void run() throws IOException {

		
		// get list of binary object
		List<String> objectFiles = cmdLine.getArguments();
		Set<File> files = new HashSet<File>();
		for (String obj : objectFiles) {
			File f = new File(obj);
			if (f.exists()) {
				files.add(f);
			} else {
				System.err.println("Warning : File " + obj + " does not exist !");
			}
		}
		BinaryObjectSet objs = new NMBinaryObjectSet(files,nmCommandArg.getValue(cmdLine));
		objs.resolve();
		/*
		 * Output a graph if required
		 */
		if (graphArg.isPresent(cmdLine)) {
			BufferedWriter graphOutput = null;
			if (graphArg.getValue(cmdLine).equals("-")) {
				graphOutput = new BufferedWriter(new OutputStreamWriter(System.out));
			} else {
				File f = new File(graphArg.getValue(cmdLine));
				if (f.exists()) {
					System.err.println("OutputFile for graph already exists");
					return;
				}
				graphOutput = new BufferedWriter( new FileWriter(f));
			}
			objs.createDotDependencyFile(graphOutput);
			graphOutput.close();
		}

		/*
		 * Output a dsm if required
		 */
		if (dsmArg.isPresent(cmdLine)) {
			BufferedWriter dsmOutput = null;
			if (dsmArg.getValue(cmdLine).equals("-")) {
				dsmOutput = new BufferedWriter(new OutputStreamWriter(System.out));
			} else {
				File f = new File(graphArg.getValue(cmdLine));
				if (f.exists()) {
					System.err.println("OutputFile for graph already exists");
					return;
				}
				dsmOutput = new BufferedWriter( new FileWriter(f));
			}
			objs.createCamCSVFile(dsmOutput);
			dsmOutput.close();
		}

		return;
	}

	/**
	 *  Expects at least 2 arguments :
	 * @param args :
	 * args[1] : Path to the file to write. "-" means stdout
	 * args[2..] : Path to the .o object files  
	 * @throws InvalidCommandLineException 
	 * @throws IOException 
	 */
	public static void main(String[] args) throws InvalidCommandLineException, IOException {
		ObjDepSolver launcher = new ObjDepSolver();
		launcher.init(args);
		launcher.run();
	}
}
