package org.snapscript.compile;

import junit.framework.TestCase;

public class StringUtilsTest extends TestCase {

   private static final String SOURCE = 
   "import org.apache.commons.lang3.StringUtils;\n"+
   "\n"+
   "var list = \"1,2,3,4,5\".split(\",\");\n"+
   "println(StringUtils.join(list, \"&x=\"));\n";

   public void testFileExtension() throws Exception {
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      System.err.println(SOURCE);
      Executable executable = compiler.compile(SOURCE);
      executable.execute();
   }
}
