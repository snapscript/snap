package org.snapscript.tree.reference;

import static org.snapscript.core.error.Reason.ACCESS;

import org.snapscript.core.Compilation;
import org.snapscript.core.Context;
import org.snapscript.core.Evaluation;
import org.snapscript.core.InternalStateException;
import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.convert.proxy.ProxyWrapper;
import org.snapscript.core.error.ErrorHandler;
import org.snapscript.core.module.Module;
import org.snapscript.core.module.Path;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.type.Type;
import org.snapscript.core.variable.Value;
import org.snapscript.core.variable.bind.VariableBinder;
import org.snapscript.tree.ModifierAccessVerifier;
import org.snapscript.tree.NameReference;

public class ReferenceProperty implements Compilation {
   
   private final Evaluation[] evaluations;
   private final NameReference reference;
   
   public ReferenceProperty(Evaluation identifier, Evaluation... evaluations) {
      this.reference = new NameReference(identifier);
      this.evaluations = evaluations;
   }
   
   @Override
   public Evaluation compile(Module module, Path path, int line) throws Exception {
      Scope scope = module.getScope();
      Context context = module.getContext();
      ErrorHandler handler = context.getHandler();
      ProxyWrapper wrapper = context.getWrapper();
      String name = reference.getName(scope);
      
      return new CompileResult(handler, wrapper, evaluations, name);
   }
   
   private static class CompileResult extends Evaluation {
   
      private final ModifierAccessVerifier verifier;
      private final Evaluation[] evaluations;
      private final VariableBinder binder;
      private final ErrorHandler handler;
      private final String name;
      
      public CompileResult(ErrorHandler handler, ProxyWrapper wrapper, Evaluation[] evaluations, String name) {
         this.binder = new VariableBinder(handler, wrapper, name);
         this.verifier = new ModifierAccessVerifier();
         this.evaluations = evaluations;
         this.handler = handler;
         this.name = name;
      }
      
      @Override
      public Constraint compile(Scope scope, Constraint left) throws Exception{
         Constraint result = binder.compile(scope, left);
         
         if(result.isPrivate()) {
            Type type = left.getType(scope); // what is the callers type

            if(!verifier.isAccessible(scope, type)) {
               handler.handleCompileError(ACCESS, scope, type, name);
            }
         }
         for(Evaluation evaluation : evaluations) {
            if(result == null) {
               throw new InternalStateException("Result of '" + name + "' is null"); 
            }
            result = evaluation.compile(scope, result);
         }
         return result;
      } 
      
      @Override
      public Value evaluate(Scope scope, Object left) throws Exception{
         Value value = binder.bind(scope, left);
         
         for(Evaluation evaluation : evaluations) {
            Object result = value.getValue();
            
            if(result == null) {
               throw new InternalStateException("Result of '" + name + "' is null"); 
            }
            value = evaluation.evaluate(scope, result);
         }
         return value; 
      } 
   }
}