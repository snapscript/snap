package org.snapscript.tree.construct;

import static org.snapscript.core.Reserved.TYPE_CONSTRUCTOR;
import static org.snapscript.core.error.Reason.CONSTRUCTION;

import java.util.List;

import org.snapscript.core.Context;
import org.snapscript.core.Evaluation;
import org.snapscript.core.constraint.CompileConstraint;
import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.convert.AliasResolver;
import org.snapscript.core.error.ErrorHandler;
import org.snapscript.core.function.resolve.FunctionResolver;
import org.snapscript.core.link.ImplicitImportLoader;
import org.snapscript.core.module.Module;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.type.Type;
import org.snapscript.core.variable.Value;
import org.snapscript.tree.ArgumentList;

public class CreateObject extends Evaluation {   
   
   private final ImplicitImportLoader loader;
   private final AliasResolver resolver;
   private final ArgumentList arguments;
   private final Constraint constraint;
   private final int violation; // what modifiers are illegal

   public CreateObject(Constraint constraint, ArgumentList arguments, int violation) {
      this.constraint = new CompileConstraint(constraint);
      this.loader = new ImplicitImportLoader();
      this.resolver = new AliasResolver();
      this.violation = violation;
      this.arguments = arguments;
   }      

   @Override
   public void define(Scope scope) throws Exception {
      List<String> names = constraint.getImports(scope);
      int count = names.size();
      
      if(count > 0) {
         loader.loadImports(scope, names);
      }
      if(arguments != null) {
         arguments.define(scope);
      }
   }
   
   @Override
   public Constraint compile(Scope scope, Constraint left) throws Exception {
      Type type = constraint.getType(scope);
      Type actual = resolver.resolve(type);
      int modifiers = actual.getModifiers();
      
      if((violation & modifiers) != 0) {
         Module module = actual.getModule();
         Context context = module.getContext();
         ErrorHandler handler = context.getHandler();
         
         handler.handleCompileError(CONSTRUCTION, scope, actual);
      }
      if(arguments != null) {
         arguments.compile(scope, actual);
      }
      return constraint;
   }   
   
   @Override
   public Value evaluate(Scope scope, Value left) throws Exception { 
      Type type = constraint.getType(scope);
      Type actual = resolver.resolve(type);
      
      Object v = bind(scope, actual);
      return Value.getTransient(v, constraint);     
//      if(call == null){
//         throw new InternalStateException("No constructor for '" + actual + "'");
//      }
//      return call.call(scope, actual);
   }
   
   private Object bind(Scope scope, Type type) throws Exception {
      Module module = scope.getModule();
      Context context = module.getContext();
      FunctionResolver resolver = context.getResolver();
      Class real = type.getType();
      
      if(arguments != null) {
         if(real == null) {
            Object[] array = arguments.create(scope, type); 
            return resolver.resolveStatic(scope, type, TYPE_CONSTRUCTOR, array).invoke(scope, null, array);
         }
         Object[] array = arguments.create(scope); 
         return resolver.resolveStatic(scope, type, TYPE_CONSTRUCTOR, array).invoke(scope, null, array);
      }
      if(real == null) {
         return resolver.resolveStatic(scope, type, TYPE_CONSTRUCTOR, type).invoke(scope, null, type);
      }
      return resolver.resolveStatic(scope, type, TYPE_CONSTRUCTOR).invoke(scope, null);
   }
}