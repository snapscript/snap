package org.snapscript.parse;

import junit.framework.TestCase;

public class SyntaxParserTest extends TestCase {
   
   private static final String SOURCE =
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
   
   public void testSyntaxParser() throws Exception {
      System.err.println(SOURCE);
      SyntaxCompiler builder = new SyntaxCompiler();
      SyntaxParser parser = builder.compile();
      LexerBuilder.print(parser, SOURCE, "script");
   }

}
