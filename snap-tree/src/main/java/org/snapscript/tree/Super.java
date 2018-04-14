package org.snapscript.tree;

import static org.snapscript.core.ModifierType.CONSTANT;
import static org.snapscript.core.Reserved.TYPE_THIS;

import org.snapscript.core.Context;
import org.snapscript.core.Evaluation;
import org.snapscript.core.InternalStateException;
import org.snapscript.core.ThisBinder;
import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.function.Function;
import org.snapscript.core.module.Module;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.scope.State;
import org.snapscript.core.scope.instance.Instance;
import org.snapscript.core.stack.ThreadStack;
import org.snapscript.core.type.Type;
import org.snapscript.core.variable.Value;
import org.snapscript.parse.StringToken;

public class Super extends Evaluation {

   private final ThisBinder binder;
   
   public Super(StringToken token) {
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
      Module module = scope.getModule();
      Context context = module.getContext();
      ThreadStack stack = context.getStack();
      Function function = stack.current(); // we can determine the function type
      
      if(function == null) {
         throw new InternalStateException("No enclosing function for 'super' reference");
      }
      State state = scope.getState();
      Value value = state.get(TYPE_THIS);
      
      if(value == null) {
         throw new InternalStateException("No enclosing type for 'super' reference");
      }
      Instance instance = value.getValue();
      Instance base = resolve(instance, function);
      
      if(base == null) {
         throw new InternalStateException("Illegal reference to 'super'"); // closure?
      }
      return Value.getTransient(base);
   }  
   
   private Instance resolve(Instance instance, Function function) {
      Type location = function.getType();
      Instance next = instance;
      
      while(next != null) {
         Type actual = next.getHandle();
         
         if(location == actual){
            return next.getSuper(); // return the object instance for super
         }
         next = next.getSuper(); 
      }
      return null;
   }

}