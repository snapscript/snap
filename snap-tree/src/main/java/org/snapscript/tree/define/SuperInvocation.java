package org.snapscript.tree.define;

import org.snapscript.core.Evaluation;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.scope.index.LocalScopeExtractor;
import org.snapscript.core.type.Type;
import org.snapscript.core.variable.Value;
import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.constraint.DeclarationConstraint;
import org.snapscript.core.function.dispatch.FunctionDispatcher;
import org.snapscript.tree.ArgumentList;
import org.snapscript.tree.NameReference;

public class SuperInvocation extends Evaluation {

   private final SuperInstanceBuilder builder;
   private final LocalScopeExtractor extractor;
   private final SuperFunctionHolder holder;
   private final NameReference reference;
   private final ArgumentList arguments;
   private final Constraint constraint;
   private final Type type;
   
   public SuperInvocation(Evaluation function, ArgumentList arguments, Type type) {
      this.extractor = new LocalScopeExtractor(true, false);
      this.reference = new NameReference(function);
      this.holder = new SuperFunctionHolder(reference, type);
      this.constraint = new DeclarationConstraint(type);
      this.builder = new SuperInstanceBuilder(type);
      this.arguments = arguments;
      this.type = type;
   }

   @Override
   public Constraint compile(Scope scope, Constraint left) throws Exception {
      FunctionDispatcher dispatcher = holder.get(scope, type);  
      
      if(arguments != null) {
         Scope outer = scope.getScope();
         Scope compound = extractor.extract(scope, outer);
         Type[] list = arguments.compile(compound, type); // arguments have no left hand side

         return dispatcher.compile(scope, constraint, list);
      }
      return dispatcher.compile(scope, constraint, type);
   }
   
   @Override
   public Value evaluate(Scope scope, Object left) throws Exception {
      Type real = scope.getType();  
      Scope instance = builder.create(scope, left);
      FunctionDispatcher dispatcher = holder.get(instance, null);  
      
      if(arguments != null) {
         Scope outer = real.getScope();
         Scope compound = extractor.extract(scope, outer);
         Object[] list = arguments.create(compound, real); // arguments have no left hand side

         return dispatcher.dispatch(instance, instance, list);
      }
      return dispatcher.dispatch(instance, instance, real);
   }
   

}