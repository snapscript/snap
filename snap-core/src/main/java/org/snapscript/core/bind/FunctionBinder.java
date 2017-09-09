package org.snapscript.core.bind;

import java.util.concurrent.Callable;

import org.snapscript.core.Module;
import org.snapscript.core.Scope;
import org.snapscript.core.Type;
import org.snapscript.core.TypeExtractor;
import org.snapscript.core.Value;
import org.snapscript.core.bind2.DelegateFunctionMatcher2;
import org.snapscript.core.bind2.FunctionResolver2;
import org.snapscript.core.bind2.ModuleFunctionMatcher2;
import org.snapscript.core.bind2.TypeFunctionMatcher2;
import org.snapscript.core.convert.Delegate;
import org.snapscript.core.stack.ThreadStack;

public class FunctionBinder {
   
   private final DelegateFunctionMatcher delegates;
   private final ObjectFunctionMatcher objects;
   private final ModuleFunctionMatcher modules;
   private final ValueFunctionMatcher values;
   private final ScopeFunctionMatcher scopes;
   private final TypeFunctionMatcher types;
   
   private final ModuleFunctionMatcher2 modules2;
   private final DelegateFunctionMatcher2 delegates2;
   private final TypeFunctionMatcher2 types2;
   
   public FunctionBinder(TypeExtractor extractor, ThreadStack stack, FunctionResolver resolver, FunctionResolver2 resolver2) {
      this.delegates = new DelegateFunctionMatcher(extractor, stack);
      this.objects = new ObjectFunctionMatcher(extractor, stack, resolver, resolver2);
      this.modules = new ModuleFunctionMatcher(extractor, stack);
      this.types = new TypeFunctionMatcher(extractor, stack);
      this.values = new ValueFunctionMatcher(stack);
      this.scopes = new ScopeFunctionMatcher(stack);
      
      this.modules2 = new ModuleFunctionMatcher2(extractor, stack);
      this.delegates2 = new DelegateFunctionMatcher2(extractor, stack);
      this.types2 = new TypeFunctionMatcher2(extractor, stack);
   }
   
   public Callable<Value> bind(Value value, Object... list) throws Exception { // closures
      FunctionPointer call = values.match(value, list);
      
      if(call != null) {
         return new FunctionCall(call, null, null);
      }
      return null;
   }
   
   public Callable<Value> bind(Scope scope, String name, Object... list) throws Exception { // function variable
      FunctionPointer call = scopes.match(scope, name, list);
      
      if(call != null) {
         return new FunctionCall(call, scope, scope);
      }
      return null;
   }
   
   public Callable<Value> bind(Scope scope, Module module, String name, Object... list) throws Exception {
      FunctionPointer call = modules2.match(module, name, list);
      
      if(call != null) {
         return new FunctionCall(call, scope, module);
      }
      return null;
   }
   
   public Callable<Value> bind(Scope scope, Type type, String name, Object... list) throws Exception {
      FunctionPointer call = types2.match(type, name, list);
      
      if(call != null) {
         return new FunctionCall(call, scope, null);
      }
      return null;
   }
   
   public Callable<Value> bind(Scope scope, Delegate delegate, String name, Object... list) throws Exception {
      FunctionPointer call = delegates2.match(delegate, name, list);
      
      if(call != null) {
         return new FunctionCall(call, scope, delegate);
      }
      return null;
   }

   public Callable<Value> bind(Scope scope, Object source, String name, Object... list) throws Exception {
      FunctionPointer call = objects.match(source, name, list);
      
      if(call != null) {
         return new FunctionCall(call, scope, source);
      }
      return null;
   }
}