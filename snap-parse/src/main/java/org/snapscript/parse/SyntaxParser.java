package org.snapscript.parse;

public class SyntaxParser {   
   
   private final SyntaxTreeBuilder builder;
   private final GrammarResolver resolver;
   
   public SyntaxParser(GrammarResolver resolver, GrammarIndexer indexer) {
      this.builder = new SyntaxTreeBuilder(indexer);
      this.resolver = resolver;
   }   

   public SyntaxNode parse(String resource, String expression, String name) {
      GrammarCache cache = new GrammarCache();
      
      if(expression == null) {
         throw new IllegalArgumentException("Expression for '" + resource + "' is null");
      }
      Grammar grammar = resolver.resolve(name);
      
      if(grammar == null) {
         throw new IllegalArgumentException("Grammar '" + name + "' is not defined");
      }
      GrammarMatcher matcher = grammar.create(cache);
      SyntaxTree tree = builder.create(resource, expression, name);
      SyntaxBuilder root = tree.mark();
         
      if(matcher.match(root, 0)) {
         root.commit();
         return tree.commit();
      }
      return null;
   } 
}
