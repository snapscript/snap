package org.snapscript.parse;

import junit.framework.TestCase;

public class SyntaxParserTest extends TestCase {
   
   private static final String SOURCE_1 =
   "/* simple test class */\r\n"+
   "class Blah {\r\n"+
   "  var x;\r\n"+
   "  var y;\r\n"+
   "\r\n"+
   "  new(x,y){\r\n"+
   "    this.x=x;\r\n"+
   "    this.y=y;\r\n"+
   "  }\r\n"+
   "  test(){\r\n" +
   "    return x+y;\r\n"+
   "  }\r\n"+
   "}\r\n";

   private static final String SOURCE_2 =
   "trait Scene {}\n"+
   "class Sphere with Scene {}\n";
   
   public void testSimpleParser() throws Exception {
      System.err.println(SOURCE_1);
      SyntaxCompiler builder = new SyntaxCompiler();
      SyntaxParser parser = builder.compile();
      LexerBuilder.print(parser, SOURCE_1, "script");
   }

   public void testComplexParser() throws Exception {
      System.err.println(SOURCE_2);
      SyntaxCompiler builder = new SyntaxCompiler();
      SyntaxParser parser = builder.compile();
      LexerBuilder.print(parser, SOURCE_2, "script");
   }
}
