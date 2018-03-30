package org.snapscript.tree.define;

import org.snapscript.core.Bug;
import org.snapscript.core.Scope;
import org.snapscript.core.Type;
import org.snapscript.core.TypeFactory;
import org.snapscript.core.define.Instance;
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
   private TypeFactory factory;
   private Type type;

   public TypeInvocationBuilder(TypeFactory factory, Signature signature, Type type) {
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
      
      private final TypeFactory factory;
      
      public ResultConverter(TypeFactory factory) {
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