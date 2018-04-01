package org.snapscript.tree.define;

import org.snapscript.core.scope.Scope;
import org.snapscript.core.scope.instance.Instance;
import org.snapscript.core.type.Allocation;
import org.snapscript.core.type.Type;
import org.snapscript.core.function.Invocation;
import org.snapscript.core.function.InvocationBuilder;
import org.snapscript.core.function.Signature;
import org.snapscript.core.function.SignatureAligner;
import org.snapscript.core.result.Result;
import org.snapscript.tree.function.ParameterExtractor;

public class TypeInvocationBuilder implements InvocationBuilder {
   
   private ParameterExtractor extractor;
   private SignatureAligner aligner;
   private Invocation invocation;
   private Allocation factory;
   private Type type;

   public TypeInvocationBuilder(Allocation factory, Signature signature, Type type) {
      this.extractor = new ParameterExtractor(signature); // this seems wrong!
      this.aligner = new SignatureAligner(signature);
      this.factory = factory;
      this.type = type;
   }
   
   @Override
   public void define(Scope scope) throws Exception {
      Scope inner = scope.getStack();
      
      extractor.define(inner); // count parameters
      factory.define(inner, type); // start counting from here 
   }
   
   
   @Override
   public void compile(Scope scope) throws Exception {
      factory.compile(scope, type);
   }
   
   @Override
   public Invocation create(Scope scope) throws Exception {
      if(invocation == null) {
         factory.allocate(scope, type);
         invocation = new ResultConverter(factory);
      }
      return invocation;
   }

   private class ResultConverter implements Invocation<Instance> {
      
      private final Allocation factory;
      
      public ResultConverter(Allocation factory) {
         this.factory = factory;
      }
      
      @Override
      public Object invoke(Scope scope, Instance object, Object... list) throws Exception {
         Type real = (Type)list[0];
         Object[] arguments = aligner.align(list);
         Scope inner = extractor.extract(object, arguments);
         Result result = factory.execute(inner, real);
         
         return result.getValue();
      }
   }
}