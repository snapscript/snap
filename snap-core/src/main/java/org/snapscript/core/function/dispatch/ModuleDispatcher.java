package org.snapscript.core.function.dispatch;

import static org.snapscript.core.error.Reason.INVOKE;

import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.error.ErrorHandler;
import org.snapscript.core.function.resolve.FunctionCall;
import org.snapscript.core.function.resolve.FunctionResolver;
import org.snapscript.core.module.Module;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.type.Type;
import org.snapscript.core.variable.Value;

public class ModuleDispatcher implements FunctionDispatcher<Module> {
   
   private final FunctionResolver resolver;
   private final ErrorHandler handler;
   private final String name;
   
   public ModuleDispatcher(FunctionResolver resolver, ErrorHandler handler, String name) {
      this.resolver = resolver;
      this.handler = handler;
      this.name = name;
   }
   
   @Override
   public Constraint compile(Scope scope, Constraint constraint, Type... arguments) throws Exception {
      Type type = constraint.getType(scope);
      Module module = type.getModule();
      FunctionCall call = bind(scope, module, arguments);
      
      if(call == null) {
         handler.handleCompileError(INVOKE, scope, type, name, arguments);
      }
      return call.check(constraint);    
   }

   @Override
   public Value dispatch(Scope scope, Module module, Object... arguments) throws Exception {   
      FunctionCall call = bind(scope, module, arguments);
      
      if(call == null) {
         handler.handleRuntimeError(INVOKE, scope, module, name, arguments);
      }
      return call.call();           
   }
   
   private FunctionCall bind(Scope scope, Module module, Type... arguments) throws Exception {
      Scope inner = module.getScope();
      FunctionCall call = resolver.resolveModule(inner, module, name, arguments);
      
      if(call == null) {
         return resolver.resolveInstance(inner, (Object)module, name, arguments);
      }
      return call;
   }
   
   private FunctionCall bind(Scope scope, Module module, Object... arguments) throws Exception {
      Scope inner = module.getScope();
      FunctionCall call = resolver.resolveModule(inner, module, name, arguments);
      
      if(call == null) {
         return resolver.resolveInstance(inner, (Object)module, name, arguments);
      }
      return call;
   }
}