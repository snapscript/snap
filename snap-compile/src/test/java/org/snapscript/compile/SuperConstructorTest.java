package org.snapscript.compile;

public class SuperConstructorTest extends ScriptTestCase  {

   private static final String SOURCE_1 =
   "class Foo{}";      
   
   private static final String SOURCE_2 =
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
      assertScriptExecutes(SOURCE_1); // Any() default constructor
      assertScriptExecutes(SOURCE_2);
   }
}
