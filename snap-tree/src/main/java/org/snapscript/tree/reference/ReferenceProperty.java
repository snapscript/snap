package org.snapscript.tree.reference;

import static org.snapscript.core.error.Reason.ACCESS;

import java.util.Set;

import org.snapscript.core.Compilation;
import org.snapscript.core.Context;
import org.snapscript.core.Evaluation;
import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.convert.proxy.ProxyWrapper;
import org.snapscript.core.error.Reason;
import org.snapscript.core.error.ErrorHandler;
import org.snapscript.core.module.Module;
import org.snapscript.core.module.Path;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.scope.Value;
import org.snapscript.core.type.Type;
import org.snapscript.core.type.TypeExtractor;
import org.snapscript.tree.NameReference;
import org.snapscript.tree.variable.VariableBinder;

public class ReferenceProperty implements Compilation {
   
   private final NameReference reference;
   
   public ReferenceProperty(Evaluation identifier) {
      this.reference = new NameReference(identifier);
   }
   
   @Override
   public Evaluation compile(Module module, Path path, int line) throws Exception {
      Scope scope = module.getScope();
      Context context = module.getContext();
      ErrorHandler handler = context.getHandler();
      ProxyWrapper wrapper = context.getWrapper();
      TypeExtractor extractor = context.getExtractor();
      String name = reference.getName(scope);
      
      return new CompileResult(extractor, handler, wrapper, name);
   }
   
   private static class CompileResult extends Evaluation {
   
      private final TypeExtractor extractor;
      private final VariableBinder binder;
      private final ErrorHandler handler;
      private final String name;
      
      public CompileResult(TypeExtractor extractor, ErrorHandler handler, ProxyWrapper wrapper, String name) {
         this.binder = new VariableBinder(handler, wrapper, name);
         this.extractor = extractor;
         this.handler = handler;
         this.name = name;
      }
      
      @Override
      public Constraint compile(Scope scope, Constraint left) throws Exception{
         Constraint constraint = binder.compile(scope, left);
         
         if(constraint.isPrivate()) {
            Type type = scope.getType();
            Type origin = left.getType(scope); // what is the callers type
            Set<Type> types = extractor.getTypes(type); // what is this scope
            
            if(!types.contains(origin)) {
               handler.handleCompileError(ACCESS, scope, origin, name);
            }
         }
         return constraint;
      } 
      
      @Override
      public Value evaluate(Scope scope, Object left) throws Exception{
         return binder.evaluate(scope, left);
      } 
   }
}