package org.snapscript.parse;

public interface GrammarMatcher {
   boolean match(SyntaxBuilder builder, int depth);
}
