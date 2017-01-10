package org.snapscript.compile;

import junit.framework.TestCase;

import org.snapscript.core.EmptyModel;

public class StreamTest extends TestCase{
   
   private static final String SOURCE =
   "import util.stream.Collectors;\n"+
   "var l = ['true', 'false', 'true', 'false', 'false'];\n"+
   "var s = l.stream()\n"+
   "          .map(Boolean::parseBoolean)\n"+
   "          .collect(Collectors.toList());\n"+
   "\n"+
   "println(s);\n";

   
   public void testScriptTypeDescription() throws Exception {
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      System.err.println(SOURCE);
      compiler.compile(SOURCE).execute(new EmptyModel());
   }
}
