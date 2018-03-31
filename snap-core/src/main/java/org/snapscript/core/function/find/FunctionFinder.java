package org.snapscript.core.function.find;

import org.snapscript.core.Module;
import org.snapscript.core.Scope;
import org.snapscript.core.Type;
import org.snapscript.core.TypeExtractor;
import org.snapscript.core.Value;
import org.snapscript.core.convert.Delegate;
import org.snapscript.core.stack.ThreadStack;

public class FunctionFinder {
   
   private final DelegateFunctionMatcher delegates;
   private final ObjectFunctionMatcher objects;
   private final ModuleFunctionMatcher modules;
   private final ValueFunctionMatcher values;
   private final ScopeFunctionMatcher scopes;
   private final TypeFunctionMatcher types;
   
   public FunctionFinder(TypeExtractor extractor, ThreadStack stack, FunctionResolver resolver) {
      this.delegates = new DelegateFunctionMatcher(extractor, stack);
      this.objects = new ObjectFunctionMatcher(extractor, resolver);
      this.modules = new ModuleFunctionMatcher(extractor, stack);
      this.types = new TypeFunctionMatcher(extractor, stack);
      this.values = new ValueFunctionMatcher(stack);
      this.scopes = new ScopeFunctionMatcher(stack);
   }
   
   public FunctionCall bindValue(Value value, Object... list) throws Exception { // closures
      FunctionPointer pointer = values.match(value, list);
      
      if(pointer != null) {
         return new FunctionCall(pointer, null, null, list);
      }
      return null;
   }
   
   public FunctionCall bindScope(Scope scope, String name, Type... list) throws Exception { // function variable
      FunctionPointer pointer = scopes.match(scope, name, list);
      
      if(pointer != null) {
         return new FunctionCall(pointer, scope, scope, list);
      }
      return null;
   }
   
   public FunctionCall bindScope(Scope scope, String name, Object... list) throws Exception { // function variable
      FunctionPointer pointer = scopes.match(scope, name, list);
      
      if(pointer != null) {
         return new FunctionCall(pointer, scope, scope, list);
      }
      return null;
   }

   public FunctionCall bindModule(Scope scope, Module module, String name, Type... list) throws Exception {
      FunctionPointer pointer = modules.match(module, name, list);
      
      if(pointer != null) {
         return new FunctionCall(pointer, scope, module, list);
      }
      return null;
   }
   
   public FunctionCall bindModule(Scope scope, Module module, String name, Object... list) throws Exception {
      FunctionPointer pointer = modules.match(module, name, list);
      
      if(pointer != null) {
         return new FunctionCall(pointer, scope, module, list);
      }
      return null;
   }

   public FunctionCall bindStatic(Scope scope, Type type, String name, Type... list) throws Exception {
      FunctionPointer pointer = types.match(type, name, list);
      
      if(pointer != null) {
         return new FunctionCall(pointer, scope, null, list);
      }
      return null;
   }
   
   public FunctionCall bindStatic(Scope scope, Type type, String name, Object... list) throws Exception {
      FunctionPointer pointer = types.match(type, name, list);
      
      if(pointer != null) {
         return new FunctionCall(pointer, scope, null, list);
      }
      return null;
   }
   
   public FunctionCall bindFunction(Scope scope, Type delegate, String name, Type... list) throws Exception {
      FunctionPointer pointer = delegates.match(delegate, name, list);
      
      if(pointer != null) {
         return new FunctionCall(pointer, scope, delegate, list);
      }
      return null;
   }
   
   public FunctionCall bindFunction(Scope scope, Delegate delegate, String name, Object... list) throws Exception {
      FunctionPointer pointer = delegates.match(delegate, name, list);
      
      if(pointer != null) {
         return new FunctionCall(pointer, scope, delegate, list);
      }
      return null;
   }
   
   public FunctionCall bindInstance(Scope scope, Type source, String name, Type... list) throws Exception {
      FunctionPointer pointer = objects.match(source, name, list);
      
      if(pointer != null) {
         return new FunctionCall(pointer, scope, source, list);
      }
      return null;
   }

   public FunctionCall bindInstance(Scope scope, Object source, String name, Object... list) throws Exception {
      FunctionPointer pointer = objects.match(source, name, list);
      
      if(pointer != null) {
         return new FunctionCall(pointer, scope, source, list);
      }
      return null;
   }
}