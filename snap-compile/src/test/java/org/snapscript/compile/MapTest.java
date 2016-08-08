package org.snapscript.compile;

import org.snapscript.compile.Compiler;
import org.snapscript.compile.Executable;

import junit.framework.TestCase;

public class MapTest extends TestCase {
   
   private static final String SOURCE =
   "var id = 123;\n"+
   "var date = new Date();\n"+
   "var map1 = {'id': id, 'date': date};\n"+
   "var map2 = {'id': 'id', 'date': 'date'};\n"+
   "var map3 = {id: 'id', date: 'date'};\n"+
   "var map4 = {id: id, date: date};\n"+
   "\n"+
   "println(map1+' quoted keys');\n"+
   "println(map2+' quoted values and keys');\n"+
   "println(map3+' quoted values');\n"+
   "println(map4+' nothing quoted');\n";
         

   public void testMap() throws Exception {
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      Executable executable = compiler.compile(SOURCE);
      executable.execute();
   }
}
