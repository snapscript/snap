package org.snapscript.compile;

import junit.framework.TestCase;

import org.snapscript.core.scope.EmptyModel;

public class SuperConstructorTest extends TestCase  {

   private static final String SOURCE =
   "enum Color{\n"+
   "   RED,\n"+
   "   GREEN,\n"+
   "   BLUE;\n"+
   "}\n"+
   "abstract class Shape{\n"+
   "   const id;\n"+
   "   new(id){\n"+
   "      this.id=id;\n"+
   "   }\n"+
   "   abstract draw(c: Canvas);\n"+
   "}\n"+
   "class Circle extends Shape{\n"+
   "   const color;\n"+
   "   const radius;\n"+
   "   new(id, radius):this(id, radius, Color.RED){}\n"+
   "   new(id, radius, color):super(id){\n"+
   "      this.radius=radius;\n"+
   "      this.color=color;\n"+
   "   }\n"+
   "   draw(c:Canvas){\n"+
   "      println(\"Circle ${radius} ${color}\");\n"+
   "   }\n"+
   "}\n"+
   "\n"+
   "trait Canvas{\n"+
   "}\n"+
   "const circle = new Circle('x', 11);\n"+
   "assert circle != null;\n";
   
   public void testSuperConstructor() throws Exception {
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      System.err.println(SOURCE);
      compiler.compile(SOURCE).execute(new EmptyModel());
   }
}
