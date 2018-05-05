package org.snapscript.compile.staticanalysis;

public class GenericFunctionCompileTest extends CompileTestCase {
   
   private static final String SUCCESS_1 =
   "var m: Map<String, Integer> = {'x':1};\n"+
   "m.get('x').longValue();\n";
   
   private static final String SUCCESS_2 =
   "var m: Map<String, String> = {'x':'x'};\n"+
   "m.get('x').substring(1);\n";   
   
   private static final String SUCCESS_3 =
   "var l: List<String> = ['x'];\n"+
   "l.get(0).substring(1);\n"; 
   
   private static final String SUCCESS_4 =
   "var l: List<Integer> = [1];\n"+
   "l.get(0).doubleValue();\n";    
   
   private static final String SUCCESS_5 =
   "var l: List = [1];\n"+
   "l.get(0).doubleValue();\n";  
   
   private static final String SUCCESS_6 =
   "var m: Map = {'x':'x'};\n"+
   "m.get('x').substring(1);\n";     
         
   private static final String FAILURE_1 =
   "var m: Map<String, String> = {'x':'x'};\n"+
   "m.get('x').longValue();\n";
   
   private static final String FAILURE_2 =
   "var m: Map<String, Integer> = {'x':1};\n"+
   "m.get('x').substring(1);\n";      
   
   public void testFunctionGenerics() throws Exception {
      assertCompileSuccess(SUCCESS_1);
      assertCompileSuccess(SUCCESS_2);
      assertCompileSuccess(SUCCESS_3);
      assertCompileSuccess(SUCCESS_4); 
      assertCompileSuccess(SUCCESS_5);
      assertCompileSuccess(SUCCESS_6);       
      assertCompileError(FAILURE_1, "Function 'longValue()' not found for 'lang.String' in /default.snap at line 2");
      assertCompileError(FAILURE_2, "Function 'substring(lang.Integer)' not found for 'lang.Integer' in /default.snap at line 2");         
   }
}
