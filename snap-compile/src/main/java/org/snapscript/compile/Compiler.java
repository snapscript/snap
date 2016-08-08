package org.snapscript.compile;

public interface Compiler {
   Executable compile(String source) throws Exception;
   Executable compile(String source, boolean verbose) throws Exception;   
}
