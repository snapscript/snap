package org.snapscript.core.function.index;

import java.util.List;

import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.function.Function;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.type.Type;

public class ReturnTypeBuilder {
   
   private final Function function;
   
   public ReturnTypeBuilder(Function function) {
      this.function = function;
   }
   
   public ReturnType create(Scope scope) {
      Constraint returns = function.getConstraint();
      List<Constraint> constraints = returns.getGenerics(scope);
      String name = returns.getName(scope);
      Type declared = function.getType();
      int count = constraints.size();
      
      if(name != null || count > 0) {
         return new GenericReturnType(returns, declared);
      }
      return new StaticReturnType(returns);
   }

}
