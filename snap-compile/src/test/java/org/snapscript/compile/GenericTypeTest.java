package org.snapscript.compile;

import junit.framework.TestCase;

public class GenericTypeTest extends TestCase {

   private static final String SOURCE_1 =
   "class Foo<String, Foo> {\n"+
   "   new(x){}\n"+
   "   func(){\n"+
   "      return 11;\n"+
   "   }\n"+
   "}\n"+
   "assert 11 == new Foo(1).func();\n";
   
   private static final String SOURCE_2 =
   "const func = (const x: Iterable<String>, const y: (x: String)) -> x.iterator.forEachRemaining(x -> y(x));\n"+
   "func([1,2,3,4], x -> println(x));";

   public void testGenericType() throws Exception {;
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      Executable executable = compiler.compile(SOURCE_1);
      executable.execute();
   
   }   
       
   public void testGenericTypeParameter() throws Exception {;
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      Executable executable = compiler.compile(SOURCE_2);
      executable.execute();
   
   }   
}
