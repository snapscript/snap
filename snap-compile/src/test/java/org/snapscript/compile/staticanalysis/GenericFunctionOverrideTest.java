package org.snapscript.compile.staticanalysis;

import org.snapscript.compile.ScriptTestCase;

public class GenericFunctionOverrideTest extends ScriptTestCase {

   private static final String SOURCE_1 =
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

   private static final String SOURCE_2 =
   "trait Comparison<T>{\n"+
    "   compare(a: T);\n"+
    "}\n"+
    "\n"+
    "class Person with Comparison<?> {\n"+
    "\n"+
    "   let name: String;\n"+
    "\n"+
    "   new(name: String) {\n"+
    "      this.name = name;\n"+
    "   }\n"+
    "\n"+
    "   override compare(a) {\n"+
    "      return name.compareTo(a.name);\n"+
    "   }\n"+
    "}\n"+
    "let p: Person = new Person('tom');\n"+
    "let b: Person = new Person('jim');\n"+
    "\n"+
    "println(p.compare(b));\n";

   private static final String SOURCE_3 =
   "class Name {\n"+
    "   const name: String;\n"+
    "\n"+
    "   new(name: String){\n"+
    "      this.name = name;\n"+
    "   }\n"+
    "}\n"+
    "class NameComparator with Comparator<Name> {\n"+
    "\n"+
    "   override compare(a: Name, b: Name) {\n"+
    "      return a.name.compareTo(b.name);\n"+
    "   }\n"+
    "}\n"+
    "let l: List<Name> = [Name('a'), Name('b'), Name('c')];\n"+
    "let c: Comparator<Name> = NameComparator();\n"+
    "\n"+
    "Collections.sort<Name>(l, c);\n";

   private static final String SOURCE_4 =
   "class StringComparator with Comparator<String> {\n"+
    "\n"+
    "   override compare(a: String, b: String) {\n"+
    "      return a.compareTo(b);\n"+
    "   }\n"+
    "}\n"+
    "let c: Comparator<String> = new StringComparator();\n"+
    "c.compare('a', 'b');\n";


   public void testGenericGunctionOverride() throws Exception {
      assertScriptExecutes(SOURCE_1);
      assertScriptExecutes(SOURCE_2);
      assertScriptExecutes(SOURCE_3);
      assertScriptExecutes(SOURCE_4);
   }
}
