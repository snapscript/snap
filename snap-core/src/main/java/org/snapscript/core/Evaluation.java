package org.snapscript.core;

public interface Evaluation{
   Value evaluate(Scope scope, Object left) throws Exception;
}