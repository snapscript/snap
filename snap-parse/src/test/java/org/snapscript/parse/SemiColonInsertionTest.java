package org.snapscript.parse;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import junit.framework.TestCase;

/*
http://www.ecma-international.org/ecma-262/6.0/index.html#sec-automatic-semicolon-insertion
http://www.bradoncode.com/blog/2015/08/26/javascript-semi-colon-insertion/
 */
public class SemiColonInsertionTest extends TestCase {
   
   private static final String SOURCE =
   "class  \n  Point{\n"+
   "   const x;\n"+
   "   const y;\n"+
   "   new(x,y){\n"+
   "      this.x=x;\n"+
   "      this.y=y;\n"+
   "   }\n"+
   "   draw(){\n"+
   "      println(x+','+y);\n"+
   "   }\n"+
   "   toString(){\n"+
   "      return '('+x+','+y+')';\n"+
   "   }\n"+
   "}\n"+
   "\n"+
   "class Line {\n"+
   "   const a;\n"+
   "   const b;\n"+
   "   new(a: Point, b: Point){\n"+
   "      this.a=a;\n"+
   "      this.b=b;\n"+
   "   }\n"+
   "   draw(){\n"+
   "      println(a+'->'+b);\n"+
   "   }\n"+
   "   toString(){\n"+
   "      return a+'->'+b;\n"+
   "   }\n"+
   "}\n";
   
   public void testSemiColonInsertion() throws Exception {
     // List<Token> tokens = createTokens(SOURCE, "/test.snap");
      
      //System.err.println(compressText(SOURCE));
      System.err.println(compressText(SOURCE.replace(";", "")));
      //assertFalse(tokens.isEmpty());
   }
   
   private String compressText(String text) {
      SourceProcessor sourceProcessor = new SourceProcessor(100);
      SourceCode source = sourceProcessor.process(text);
      return new String(source.getSource());
   }
   
   private List<Token> createTokens(String text, String resource) {
      List<Token> tokens = new ArrayList<Token>();
      GrammarIndexer grammarIndexer = new GrammarIndexer();
      Map<String, Grammar> grammars = new LinkedHashMap<String, Grammar>();      
      GrammarResolver grammarResolver = new GrammarResolver(grammars);
      GrammarCompiler grammarCompiler = new GrammarCompiler(grammarResolver, grammarIndexer);  
      SourceProcessor sourceProcessor = new SourceProcessor(100);
      Syntax[] language = Syntax.values();
      
      for(Syntax syntax : language) {
         String name = syntax.getName();
         String value = syntax.getGrammar();
         Grammar grammar = grammarCompiler.process(name, value);
         
         grammars.put(name, grammar);
      }
      SourceCode source = sourceProcessor.process(text);
      char[] original = source.getOriginal();
      char[] compress = source.getSource();
      short[] lines = source.getLines();
      short[]types = source.getTypes();

      TokenIndexer tokenIndexer = new TokenIndexer(grammarIndexer, resource, original, compress, lines, types);
      tokenIndexer.index(tokens);
      return tokens;
   }
}
