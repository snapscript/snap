package org.snapscript.tree.define;

import org.snapscript.core.Evaluation;
import org.snapscript.core.LocalScopeExtractor;
import org.snapscript.core.Scope;
import org.snapscript.core.Type;
import org.snapscript.core.Value;
import org.snapscript.core.dispatch.CallDispatcher;
import org.snapscript.tree.ArgumentList;
import org.snapscript.tree.NameReference;

public class SuperInvocation extends Evaluation {

   private final SuperInstanceBuilder builder;
   private final LocalScopeExtractor extractor;
   private final NameReference reference;
   private final ArgumentList arguments;
   private final SuperCallSite site;
   
   public SuperInvocation(Evaluation function, ArgumentList arguments, Type type) {
      this.extractor = new LocalScopeExtractor(true, false);
      this.reference = new NameReference(function);
      this.site = new SuperCallSite(reference, type);
      this.builder = new SuperInstanceBuilder(type);
      this.arguments = arguments;
   }
   
   @Override
   public Value evaluate(Scope scope, Object left) throws Exception {
      Type real = scope.getType();  
      Scope instance = builder.create(scope, left);
      CallDispatcher dispatcher = site.get(instance, null);  
      
      if(arguments != null) {
         Scope outer = real.getScope();
         Scope compound = extractor.extract(scope, outer);
         Object[] list = arguments.create(compound, real); // arguments have no left hand side

         return dispatcher.dispatch(instance, instance, list);
      }
      return dispatcher.dispatch(instance, instance, real);
   }
   

}