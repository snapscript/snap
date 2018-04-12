package org.snapscript.compile.staticanalysis;

public class PrivateAccessCompileTest extends CompileTestCase {
   
   private static final String SUCCESS_1 =
   "class Typ {\n"+
   "   private var x;\n"+
   "   new(){\n"+
   "      this.x=10;\n"+
   "   }\n"+
   "}";
   
   private static final String SUCCESS_2 =
   "class Typ {\n"+
   "   private const x;\n"+
   "   new(){\n"+
   "      this.x=10;\n"+
   "   }\n"+
   "}";
   
   private static final String SUCCESS_3 =
   "class Supr {\n"+
   "   private var x;\n"+
   "   new(a){\n"+
   "      this.x=a;\n"+
   "   }\n"+
   "}"+
   "class Typ extends Supr{\n"+
   "   new() : super(1){\n"+
   "      assert this.x == 1;\n"+
   "      this.x=10;\n"+
   "   }\n"+
   "}";

   private static final String FAILURE_1 =
   "class Typ {\n"+
   "   private func(){}\n"+
   "}"+
   "new Typ().func();\n";
   
   private static final String FAILURE_2 =
   "class Typ {\n"+
   "   private static func(){}\n"+
   "}"+
   "Typ.func();\n";   
   
   private static final String FAILURE_3 =
   "module Mod {\n"+
   "   private func(){}\n"+
   "}"+
   "Mod.func();\n"; 
   
   private static final String FAILURE_4 =
   "class Typ {\n"+
   "   private var x;\n"+
   "}"+
   "new Typ().x;\n"; 
   
   private static final String FAILURE_5 =
   "class Typ {\n"+
   "   private static var x;\n"+
   "}"+
   "Typ.x;\n"; 
   
   private static final String FAILURE_6 =
   "class Mod {\n"+
   "   private const x;\n"+
   "}"+
   "Mod.x;\n"; 
   
   private static final String FAILURE_7 =
   "class Mod {\n"+
   "   private var x;\n"+
   "}"+
   "Mod.x;\n"; 
   
   public void testModificationOfConstants() throws Exception {
      assertCompileSuccess(SUCCESS_1); 
      assertCompileSuccess(SUCCESS_2);
      assertCompileSuccess(SUCCESS_3);
      assertCompileError(FAILURE_1, "Function 'func' is private in /default.snap at line 3"); 
      assertCompileError(FAILURE_2, "Function 'func' is private in /default.snap at line 3"); 
      assertCompileError(FAILURE_3, "Function 'func' is private in /default.snap at line 3"); 
      assertCompileError(FAILURE_4, "Property 'x' for 'default.Typ' is not accessible in /default.snap at line 3"); 
      assertCompileError(FAILURE_5, "Property 'x' for 'default.Typ' is not accessible in /default.snap at line 3"); 
      assertCompileError(FAILURE_6, "Property 'x' for 'default.Mod' is not accessible in /default.snap at line 3"); 
      assertCompileError(FAILURE_7, "Property 'x' for 'default.Mod' is not accessible in /default.snap at line 3"); 
   }

}
