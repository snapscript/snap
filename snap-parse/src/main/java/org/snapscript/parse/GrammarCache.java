package org.snapscript.parse;

import java.util.HashMap;
import java.util.Map;

public class GrammarCache {

   private final Map<Integer, GrammarMatcher> matchers;

   public GrammarCache() {
      this.matchers = new HashMap<Integer, GrammarMatcher>(); // this cache is no good
   }  
   
   public GrammarMatcher resolve(int index) {
      return matchers.get(index);
   }
   
   public void cache(int index, GrammarMatcher matcher) {
      matchers.put(index, matcher);
   }
}
