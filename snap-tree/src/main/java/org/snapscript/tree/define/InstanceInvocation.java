package org.snapscript.tree.define;

import org.snapscript.core.InternalStateException;
import org.snapscript.core.ThisBinder;
import org.snapscript.core.function.Invocation;
import org.snapscript.core.function.InvocationBuilder;
import org.snapscript.core.scope.Scope;

public class InstanceInvocation implements Invocation<Scope> {

   private final InvocationBuilder builder;
   private final ThisBinder binder;
   private final String name;
   private final boolean trait;

   public InstanceInvocation(InvocationBuilder builder, String name, boolean trait) {
      this.binder = new ThisBinder();
      this.builder = builder;
      this.trait = trait;
      this.name = name;
   }
   
   @Override
   public Object invoke(Scope scope, Scope instance, Object... list) throws Exception {
      if(trait) {
         throw new InternalStateException("Function '" + name + "' is abstract");
      }
      Scope outer = binder.bind(scope, instance);
      Invocation invocation = builder.create(outer);
      
      return invocation.invoke(outer, instance, list);
   }
}