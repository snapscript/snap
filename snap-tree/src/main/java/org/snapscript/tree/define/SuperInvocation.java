package org.snapscript.tree.define;

import static org.snapscript.core.scope.index.CaptureType.SUPER;
import static org.snapscript.core.variable.Value.NULL;

import org.snapscript.core.Evaluation;
import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.constraint.StaticConstraint;
import org.snapscript.core.function.Connection;
import org.snapscript.core.function.dispatch.FunctionDispatcher;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.scope.index.CaptureScopeExtractor;
import org.snapscript.core.type.Type;
import org.snapscript.core.variable.Value;
import org.snapscript.tree.ArgumentList;
import org.snapscript.tree.NameReference;

public class SuperInvocation extends Evaluation {

   private final SuperInstanceBuilder builder;
   private final CaptureScopeExtractor extractor;
   private final SuperFunctionHolder holder;
   private final NameReference reference;
   private final ArgumentList arguments;
   private final Constraint constraint;
   private final Type type;
   
   public SuperInvocation(Evaluation function, ArgumentList arguments, Type type) {
      this.extractor = new CaptureScopeExtractor(SUPER);
      this.reference = new NameReference(function);
      this.holder = new SuperFunctionHolder(reference, type);
      this.constraint = new StaticConstraint(type);
      this.builder = new SuperInstanceBuilder(type);
      this.arguments = arguments;
      this.type = type;
   }

   @Override
   public Constraint compile(Scope scope, Constraint left) throws Exception {
      Value value = Value.getTransient(type);
      FunctionDispatcher dispatcher = holder.get(scope, value);  
      
      if(arguments != null) {
         Scope outer = scope.getScope();
         Scope compound = extractor.extract(scope, outer);
         Type[] list = arguments.compile(compound, type); // arguments have no left hand side

         return dispatcher.compile(scope, constraint, list);
      }
      return dispatcher.compile(scope, constraint, type);
   }
   
   @Override
   public Value evaluate(Scope scope, Value left) throws Exception {
      Type real = scope.getType();  
      Scope instance = builder.create(scope, left);
      Value value = Value.getTransient(instance);
      FunctionDispatcher dispatcher = holder.get(instance, NULL);  
      
      if(arguments != null) {
         Scope outer = real.getScope();
         Scope compound = extractor.extract(scope, outer);
         Object[] list = arguments.create(compound, real); // arguments have no left hand side
         Connection connection = dispatcher.connect(instance, value, list);
         Object result = connection.invoke(instance, value, list);
         
         return Value.getTransient(result);
      }
      Connection connection = dispatcher.connect(instance, value, real);
      Object result = connection.invoke(instance, value, real);
      
      return Value.getTransient(result);
   }
}