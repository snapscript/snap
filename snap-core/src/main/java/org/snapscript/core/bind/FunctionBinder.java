package org.snapscript.core.bind;

import java.util.concurrent.Callable;

import org.snapscript.core.Module;
import org.snapscript.core.Result;
import org.snapscript.core.Scope;
import org.snapscript.core.Type;
import org.snapscript.core.TypeLoader;
import org.snapscript.core.Value;
import org.snapscript.core.error.ThreadStack;

public class FunctionBinder {
   
   private final ObjectFunctionMatcher objects;
   private final ModuleFunctionMatcher modules;
   private final ValueFunctionMatcher values;
   private final ScopeFunctionMatcher scopes;
   private final TypeFunctionMatcher types;
   
   public FunctionBinder(TypeLoader loader, ThreadStack stack) {
      this.objects = new ObjectFunctionMatcher(loader, stack);
      this.modules = new ModuleFunctionMatcher(loader, stack);
      this.types = new TypeFunctionMatcher(loader, stack);
      this.values = new ValueFunctionMatcher(stack);
      this.scopes = new ScopeFunctionMatcher(stack);
   }
   
   public Callable<Result> bind(Value value, Object... list) throws Exception { // closures
      FunctionPointer call = values.match(value, list);
      
      if(call != null) {
         return new FunctionCall(call, null, null);
      }
      return null;
   }
   
   public Callable<Result> bind(Scope scope, String name, Object... list) throws Exception { // function variable
      FunctionPointer call = scopes.match(scope, name, list);
      
      if(call != null) {
         return new FunctionCall(call, scope, scope);
      }
      return null;
   }
   
   public Callable<Result> bind(Scope scope, Module module, String name, Object... list) throws Exception {
      FunctionPointer call = modules.match(module, name, list);
      
      if(call != null) {
         return new FunctionCall(call, scope, module);
      }
      return null;
   }
   
   public Callable<Result> bind(Scope scope, Type type, String name, Object... list) throws Exception {
      FunctionPointer call = types.match(type, name, list);
      
      if(call != null) {
         return new FunctionCall(call, scope, null);
      }
      return null;
   }

   public Callable<Result> bind(Scope scope, Object source, String name, Object... list) throws Exception {
      FunctionPointer call = objects.match(source, name, list);
      
      if(call != null) {
         return new FunctionCall(call, scope, source);
      }
      return null;
   }
}
