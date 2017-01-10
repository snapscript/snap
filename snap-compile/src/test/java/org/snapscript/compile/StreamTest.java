package org.snapscript.compile;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.snapscript.core.EmptyModel;

import junit.framework.TestCase;

public class StreamTest extends TestCase{
   
   private static final String SOURCE =
   "import util.stream.Collectors;\n"+
   "var l = ['true', 'false', 'true', 'false', 'false'];\n"+
   "var s = l.stream()\n"+
   "          .map(Boolean::parseBoolean)\n"+
   "          .collect(Collectors.toList());\n"+
   "\n"+
   "println(s);\n";

   
   public void testScriptTypeDescription() throws Exception {
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      System.err.println(SOURCE);
      compiler.compile(SOURCE).execute(new EmptyModel());
   }
   
   public void testStream()throws Exception {
      List<String> l = Arrays.asList("true", "false", "true", "false", "false");         
      List<Object> s = l.stream()
               .map(Boolean::parseBoolean)
               .collect(Collectors.toList());
               
          
      System.err.println(s);
   }

}
