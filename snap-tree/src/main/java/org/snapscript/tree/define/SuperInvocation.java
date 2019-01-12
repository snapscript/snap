package org.snapscript.tree.define;

import static org.snapscript.core.scope.index.CaptureType.EXECUTE_SUPER;
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
import org.snapscript.tree.construct.ConstructArgumentList;

public class SuperInvocation extends Evaluation {

   private final ConstructArgumentList arguments;
   private final SuperInstanceBuilder builder;
   private final CaptureScopeExtractor extractor;
   private final SuperFunctionMatcher matcher;
   private final NameReference reference;
   private final Constraint constraint;
   private final Type type;
   
   public SuperInvocation(Evaluation function, ArgumentList arguments, Type type) {
      this.arguments = new ConstructArgumentList(arguments);
      this.extractor = new CaptureScopeExtractor(EXECUTE_SUPER);
      this.reference = new NameReference(function);
      this.matcher = new SuperFunctionMatcher(reference, type);
      this.constraint = new StaticConstraint(type);
      this.builder = new SuperInstanceBuilder(type);
      this.type = type;
   }

   @Override
   public Constraint compile(Scope scope, Constraint left) throws Exception {
      Scope outer = scope.getScope();
      FunctionDispatcher dispatcher = matcher.match(scope, constraint);  
      Scope compound = extractor.extract(scope, outer);
      Type[] list = arguments.compile(compound, type); // arguments have no left hand side

      return dispatcher.compile(scope, constraint, list);
   }
   
   @Override
   public Value evaluate(Scope scope, Value left) throws Exception {
      Type real = scope.getType();  
      Scope outer = real.getScope();
      Scope instance = builder.create(scope, left);
      Value value = Value.getTransient(instance);
      Scope compound = extractor.extract(scope, outer);
      Object[] list = arguments.create(compound, real); // arguments have no left hand side
      FunctionDispatcher dispatcher = matcher.match(instance, NULL);  
      Connection connection = dispatcher.connect(instance, value, list);
      Object result = connection.invoke(instance, value, list);
      
      return Value.getTransient(result);
   }
}