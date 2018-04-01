package org.snapscript.core.function.search;

import org.snapscript.core.type.Type;
import org.snapscript.core.convert.proxy.Delegate;
import org.snapscript.core.function.match.DelegateFunctionMatcher;
import org.snapscript.core.function.match.LocalFunctionMatcher;
import org.snapscript.core.function.match.ModuleFunctionMatcher;
import org.snapscript.core.function.match.TypeInstanceFunctionMatcher;
import org.snapscript.core.function.match.TypeStaticFunctionMatcher;
import org.snapscript.core.function.match.ValueFunctionMatcher;
import org.snapscript.core.module.Module;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.scope.Value;
import org.snapscript.core.stack.ThreadStack;
import org.snapscript.core.type.TypeExtractor;

public class FunctionSearcher { 
   
   private final DelegateFunctionMatcher delegates;
   private final TypeInstanceFunctionMatcher objects;
   private final ModuleFunctionMatcher modules;
   private final ValueFunctionMatcher values;
   private final LocalFunctionMatcher scopes;
   private final TypeStaticFunctionMatcher types;
   
   public FunctionSearcher(TypeExtractor extractor, ThreadStack stack, FunctionResolver resolver) {
      this.delegates = new DelegateFunctionMatcher(extractor, stack);
      this.objects = new TypeInstanceFunctionMatcher(extractor, resolver);
      this.modules = new ModuleFunctionMatcher(extractor, stack);
      this.types = new TypeStaticFunctionMatcher(extractor, stack);
      this.values = new ValueFunctionMatcher(stack);
      this.scopes = new LocalFunctionMatcher(stack);
   }
   
   public FunctionCall searchValue(Value value, Object... list) throws Exception { // closures
      FunctionPointer pointer = values.match(value, list);
      
      if(pointer != null) {
         return new FunctionCall(pointer, null, null, list);
      }
      return null;
   }
   
   public FunctionCall searchScope(Scope scope, String name, Type... list) throws Exception { // function variable
      FunctionPointer pointer = scopes.match(scope, name, list);
      
      if(pointer != null) {
         return new FunctionCall(pointer, scope, scope, list);
      }
      return null;
   }
   
   public FunctionCall searchScope(Scope scope, String name, Object... list) throws Exception { // function variable
      FunctionPointer pointer = scopes.match(scope, name, list);
      
      if(pointer != null) {
         return new FunctionCall(pointer, scope, scope, list);
      }
      return null;
   }

   public FunctionCall searchModule(Scope scope, Module module, String name, Type... list) throws Exception {
      FunctionPointer pointer = modules.match(module, name, list);
      
      if(pointer != null) {
         return new FunctionCall(pointer, scope, module, list);
      }
      return null;
   }
   
   public FunctionCall searchModule(Scope scope, Module module, String name, Object... list) throws Exception {
      FunctionPointer pointer = modules.match(module, name, list);
      
      if(pointer != null) {
         return new FunctionCall(pointer, scope, module, list);
      }
      return null;
   }

   public FunctionCall searchStatic(Scope scope, Type type, String name, Type... list) throws Exception {
      FunctionPointer pointer = types.match(type, name, list);
      
      if(pointer != null) {
         return new FunctionCall(pointer, scope, null, list);
      }
      return null;
   }
   
   public FunctionCall searchStatic(Scope scope, Type type, String name, Object... list) throws Exception {
      FunctionPointer pointer = types.match(type, name, list);
      
      if(pointer != null) {
         return new FunctionCall(pointer, scope, null, list);
      }
      return null;
   }
   
   public FunctionCall searchFunction(Scope scope, Type delegate, String name, Type... list) throws Exception {
      FunctionPointer pointer = delegates.match(delegate, name, list);
      
      if(pointer != null) {
         return new FunctionCall(pointer, scope, delegate, list);
      }
      return null;
   }
   
   public FunctionCall searchFunction(Scope scope, Delegate delegate, String name, Object... list) throws Exception {
      FunctionPointer pointer = delegates.match(delegate, name, list);
      
      if(pointer != null) {
         return new FunctionCall(pointer, scope, delegate, list);
      }
      return null;
   }
   
   public FunctionCall searchInstance(Scope scope, Type source, String name, Type... list) throws Exception {
      FunctionPointer pointer = objects.match(source, name, list);
      
      if(pointer != null) {
         return new FunctionCall(pointer, scope, source, list);
      }
      return null;
   }

   public FunctionCall searchInstance(Scope scope, Object source, String name, Object... list) throws Exception {
      FunctionPointer pointer = objects.match(source, name, list);
      
      if(pointer != null) {
         return new FunctionCall(pointer, scope, source, list);
      }
      return null;
   }
}