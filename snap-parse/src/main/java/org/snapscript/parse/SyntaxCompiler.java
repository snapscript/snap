package org.snapscript.parse;

import java.util.LinkedHashMap;
import java.util.Map;

public class SyntaxCompiler {
   
   private final Map<String, Grammar> grammars;
   private final GrammarCompiler compiler;
   private final GrammarResolver resolver;
   private final GrammarIndexer indexer;
   private final SyntaxParser parser;

   public SyntaxCompiler() {
      this.grammars = new LinkedHashMap<String, Grammar>();      
      this.resolver = new GrammarResolver(grammars);
      this.indexer = new GrammarIndexer();
      this.parser = new SyntaxParser(resolver, indexer);      
      this.compiler = new GrammarCompiler(resolver, indexer);      
   } 

   public synchronized SyntaxParser compile() {
      if(grammars.isEmpty()) { 
         Syntax[] language = Syntax.values();
         
         for(Syntax syntax : language) {
            String name = syntax.getName();
            String value = syntax.getGrammar();
            Grammar grammar = compiler.process(name, value);
            
            grammars.put(name, grammar);
         }
      }
      return parser;
   }
}
