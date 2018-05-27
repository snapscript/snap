package org.snapscript.compile.staticanalysis;

import org.snapscript.compile.ClassPathCompilerBuilder;
import org.snapscript.compile.Compiler;
import org.snapscript.compile.verify.VerifyException;

public class ClassHierarchyFailureTest extends CompileTestCase {
   
   private static final String SOURCE_1 = 
   "class X{\n"+
   "   new(a, b){}\n"+
   "}\n"+
   "class Y extends X{\n"+
   "   new(a){}\n"+
   "}\n";       

   private static final String SOURCE_2 = 
   "class X{\n"+
   "   new(a, b){}\n"+
   "}\n"+
   "class Y extends X {\n"+
   "   new(a): super(a){}\n"+
   "}\n";
 
   private static final String SOURCE_3 =
   "class X{\n"+
   "   new(a: String, b: Date){}\n"+
   "}\n"+
   "class Y extends X{\n"+
   "   new(a): super('a', 11){}\n"+
   "}\n";
   
   private static final String SOURCE_4 = 
   "import awt.event.KeyListener;\n"+
   "import awt.event.KeyEvent;\n"+
   "\n"+
   "class GameListener extends KeyListener {\n"+
   "\n"+
   "    public keyReleased(e: KeyEvent) {\n"+
   "       var key: Integer = e.getKeyCode();\n"+
   "\n"+
   "       // 'Z' is pressed.\n"+
   "        if (key == KeyEvent.VK_Z) { // pause\n"+
   "            println('Z');\n"+
   "        }\n"+
   "        // 'X' is pressed.\n"+
   "        if (key == KeyEvent.VK_X) { // resume\n"+
   "            println('X');\n"+
   "        }\n"+
   "        // '1' is pressed.\n"+
   "        if (key == KeyEvent.VK_1) {\n"+
   "            println('1');\n"+
   "        }\n"+
   "    }\n"+
   "\n"+
   "    // 'L' is pressed or held.\n"+
   "    public keyPressed(e: KeyEvent) {\n"+
   "       var key: Integer = e.getKeyCode();\n"+
   "       if (key == KeyEvent.VK_L) {\n"+
   "          println('L');\n"+
   "       }\n"+
   "    }\n"+
   "\n"+
   "}\n";
   
   private static final String SOURCE_5 = 
   "import awt.event.KeyAdapter;\n"+
   "import awt.event.KeyEvent;\n"+
   "\n"+
   "class GameListener with KeyAdapter {\n"+
   "\n"+
   "    public keyReleased(e: KeyEvent) {\n"+
   "       var key: Integer = e.getKeyCode();\n"+
   "\n"+
   "       // 'Z' is pressed.\n"+
   "        if (key == KeyEvent.VK_Z) { // pause\n"+
   "            println('Z');\n"+
   "        }\n"+
   "        // 'X' is pressed.\n"+
   "        if (key == KeyEvent.VK_X) { // resume\n"+
   "            println('X');\n"+
   "        }\n"+
   "        // '1' is pressed.\n"+
   "        if (key == KeyEvent.VK_1) {\n"+
   "            println('1');\n"+
   "        }\n"+
   "    }\n"+
   "\n"+
   "    // 'L' is pressed or held.\n"+
   "    public keyPressed(e: KeyEvent) {\n"+
   "       var key: Integer = e.getKeyCode();\n"+
   "       if (key == KeyEvent.VK_L) {\n"+
   "          println('L');\n"+
   "       }\n"+
   "    }\n"+
   "\n"+
   "}\n";
   public void testHierarchyCompilation() throws Exception {
      assertCompileError(SOURCE_1, "Function 'new(default.X)' not found for 'default.X' in /default.snap at line 4");
      assertCompileError(SOURCE_2, "Function 'new(default.X, default.Any)' not found for 'default.X' in /default.snap at line 4");
      assertCompileError(SOURCE_3, "Function 'new(default.X, lang.String, lang.Integer)' not found for 'default.X' in /default.snap at line 4");
      assertCompileError(SOURCE_4, "Invalid super class 'awt.event.KeyListener' for type 'default.GameListener' in /default.snap at line 4");
      assertCompileError(SOURCE_5, "Invalid trait 'awt.event.KeyAdapter' for type 'default.GameListener' in /default.snap at line 4");       
   }

}
