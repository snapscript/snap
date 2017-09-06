package org.snapscript.tree.define;

import org.snapscript.core.InternalStateException;
import org.snapscript.core.Scope;
import org.snapscript.core.function.Invocation;
import org.snapscript.core.function.InvocationBuilder;
import org.snapscript.core.function.Signature;
import org.snapscript.core.function.SignatureAligner;

public class InstanceInvocation implements Invocation<Scope> {

   private final InvocationBuilder builder;
   private final SignatureAligner aligner;
   private final ThisScopeBinder binder;
   private final String name;
   private final boolean trait;

   public InstanceInvocation(InvocationBuilder builder, Signature signature, String name, boolean trait) {
      this.aligner = new SignatureAligner(signature);
      this.binder = new ThisScopeBinder();
      this.builder = builder;
      this.trait = trait;
      this.name = name;
   }
   
   @Override
   public Object invoke(Scope scope, Scope instance, Object... list) throws Exception {
      if(trait) {
         throw new InternalStateException("Function '" + name + "' is abstract");
      }
      Object[] arguments = aligner.align(list); 
      Scope outer = binder.bind(scope, instance);
      Invocation invocation = builder.create(outer);
      
      return invocation.invoke(outer, instance, arguments);
   }
}