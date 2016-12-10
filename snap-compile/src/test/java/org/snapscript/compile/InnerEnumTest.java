package org.snapscript.compile;

import junit.framework.TestCase;

public class InnerEnumTest extends TestCase {

   private static final String SOURCE = 
   "class Palatte {\n"+
   "\n"+
   "   enum Color {\n"+
   "      RED,\n"+
   "      GREEN,\n"+
   "      BLUE,\n"+
   "      YELLOW,\n"+
   "      PURPLE;\n"+
   "      toString(){\n"+
   "         return name;\n"+
   "      }\n"+
   "   }\n"+
   "\n"+
   "   class Dot {\n"+
   "      var x;\n"+
   "      var y;\n"+
   "      var radius;\n"+
   "      var color:Color;\n"+
   "\n"+
   "      new(x,y,radius,color: Color){\n"+
   "         this.x=x;\n"+
   "         this.y=y;\n"+
   "         this.radius = radius;\n"+
   "         this.color = color;\n"+
   "      }\n"+
   "\n"+
   "      draw(){\n"+
   "         println(\"x=${x} y=${y} radius=${radius} color=${color}\");\n"+
   "      }\n"+
   "   }\n"+
   "}\n"+
   "println(Palatte.Color.RED);\n";
         
   public void testInnerEnum() throws Exception {
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      System.err.println(SOURCE);
      Executable executable = compiler.compile(SOURCE);
      executable.execute();
   }
}
