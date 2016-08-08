package org.snapscript.compile;

import org.snapscript.compile.Compiler;
import org.snapscript.compile.Executable;

import junit.framework.TestCase;

public class TryCatchTest extends TestCase {
   
   private static final String SOURCE=
   "try {\n"+
   "   try {\n"+
   "      throw \"this is some text 2 - catch Integer\";\n"+
   "   }catch(e: Integer){\n"+
   "      System.out.println(\"problem!!!! \"+e);\n"+
   "   }\n"+
   "}catch(e){\n"+
   "   println(e.class);\n"+
   "   System.out.println(\"caught in last block \"+e);\n"+
   "}\n";

   public void testCatchConstraints() throws Exception{
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      Executable executable = compiler.compile(SOURCE);
      executable.execute();
   }
}
