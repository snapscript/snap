package org.snapscript.compile;

import junit.framework.TestCase;

public class ConstructorReferenceToStaticTest extends TestCase {

   private static final String SOURCE =
   "class X{\n"+
   "   static const BLAH = \"ss\";\n"+
   "   var a;\n"+
   "   var b;\n"+
   "   var c;\n"+
   "   new(a,b):this(a,b,BLAH){\n"+
   "   }\n"+
   "   new(a,b,c){\n"+
   "      this.a=a;\n"+
   "      this.b=b;\n"+
   "      this.c=c;\n"+
   "   }\n"+
   "\n"+
   "   dump(){\n"+
   "      println(\"a=${a} b=${b}\");\n"+
   "   }\n"+
   "}\n"+
   "\n"+
   "class Y extends X {\n"+
   "   static const DEFAULT_SIZE = 10;\n"+
   "   var a;\n"+
   "   new(a,b) : super(a, DEFAULT_SIZE){\n"+
   "      this.a=b;\n"+
   "   }\n"+
   "   toString(){\n"+
   "      return \"${a}, ${b}\";\n"+
   "   }\n"+
   "}\n"+
   "var y = new Y(1, 2);\n"+
   "println(y);\n";
   
   public void testConstructorStaticReference() throws Exception{
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      Executable executable = compiler.compile(SOURCE);
      long start = System.currentTimeMillis();
      
       executable.execute();
       System.err.println("time="+(System.currentTimeMillis()-start));
   }
   
   public static void main(String[] list) throws Exception {
      new ConstructorReferenceToStaticTest().testConstructorStaticReference();
   }
         
}
