package org.snapscript.tree.compile;

import static org.snapscript.core.Reserved.TYPE_THIS;

import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.function.Function;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.scope.State;
import org.snapscript.core.type.Type;
import org.snapscript.core.variable.Value;

public class TypeScopeCompiler extends ScopeCompiler{
   
   public TypeScopeCompiler() {
      super();
   }

   public Scope compile(Scope scope, Type type, Function function) throws Exception {          
      Scope outer = type.getScope();
      Scope inner = outer.getStack();
      State state = inner.getState();
      Constraint constraint = Constraint.getConstraint(type);
      Value value = Value.getConstant(scope, constraint);
      
      compileParameters(inner, function);
      compileProperties(inner, type);
      state.add(TYPE_THIS, value);
      
      return inner;
   }
}
