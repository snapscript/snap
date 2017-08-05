package org.snapscript.compile;

import junit.framework.TestCase;

public abstract class ScriptTestCase extends TestCase {

   protected void execute(String source) throws Exception {
      System.err.println(source);
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      Executable executable = compiler.compile(source);
      executable.execute();
   }
}
