package org.snapscript.tree.define;

import org.snapscript.core.Result;
import org.snapscript.core.Scope;
import org.snapscript.core.Type;
import org.snapscript.core.TypeFactory;
import org.snapscript.core.function.Invocation;
import org.snapscript.core.function.InvocationBuilder;
import org.snapscript.core.function.Signature;
import org.snapscript.tree.function.ParameterExtractor;

public class SuperInvocationBuilder implements InvocationBuilder {
   
   private ParameterExtractor extractor;
   private TypeFactory factory;
   private Invocation invocation;
   private Type type;

   public SuperInvocationBuilder(TypeFactory factory, Signature signature, Type type) {
      this.extractor = new ParameterExtractor(signature);
      this.factory = factory;
      this.type = type;
   }
   
   @Override
   public Invocation create(Scope scope) throws Exception {
      if(invocation == null) {
         invocation = compile(scope);
      }
      return invocation;
   }
   
   private Invocation compile(Scope scope) throws Exception {
      Scope inner = scope.getInner();
      
      extractor.compile(inner); // count parameters
      factory.compile(inner, type); // start counting from here - BLOCK STATEMENT MAY BE COMPILED!!
     
      return new ResultConverter(factory);
   }

   private class ResultConverter implements Invocation {
      
      private final TypeFactory factory;
      
      public ResultConverter(TypeFactory factory) {
         this.factory = factory;
      }
      
      public Object invoke(Scope scope, Object object, Object... list) throws Exception {
         Type real = (Type)list[0];
         Scope inner = extractor.extract(scope, list);
         Result result = factory.execute(inner, real);
         
         return result.getValue();
      }
   }
}
