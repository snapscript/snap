package org.snapscript.parse;

import java.util.concurrent.atomic.AtomicReference;

public class ReferenceBuilder {

   private final GrammarResolver resolver; 
   private final String name;
   private final int index;
   
   public ReferenceBuilder(GrammarResolver resolver, String name, int index) {
      this.resolver = resolver;
      this.index = index;
      this.name = name;
   }   
   
   public GrammarMatcher create(GrammarCache cache) {
      Grammar grammar = resolver.resolve(name);
      
      if(grammar == null) {
         throw new ParseException("Grammar '" + name + "' not found");
      }
      return new ReferenceMatcher(cache, grammar, name, index);
   }  
   
   private static class ReferenceMatcher implements GrammarMatcher {
      
      private final AtomicReference<GrammarMatcher> reference;
      private final GrammarCache cache;
      private final Grammar grammar;
      private final String name;
      private final int index;
      
      public ReferenceMatcher(GrammarCache cache, Grammar grammar, String name, int index) {
         this.reference = new AtomicReference<GrammarMatcher>();
         this.grammar = grammar;
         this.cache = cache;
         this.index = index;
         this.name = name;
      }  
   
      @Override
      public boolean match(SyntaxBuilder builder, int depth) {  
         GrammarMatcher matcher = reference.get();
         
         if(matcher == null) {
            matcher = grammar.create(cache);
            reference.set(matcher);
         }
         SyntaxBuilder child = builder.mark(index);   
   
         if(child != null) {
            if(matcher.match(child, 0)) {
               child.commit();
               return true;
            }
            child.reset();
         }
         return false;
      }
      
      @Override
      public String toString() {
         return String.format("<%s>", name);
      }  
   }
}
