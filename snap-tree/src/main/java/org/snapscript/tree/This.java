package org.snapscript.tree;

import static org.snapscript.core.ModifierType.CONSTANT;

import org.snapscript.core.Evaluation;
import org.snapscript.core.ThisBinder;
import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.type.Type;
import org.snapscript.core.variable.Value;
import org.snapscript.parse.StringToken;

public class This extends Evaluation {

   private final ThisBinder binder;
   
   public This(StringToken token) {
      this.binder = new ThisBinder();
   }
   
   @Override
   public Constraint compile(Scope scope, Constraint left) throws Exception {
      Scope instance = binder.bind(scope, scope);
      Type type = instance.getType();
      
      return Constraint.getConstraint(type, CONSTANT.mask);
   }
   
   @Override
   public Value evaluate(Scope scope, Object left) throws Exception {
      Scope instance = binder.bind(scope, scope);
      Type type = instance.getType();
      Constraint constraint = Constraint.getConstraint(type);
      
      return Value.getConstant(instance, constraint);
   }  
}
