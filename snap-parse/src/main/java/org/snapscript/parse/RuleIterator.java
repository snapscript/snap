package org.snapscript.parse;

public interface RuleIterator {
   boolean hasNext();
   Rule peek();
   Rule next();
}