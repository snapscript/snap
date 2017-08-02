package org.snapscript.parse;

public interface Grammar {   
   GrammarMatcher create(GrammarCache cache, int length);
}