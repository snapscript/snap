
package org.snapscript.tree.define;

import org.snapscript.core.Evaluation;
import org.snapscript.core.Scope;
import org.snapscript.core.ScopeCombiner;
import org.snapscript.core.Type;
import org.snapscript.core.Value;
import org.snapscript.tree.ArgumentList;
import org.snapscript.tree.NameReference;
import org.snapscript.tree.dispatch.InvocationBinder;
import org.snapscript.tree.dispatch.InvocationDispatcher;
import org.snapscript.tree.dispatch.SuperInvocationBinder;

public class SuperInvocation implements Evaluation {

   private final SuperInstanceBuilder builder;
   private final InvocationBinder binder;
   private final NameReference reference;
   private final ArgumentList arguments;
   
   public SuperInvocation(Evaluation function, ArgumentList arguments, Type type) {
      this.binder = new SuperInvocationBinder(type);
      this.builder = new SuperInstanceBuilder(type);
      this.reference = new NameReference(function);
      this.arguments = arguments;
   }
   
   @Override
   public Value evaluate(Scope scope, Object left) throws Exception {
      Type real = scope.getType();
      String name = reference.getName(scope);   
      Scope instance = builder.create(scope, left);
      InvocationDispatcher dispatcher = binder.bind(instance, null);  
      
      if(arguments != null) {
         Scope outer = real.getScope();
         Scope compound = ScopeCombiner.combine(scope, outer);
         Value array = arguments.create(compound, real); // arguments have no left hand side
         Object[] list = array.getValue();

         return dispatcher.dispatch(name, list);
      }
      return dispatcher.dispatch(name, real);
   }
   

}