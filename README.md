dependency-tools
================

Set of java classes using `nm` (from binutils) to extract dependencies between object files.

Contains :

mind-violation
--------------

Executable used to identify *hidden* dependencies between Mind components.
A dependency is considered *hidden* if it is not formally explicited in the .adl file of the component.

Usage :
mind-violation buildFolder


Hidden dependencies are extracted from component definitions (not instances).
Retrieving which object file belongs to which component definition, is done by reversing the output file naming convention of the compiler.

Three files will be created :

1. HiddenDeps.txt
2. HiddenDeps.gv
3. HiddenDeps.csv

.txt file describe textually, component by component, external symbols used, or external symbols exported.

.gv files are suitable to be plotted using graphviz tools, components are represented by nodes, and hidden dependencies by directed edge. The explicited dependencies (ADL diagram) are not shown on this diagram.

.csv file exports dependencies as a Dependency Structure Matrix in csv format.


object-dependencies
-------------------

Executable used to extract dependencies from legacy C compilation.

Can output :

1. A .gv file (which is not usable for real life project)
2. A .csv file suitable to be treated by CAM software (Cambridge Advanced Modeller http://www-edc.eng.cam.ac.uk/cam ) for clustering or fragmenting. This representation is especially useful to extract implied architecture from legacy code.
