@echo off

@REM ==== CHECK MIND_HOME ===
@REM use the batch path to determine MIND_HOME if not defined.
pushd %~dp0..\
set MINDC_ROOT=%cd%
popd

if "%MIND_HOME%" == "" set MIND_HOME=%MINDC_ROOT%

set /A ARGS_COUNT=0
for %%A in (%*) do set /A ARGS_COUNT+=1

if not %ARGS_COUNT% == 1 goto error

java -cp %MIND_HOME%\lib\dependency-tools-*.jar org.ow2.mind.ProcessImplementationFileListing %*
if not errorlevel 1 goto end

:error
echo Usage : %0 ImplementationOutputFiles.lst
echo Generate listing files of hiden dependencies between components from Mind output file listing :
echo HidenDeps.txt \"readable\" list of shared symbols.
echo HidenDeps.dot hiden dependency graph in graphviz's dot format.
echo HidenDeps.csv Dependency Structure Matrix suitable forimport by CAM.

:end

