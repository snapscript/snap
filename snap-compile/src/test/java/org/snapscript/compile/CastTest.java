package org.snapscript.compile;

import junit.framework.TestCase;

public class CastTest extends TestCase {

   private static final String SOURCE_1 =
   "var builder = new StringBuilder();\n"+
   "\n"+
   "builder.append(97 as Character);\n"+   
   "builder.append(98 as Character);\n"+   
   "builder.append(99 as Character);\n"+   
   "builder.append(100 as Character);\n"+   
   "builder.append(101 as Character);\n"+   
   "builder.append(102 as Character);\n"+
   "\n"+
   "var text = builder.toString();\n"+
   "println(text);\n"+
   "assert text == 'abcdef';\n";
   
   private static final String SOURCE_2 =
   "const x = 11 as Character;\n"+
   "const y = '22.0' as Integer;\n"+
   "\n"+
   "assert x.class == Character.class;\n"+
   "assert y.class == Integer.class;\n";
         
   public void testCharacterCast() throws Exception {;
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      Executable executable = compiler.compile(SOURCE_1);
      executable.execute();
   } 
   
   public void testDeclarationCast() throws Exception {;
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      Executable executable = compiler.compile(SOURCE_2);
      executable.execute();
   } 
}
