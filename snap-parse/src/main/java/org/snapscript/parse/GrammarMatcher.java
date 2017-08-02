package org.snapscript.parse;

public interface GrammarMatcher {
   boolean check(SyntaxChecker checker, int depth);
   boolean build(SyntaxBuilder builder, int depth);
}