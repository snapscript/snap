package org.snapscript.core;

public interface ExpressionEvaluator {
   <T> T evaluate(Model model, String source) throws Exception;
   <T> T evaluate(Model model, String source, String module) throws Exception;
   <T> T evaluate(Scope scope, String source) throws Exception;
   <T> T evaluate(Scope scope, String source, String module) throws Exception;
}
