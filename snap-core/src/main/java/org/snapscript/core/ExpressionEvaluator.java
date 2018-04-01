package org.snapscript.core;

import org.snapscript.core.scope.Model;
import org.snapscript.core.scope.Scope;

public interface ExpressionEvaluator {
   <T> T evaluate(Model model, String source) throws Exception;
   <T> T evaluate(Model model, String source, String module) throws Exception;
   <T> T evaluate(Scope scope, String source) throws Exception;
   <T> T evaluate(Scope scope, String source, String module) throws Exception;
}