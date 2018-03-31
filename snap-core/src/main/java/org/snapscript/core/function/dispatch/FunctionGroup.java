package org.snapscript.core.function.dispatch;

import org.snapscript.core.Scope;
import org.snapscript.core.constraint.Constraint;

public interface FunctionGroup {   
   FunctionDispatcher get(Scope scope) throws Exception;
   FunctionDispatcher get(Scope scope, Constraint left) throws Exception;
   FunctionDispatcher get(Scope scope, Object left) throws Exception;

}