package org.snapscript.compile;

import junit.framework.TestCase;

import org.snapscript.core.Bug;

public class LinkedListIteratorTest extends TestCase {
   
   private static final String SOURCE =
   "class Item{\n"+
   "   var x = 0;\n"+
   "   var y = 0;\n"+
   "   new(x){\n"+
   "      this.x=x;\n"+
   "   }\n"+
   "}\n"+
   "var list = new LinkedList();\n"+
   "\n"+
   "for(var i = 0; i < 10000; i++){\n"+
   "   list.add(new Item(i));\n"+
   "}\n"+
   "var start = System.currentTimeMillis();\n"+
   "\n"+
   "try {\n"+
   "   for(var i = 0; i < 100; i++) {\n"+
   "      var it = list.iterator();\n"+
   "\n"+
   "      while(it.hasNext()){\n"+
   "         var v = it.next();\n"+
   "         v.y++;\n"+
   "      }\n"+
   "   }\n"+
   "} finally {\n"+
   "   var finish = System.currentTimeMillis();\n"+
   "   println(finish - start);\n"+
   "}\n";

   @Bug("this is slower than it should be")
   public void testListIteration() throws Exception {
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      System.err.println(SOURCE);
      compiler.compile(SOURCE).execute();
   }
}
