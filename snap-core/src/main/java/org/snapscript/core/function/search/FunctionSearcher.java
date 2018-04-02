package org.snapscript.core.function.search;

import org.snapscript.core.type.Type;
import org.snapscript.core.convert.proxy.Delegate;
import org.snapscript.core.function.match.DelegateMatcher;
import org.snapscript.core.function.match.LocalMatcher;
import org.snapscript.core.function.match.ModuleMatcher;
import org.snapscript.core.function.match.TypeInstanceMatcher;
import org.snapscript.core.function.match.TypeStaticMatcher;
import org.snapscript.core.function.match.ValueMatcher;
import org.snapscript.core.module.Module;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.scope.Value;
import org.snapscript.core.stack.ThreadStack;
import org.snapscript.core.type.TypeExtractor;

public class FunctionSearcher { 
   
   private final TypeInstanceMatcher instances;
   private final TypeStaticMatcher statics;
   private final DelegateMatcher delegates;
   private final ModuleMatcher modules;
   private final ValueMatcher values;
   private final LocalMatcher scopes;
   
   public FunctionSearcher(TypeExtractor extractor, ThreadStack stack, FunctionResolver resolver) {
      this.instances = new TypeInstanceMatcher(extractor, resolver);
      this.statics = new TypeStaticMatcher(extractor, stack);
      this.delegates = new DelegateMatcher(extractor, stack);
      this.modules = new ModuleMatcher(extractor, stack);
      this.values = new ValueMatcher(stack);
      this.scopes = new LocalMatcher(stack);
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
      FunctionPointer pointer = statics.match(type, name, list);
      
      if(pointer != null) {
         return new FunctionCall(pointer, scope, null, list);
      }
      return null;
   }
   
   public FunctionCall searchStatic(Scope scope, Type type, String name, Object... list) throws Exception {
      FunctionPointer pointer = statics.match(type, name, list);
      
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
      FunctionPointer pointer = instances.match(source, name, list);
      
      if(pointer != null) {
         return new FunctionCall(pointer, scope, source, list);
      }
      return null;
   }

   public FunctionCall searchInstance(Scope scope, Object source, String name, Object... list) throws Exception {
      FunctionPointer pointer = instances.match(source, name, list);
      
      if(pointer != null) {
         return new FunctionCall(pointer, scope, source, list);
      }
      return null;
   }
}