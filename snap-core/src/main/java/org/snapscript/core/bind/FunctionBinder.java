package org.snapscript.core.bind;

import java.util.concurrent.Callable;

import org.snapscript.core.Module;
import org.snapscript.core.Scope;
import org.snapscript.core.Type;
import org.snapscript.core.TypeExtractor;
import org.snapscript.core.Value;
import org.snapscript.core.convert.Delegate;
import org.snapscript.core.stack.ThreadStack;

public class FunctionBinder {
   
   private final DelegateFunctionMatcher delegates;
   private final ObjectFunctionMatcher objects;
   private final ModuleFunctionMatcher modules;
   private final ValueFunctionMatcher values;
   private final ScopeFunctionMatcher scopes;
   private final TypeFunctionMatcher types;
   
   public FunctionBinder(TypeExtractor extractor, ThreadStack stack, FunctionResolver resolver) {
      this.delegates = new DelegateFunctionMatcher(extractor, stack);
      this.objects = new ObjectFunctionMatcher(extractor, resolver);
      this.modules = new ModuleFunctionMatcher(extractor, stack);
      this.types = new TypeFunctionMatcher(extractor, stack);
      this.values = new ValueFunctionMatcher(stack);
      this.scopes = new ScopeFunctionMatcher(stack);
   }
   
   public InvocationTask bindValue(Value value, Object... list) throws Exception { // closures
      FunctionCall call = values.match(value, list);
      
      if(call != null) {
         return new InvocationTask(call, null, null, list);
      }
      return null;
   }
   
   public InvocationTask bindScope(Scope scope, String name, Object... list) throws Exception { // function variable
      FunctionCall call = scopes.match(scope, name, list);
      
      if(call != null) {
         return new InvocationTask(call, scope, scope, list);
      }
      return null;
   }

   public InvocationTask bindModule(Scope scope, Module module, String name, Type... list) throws Exception {
      FunctionCall call = modules.match(module, name, list);
      
      if(call != null) {
         return new InvocationTask(call, scope, module, list);
      }
      return null;
   }
   
   public InvocationTask bindModule(Scope scope, Module module, String name, Object... list) throws Exception {
      FunctionCall call = modules.match(module, name, list);
      
      if(call != null) {
         return new InvocationTask(call, scope, module, list);
      }
      return null;
   }

   public InvocationTask bindStatic(Scope scope, Type type, String name, Type... list) throws Exception {
      FunctionCall call = types.match(type, name, list);
      
      if(call != null) {
         return new InvocationTask(call, scope, null, list);
      }
      return null;
   }
   
   public InvocationTask bindStatic(Scope scope, Type type, String name, Object... list) throws Exception {
      FunctionCall call = types.match(type, name, list);
      
      if(call != null) {
         return new InvocationTask(call, scope, null, list);
      }
      return null;
   }
   
   public InvocationTask bindFunction(Scope scope, Delegate delegate, String name, Object... list) throws Exception {
      FunctionCall call = delegates.match(delegate, name, list);
      
      if(call != null) {
         return new InvocationTask(call, scope, delegate, list);
      }
      return null;
   }
   
   public InvocationTask bindInstance(Scope scope, Type source, String name, Type... list) throws Exception {
      FunctionCall call = objects.match(source, name, list);
      
      if(call != null) {
         return new InvocationTask(call, scope, source, list);
      }
      return null;
   }

   public InvocationTask bindInstance(Scope scope, Object source, String name, Object... list) throws Exception {
      FunctionCall call = objects.match(source, name, list);
      
      if(call != null) {
         return new InvocationTask(call, scope, source, list);
      }
      return null;
   }
}