package org.snapscript.compile.staticanalysis;

public class GenericConstructorCompileTest extends CompileTestCase {

   private static final String SUCCESS_1 =
   "class Foo<T: List<?>>{}\n"+
   "let x = Foo<List<String>>();\n"+
   "println(x.class);\n";
   
   private static final String SUCCESS_2 =
   "class Foo<T: Collection<?>>{}\n"+
   "let x = new Foo<Set<?>>();\n"+
   "println(x.class);\n";
   
   private static final String FAILURE_1 =
   "class Foo<T: List<?>>{}\n"+
   "let x = Foo<Locale>();\n"+
   "println(x.class);\n";
   
   private static final String FAILURE_2 =
   "class Foo<T: List<?>>{}\n"+
   "let x = new Foo<Locale>();\n"+
   "println(x.class);\n";

   public void testConstructorGenerics() throws Exception {
      assertCompileSuccess(SUCCESS_1);
      assertCompileSuccess(SUCCESS_2);
      assertCompileError(FAILURE_1, "Generic parameter 'T' does not match 'util.List<lang.Object>' in /default.snap at line 2");
      assertCompileError(FAILURE_2, "Generic parameter 'T' does not match 'util.List<lang.Object>' in /default.snap at line 2");           
   }
}
