package org.snapscript.compile.staticanalysis;

import org.snapscript.compile.ScriptTestCase;

public class GenericFunctionOverrideTest extends ScriptTestCase {

   private static final String SOURCE =
    "trait Comparison<T>{\n"+
    "   compare(a: T);\n"+
    "}\n"+
    "\n"+
    "class Person with Comparison<Person> {\n"+
    "\n"+
    "   let name: String;\n"+
    "\n"+
    "   new(name: String) {\n"+
    "      this.name = name;\n"+
    "   }\n"+
    "\n"+
    "   override compare(a: Person) {\n"+
    "      return name.compareTo(a.name);\n"+
    "   }\n"+
    "}\n"+
    "let p: Person = new Person('tom');\n"+
    "let b: Person = new Person('jim');\n"+
    "\n"+
    "println(p.compare(b));\n";

   public void testGenericGunctionOverride() throws Exception {
      assertScriptExecutes(SOURCE);
   }
}
