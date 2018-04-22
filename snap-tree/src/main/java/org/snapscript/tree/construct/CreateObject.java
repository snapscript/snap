package org.snapscript.tree.construct;

import static org.snapscript.core.Reserved.TYPE_CONSTRUCTOR;

import org.snapscript.core.Context;
import org.snapscript.core.Evaluation;
import org.snapscript.core.InternalStateException;
import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.function.resolve.FunctionCall;
import org.snapscript.core.function.resolve.FunctionResolver;
import org.snapscript.core.module.Module;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.type.Type;
import org.snapscript.core.variable.Value;
import org.snapscript.tree.ArgumentList;
import org.snapscript.tree.constraint.CompiledConstraint;

public class CreateObject extends Evaluation {
   
   private final ArgumentList arguments;
   private final Constraint constraint;

   public CreateObject(Constraint constraint, ArgumentList arguments) {
      this.constraint = new CompiledConstraint(constraint);
      this.arguments = arguments;
   }      

   @Override
   public void define(Scope scope) throws Exception {
      if(arguments != null) {
         arguments.define(scope);
      }
   }
   
   @Override
   public Constraint compile(Scope scope, Constraint left) throws Exception {
      Type type = constraint.getType(scope);
      
      if(arguments != null) {
         arguments.compile(scope, type);
      }
      return constraint;
   }   
   
   @Override
   public Value evaluate(Scope scope, Object left) throws Exception { 
      Type type = constraint.getType(scope);
      FunctionCall call = bind(scope, type);
           
      if(call == null){
         throw new InternalStateException("No constructor for '" + type + "'");
      }
      return call.call();
   }
   
   private FunctionCall bind(Scope scope, Type type) throws Exception {
      Module module = scope.getModule();
      Context context = module.getContext();
      FunctionResolver resolver = context.getResolver();
      Class real = type.getType();
      
      if(arguments != null) {
         if(real == null) {
            Object[] array = arguments.create(scope, type); 
            return resolver.resolveStatic(scope, type, TYPE_CONSTRUCTOR, array);
         }
         Object[] array = arguments.create(scope); 
         return resolver.resolveStatic(scope, type, TYPE_CONSTRUCTOR, array);
      }
      if(real == null) {
         return resolver.resolveStatic(scope, type, TYPE_CONSTRUCTOR, type);
      }
      return resolver.resolveStatic(scope, type, TYPE_CONSTRUCTOR);
   }
}