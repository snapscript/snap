package org.snapscript.compile;

import org.snapscript.compile.Compiler;
import org.snapscript.compile.Executable;

import junit.framework.TestCase;

public class TemplateInConstructorTest extends TestCase {

   private static final String SOURCE =
   "class Address with Runnable{\n"+
   "   var addr;\n"+
   "   new(host, port){\n"+
   "      this.addr = \"${host}:${port}\".getBytes();\n"+
   "   }\n"+
   "}\n"+
   "var a = new Address('localhost', 443);\n"+
   "println(a.addr);\n";   


   public void testTemplate() throws Exception {
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      Executable executable = compiler.compile(SOURCE);
      executable.execute();
   }
}
