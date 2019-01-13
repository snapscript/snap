package org.snapscript.compile;

import junit.framework.TestCase;

public class ExtensionTest extends ScriptTestCase {

   private static final String SOURCE_1 = 
   "var list = new File(\".\").findFiles(\".*\");\n"+
   "println(list);\n";
   
   private static final String SOURCE_2 = 
   "println(new Date().getYear());\n";
   
   private static final String SOURCE_3 = 
   "let x = URL('http://www.google.com').get().response();\n"+
   "println(x);\n"+
   "println(x);";

   private static final String SOURCE_4 =
   "File(`${System.getProperty('user.home')}/.bash_history`).forEachLine(l -> println(l));\n";

   public void testFileExtension() throws Exception {
      assertScriptExecutes(SOURCE_1);
      assertScriptExecutes(SOURCE_2);
      assertScriptExecutes(SOURCE_3);
      assertScriptExecutes(SOURCE_4);
   }  
}
