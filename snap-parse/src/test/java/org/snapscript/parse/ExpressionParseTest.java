package org.snapscript.parse;

import junit.framework.TestCase;

public class ExpressionParseTest extends TestCase {

   public void testParse() throws Exception {
      SyntaxParser tree = LexerBuilder.create();

      assertNotNull(tree);

      analyze(tree, "trait X{}", "trait-definition");
      analyze(tree, "class X{const x = 11;}", "class-definition");
      analyze(tree, "trait X{const x = 11;}", "trait-definition");
      analyze(tree, "const x = 11;", "trait-constant");
      analyze(tree, "a&&y", "conditional");
      analyze(tree, "a&&y", "combination");
      analyze(tree, "Integer[][]", "constraint");
      analyze(tree, "Integer[][]", "reference");
      analyze(tree, "func = 0;", "assignment-statement");
      analyze(tree, "@Blah", "argument");
      analyze(tree, "@Blah(x: 55)", "argument");
      analyze(tree, "@Blah(x: 55, y: 'blah')", "argument");
      analyze(tree, "blah(){}", "class-function");
      analyze(tree, "@Blah", "annotation-declaration");
      analyze(tree, "@Blah()", "annotation-declaration");
      analyze(tree, "@Blah(x: 1)", "annotation-declaration");
      analyze(tree, "@Blah", "annotation-list");
      analyze(tree, "@Blah()", "annotation-list");
      analyze(tree, "@Blah(x: 1)", "annotation-list");
      analyze(tree, "@Blah@Foo", "annotation-list");
      analyze(tree, "@Blah()@Foo(x:1)", "annotation-list");
      analyze(tree, "@Blah(x: 1)@Foo", "annotation-list");
      analyze(tree, "blah(){}", "class-function");
      analyze(tree, "public blah(){}", "class-function");
      analyze(tree, "@Blah blah(){}", "class-function");
      analyze(tree, "@Blah class Boo{}", "class-definition");
      analyze(tree, "@Blah", "annotation-declaration");
      analyze(tree, "-blah", "value-operand");
      analyze(tree, "-blah", "calculation-operand");
      analyze(tree, "-blah", "assignment-expression");
      analyze(tree, "this.addr = -blah;", "assignment-statement");
      analyze(tree, "addr = \"${host}:${port}\".getBytes();", "assignment-statement");
      analyze(tree, "this.addr = \"${host}:${port}\".getBytes();", "assignment-statement");
      analyze(tree, "{this.addr = \"${host}:${port}\".getBytes();}", "compound-statement");
      analyze(tree, "{this.addr = \"${host}:${port}\".getBytes();}", "group-statement");
      analyze(tree, "new(a,b,c){this.addr = \"${host}:${port}\".getBytes();}", "class-constructor");
      analyze(tree, "class Blah with Runnable{new(a,b,c){this.addr = \"${host}:${port}\".getBytes();}}", "script");
      analyze(tree, "count = source.read(buffer)", "assignment");
      analyze(tree, "(count = source.read(buffer))", "assignment-operand");
      analyze(tree, "(count = source.read(buffer)) != -1", "conditional");
      analyze(tree, "a == b", "expression");
      analyze(tree, "a, b...", "parameter-list");
      analyze(tree, "f1 < f2 ? f1 : f2", "expression");
      analyze(tree, "x=f1 < f2 ? f1 : f2", "expression");
      analyze(tree, "x=(f1 < f2 ? f1 : f2)", "expression");
      //analyze(tree, "x+(f1 < f2 ? f1 : f2)", "expression");
      analyze(tree, "[1,2,3]", "expression"); 
      analyze(tree, "x[0]", "expression");  
      analyze(tree, "x[0][1]", "expression");  
      analyze(tree, "call()", "expression");      
      analyze(tree, "call(12)", "expression");
      analyze(tree, "call(12.0f)", "expression");   
      analyze(tree, "call(\"12\")", "expression");   
      analyze(tree, "call(a[12])", "expression");   
      analyze(tree, "call().another()", "expression");
      analyze(tree, "call().another()", "expression");  
      analyze(tree, "call(12.0f).another(\"text\")", "expression");
      analyze(tree, "document.getElementById(\"demo\")", "expression");
      analyze(tree, "document.innerHTML", "expression"); // too long of a match???? i.e it matches primary           
      //analyze(tree, "document.innerHTML", "indirect-reference"); // too long of a match???? i.e it matches primary            
      analyze(tree, "document.getElementById(\"demo\").innerHTML", "expression");
      analyze(tree, "document.getElementById(\"demo\").innerHTML=z", "expression");
      analyze(tree, "calc.sumValues(1,3,Math.max(2,g.time))", "expression");
      analyze(tree, "a.b(C.D(\" This is a [] trickey index \\\"ok\\\"\"), \"some text\",E.F.G(H.I(\"4\")))", "expression");
      analyze(tree, "method.invoke(i++, \"some text\",  g.doIt())", "expression");
      analyze(tree, "show(x)", "expression");      
      analyze(tree, "show(new Date(1,2,3))", "expression");
      analyze(tree, "x=new StringBuilder()", "expression");
      analyze(tree, "new StringBuilder(1)", "construct");
      analyze(tree, "new StringBuilder()", "construct");      
      analyze(tree, "show(1)", "expression");
      analyze(tree, "show(new Date(1,2,3),1+2, \"some text\")", "expression");
      analyze(tree, "x+=show(new Date(1,2,3),1+2, \"some text\", true, false)", "expression");
      analyze(tree, "new Date(1,2,3).doSomeWork()", "expression");
      analyze(tree, "return i;", "statement");
      analyze(tree, "return new Value(\"blah\");", "statement");        
      analyze(tree, "return new Value(\"blah\").doWork();", "statement");
      analyze(tree, "return i+(x/d);", "statement");
      analyze(tree, "return;", "statement");
      analyze(tree, "return++i;", "statement");
      analyze(tree, "t", "expression");
      analyze(tree, "t!=null", "expression");
      analyze(tree, "i>2", "expression");
      analyze(tree, "a&&y", "expression");
      analyze(tree, "a&&(y)", "expression");
      analyze(tree, "i", "conditional-operand");
      analyze(tree, "i", "comparison-operand");
      analyze(tree, "32", "comparison-operand");
      analyze(tree, "true", "comparison-operand");       
      analyze(tree, "i>32", "comparison");
      analyze(tree, "i>32", "conditional-operand");       
      analyze(tree, "(i>32)&&true", "expression");       
      analyze(tree, "i>32&&true", "expression"); 
      analyze(tree, "i>2&&t!=null", "expression");
      analyze(tree, "i>2&&(t!=null||i==3)", "expression");
      analyze(tree, "throw e;", "throw-statement");
          
   }

   private void analyze(SyntaxParser analyzer, String source, String grammar) throws Exception {
      analyze(analyzer, source, grammar, true);
   }

   private void analyze(SyntaxParser analyzer, String source, String grammar, boolean success) throws Exception {
      SyntaxNode list = analyzer.parse(null, source, grammar);

      if (list != null) {
         LexerBuilder.print(analyzer, source, grammar);
      }else {
         if(success) {
            assertTrue(false);
         } 
      }
   }

}

