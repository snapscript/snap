
package org.snapscript.tree.define;

import org.snapscript.core.Bug;
import org.snapscript.core.Evaluation;
import org.snapscript.core.Scope;
import org.snapscript.core.ScopeCombiner;
import org.snapscript.core.Type;
import org.snapscript.core.Value;
import org.snapscript.core.ValueType;
import org.snapscript.core.define.Instance;
import org.snapscript.tree.ArgumentList;
import org.snapscript.tree.NameReference;
import org.snapscript.tree.dispatch.InvocationBinder;
import org.snapscript.tree.dispatch.InvocationDispatcher;

public class SuperInvocation implements Evaluation {

   private final SuperInstanceBuilder builder;
   private final InvocationBinder dispatcher;
   private final NameReference reference;
   private final ArgumentList arguments;
   private final Type type;
   
   public SuperInvocation(Evaluation function, ArgumentList arguments, Type type) {
      this.builder = new SuperInstanceBuilder(type);
      this.reference = new NameReference(function);
      this.dispatcher = new InvocationBinder();
      this.arguments = arguments;
      this.type = type;
   }
   
   @Bug("we need to handle extended classes better")
   @Override
   public Value evaluate(Scope scope, Object left) throws Exception {
      Type real = scope.getType();
      String name = reference.getName(scope);   
      Scope instance = builder.create(scope, left);
      InvocationDispatcher handler = dispatcher.bind(instance, null);  
      
      if(arguments != null) {
         Scope outer = real.getScope();
         Scope compound = ScopeCombiner.combine(scope, outer);
         Value array = arguments.create(compound, real); // arguments have no left hand side
         Object[] list = array.getValue();
         Class clazz = type.getType();
         
         if(clazz != null) {
            Object[] o = new Object[list.length-1];
            System.arraycopy(list, 1, o, 0, o.length);
//            Object ob = Extender.getExtendedClass(scope, clazz, o);
//            Value v= handler.dispatch(name, o);
//            Object ob =v.getValue();
            Instance inst= scope.getModule().getContext().getProvider().create(type).createInstance(scope, real, o);
            return ValueType.getTransient(inst);
         }
         return handler.dispatch(name, list);
      }
      return handler.dispatch(name, real);
   }
   

}