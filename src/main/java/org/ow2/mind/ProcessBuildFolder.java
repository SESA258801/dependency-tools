package org.ow2.mind;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.util.List;

import org.ow2.mind.cli.CmdArgument;
import org.ow2.mind.cli.CmdFlag;
import org.ow2.mind.cli.CmdOption;
import org.ow2.mind.cli.CommandLine;
import org.ow2.mind.cli.InvalidCommandLineException;
import org.ow2.mind.cli.Options;

public class ProcessBuildFolder {
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
	protected NMBinaryComponentSet components = null;

	protected final CmdArgument   nmCommandArg              = new CmdArgument(
			ID_PREFIX
			+ "NMCommand",
			"N", "nm-command",
			"Specify the command to invoke a nm like tool",
			"<nmExecutable>",
			"nm", false);


	protected final Options       options                    = new Options();

	protected CommandLine cmdLine = null;
	File buildFolder = null;
	String nmCommand = null;

	protected void printHelp(final PrintStream ps) {
		printUsage(ps);
		ps.println();
		ps.println("Available options are :");
		int maxCol = 0;

		for (final CmdOption opt : options.getOptions()) {
			final int col = 2 + opt.getPrototype().length();
			if (col > maxCol) maxCol = col;
		}
		for (final CmdOption opt : options.getOptions()) {
			final StringBuffer sb = new StringBuffer("  ");
			sb.append(opt.getPrototype());
			while (sb.length() < maxCol)
				sb.append(' ');
			sb.append("  ").append(opt.getDescription());
			ps.println(sb);
		}
	}

	protected void printUsage(final PrintStream ps) {
		ps.println("Usage: " + getProgramName()
				+ " [OPTIONS] <buildFolder>");
		ps.println("  where <buildFolder> is the output folder from a mindc compilation");
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

	public static void main(String[] args) throws InvalidCommandLineException {
		ProcessBuildFolder launcher = new ProcessBuildFolder();
		launcher.init(args);
		launcher.resolve();
		launcher.dump();

	}

	private void dump() {
		String directory = buildFolder.getParentFile().getPath();

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

	private void resolve() {		
		try {
			components = new NMBinaryComponentSet(buildFolder,nmCommand);
		} catch (FileNotFoundException e) {
			System.out.println("File not found : " + buildFolder);
		}
		components.resolve();
		components.stripInternalOnly();
	}

	private void init(final String... args) throws InvalidCommandLineException {

		options.addOptions(helpOpt, versionOpt, nmCommandArg);
		// parse arguments to a CommandLine.
		cmdLine = CommandLine.parseArgs(options, false, args);

		// If help is asked, print it and exit.
		if (helpOpt.isPresent(cmdLine) || (args.length == 0)) {
			printHelp(System.out);
			System.exit(0);
		}

		// If version is asked, print it and exit.
		if (versionOpt.isPresent(cmdLine)) {
			printVersion(System.out);
			System.exit(0);
		}

		List<String> targets = cmdLine.getArguments();
		if (targets.size() != 1) {
			printHelp(System.out);
			System.exit(0);
		} else {
			buildFolder = (new File(targets.get(0))).getAbsoluteFile();
		}
		nmCommand = nmCommandArg.getValue(cmdLine);
	}
}
