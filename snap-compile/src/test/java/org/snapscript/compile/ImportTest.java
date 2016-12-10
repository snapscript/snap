package org.snapscript.compile;

import java.awt.geom.Line2D;
import java.awt.geom.Line2D.Double;

import junit.framework.TestCase;

public class ImportTest extends TestCase {

   private static final String SOURCE=
   "import lang.reflect.*;\n"+
   "import static lang.Integer.*;\n"+         
   "import static util.stream.Collectors.toMap;\n"+
   "import awt.geom.Line2D;\n"+
   "import awt.geom.Line2D.Double;\n"+
   "import awt.Color as RGB;\n"+
   "class HashMap{\n"+
   "   static dump() {\n"+
   "      println('x');\n"+
   "   }\n"+
   "}\n"+
   "println(Field.class);\n"+
   "println(new Line2D$Double());\n"+
   "println(new Line2D.Double());\n"+
   "println(new Double());\n"+
   "println(new Line2D$Double().class);\n"+
   "println(new Line2D.Double().class);\n"+
   "println(new Double().class);\n"+
   "HashMap.dump();\n"+
   "println(RGB.black);";

   public void testImport() throws Exception {
      Double d = new Double();
      System.err.println(d);
      System.err.println(new Line2D.Double());
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      Executable executable = compiler.compile(SOURCE);
      executable.execute();
   }

   public static void main(String[] list) throws Exception {
      new ImportTest().testImport();
   }
}
