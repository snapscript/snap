package org.snapscript.tree;

import static org.snapscript.core.ModifierType.CONSTANT;

import org.snapscript.core.Evaluation;
import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.scope.ScopeBinder;
import org.snapscript.core.type.Type;
import org.snapscript.core.variable.Value;
import org.snapscript.parse.StringToken;

public class This extends Evaluation {

   private final ScopeBinder binder;
   
   public This(StringToken token) {
      this.binder = new ScopeBinder();
   }
   
   @Override
   public Constraint compile(Scope scope, Constraint left) throws Exception {
      Scope instance = binder.bind(scope, scope);
      Type type = instance.getType();
      
      return Constraint.getConstraint(type, CONSTANT.mask);
   }
   
   @Override
   public Value evaluate(Scope scope, Value left) throws Exception {
      Scope instance = binder.bind(scope, scope);
      Type type = instance.getType();
      Constraint constraint = Constraint.getConstraint(type, CONSTANT.mask);
      
      return Value.getConstant(instance, type, constraint);
   }  
}
