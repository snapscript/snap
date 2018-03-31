package org.snapscript.tree.construct;

import static org.snapscript.core.Reserved.TYPE_CONSTRUCTOR;

import org.snapscript.core.Context;
import org.snapscript.core.Evaluation;
import org.snapscript.core.InternalStateException;
import org.snapscript.core.Module;
import org.snapscript.core.Scope;
import org.snapscript.core.Type;
import org.snapscript.core.Value;
import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.function.find.FunctionCall;
import org.snapscript.core.function.find.FunctionFinder;
import org.snapscript.tree.ArgumentList;
import org.snapscript.tree.reference.CompiledReference;

public class CreateObject extends Evaluation {
   
   private final ArgumentList arguments;
   private final Evaluation reference;

   public CreateObject(Evaluation reference, ArgumentList arguments) {
      this.reference = new CompiledReference(reference);
      this.arguments = arguments;
   }      

   @Override
   public void define(Scope scope) throws Exception {
      reference.define(scope);
      
      if(arguments != null) {
         arguments.define(scope);
      }
   }
   
   @Override
   public Constraint compile(Scope scope, Constraint left) throws Exception {
      Constraint constraint = reference.compile(scope, null);
      Type type = constraint.getType(scope);
      
      if(arguments != null) {
         arguments.compile(scope, type);
      }
      return constraint;
   }   
   
   @Override
   public Value evaluate(Scope scope, Object left) throws Exception { // this is rubbish
      Value value = reference.evaluate(scope, null);
      Type type = value.getValue();
      FunctionCall call = bind(scope, type);
           
      if(call == null){
         throw new InternalStateException("No constructor for '" + type + "'");
      }
      return call.call();
   }
   
   private FunctionCall bind(Scope scope, Type type) throws Exception {
      Module module = scope.getModule();
      Context context = module.getContext();
      FunctionFinder binder = context.getFinder();
      Class real = type.getType();
      
      if(arguments != null) {
         if(real == null) {
            Object[] array = arguments.create(scope, type); 
            return binder.bindStatic(scope, type, TYPE_CONSTRUCTOR, array);
         }
         Object[] array = arguments.create(scope); 
         return binder.bindStatic(scope, type, TYPE_CONSTRUCTOR, array);
      }
      if(real == null) {
         return binder.bindStatic(scope, type, TYPE_CONSTRUCTOR, type);
      }
      return binder.bindStatic(scope, type, TYPE_CONSTRUCTOR);
   }
}