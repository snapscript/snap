package org.snapscript.core.function.resolve;

import org.snapscript.core.convert.proxy.Delegate;
import org.snapscript.core.function.index.DelegateIndexer;
import org.snapscript.core.function.index.FunctionIndexer;
import org.snapscript.core.function.index.FunctionPointer;
import org.snapscript.core.function.index.LocalIndexer;
import org.snapscript.core.function.index.ModuleIndexer;
import org.snapscript.core.function.index.TypeInstanceIndexer;
import org.snapscript.core.function.index.TypeStaticIndexer;
import org.snapscript.core.function.index.ValueIndexer;
import org.snapscript.core.module.Module;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.stack.ThreadStack;
import org.snapscript.core.type.Type;
import org.snapscript.core.type.TypeExtractor;
import org.snapscript.core.variable.Value;

public class FunctionResolver {   
   
   private final TypeInstanceIndexer instances;
   private final TypeStaticIndexer statics;
   private final DelegateIndexer delegates;
   private final ModuleIndexer modules;
   private final ValueIndexer values;
   private final LocalIndexer scopes;
   
   public FunctionResolver(TypeExtractor extractor, ThreadStack stack, FunctionIndexer indexer) {
      this.instances = new TypeInstanceIndexer(extractor, indexer);
      this.statics = new TypeStaticIndexer(extractor, stack);
      this.delegates = new DelegateIndexer(extractor, stack);
      this.modules = new ModuleIndexer(extractor, stack);
      this.values = new ValueIndexer(stack);
      this.scopes = new LocalIndexer(stack);
   }
   
   public FunctionCall resolveValue(Value value, Object... list) throws Exception { // closures
      FunctionPointer pointer = values.index(value, list);
      
      if(pointer != null) {
         return new FunctionCall(pointer, null, null, list);
      }
      return null;
   }
   
   public FunctionCall resolveScope(Scope scope, String name, Type... list) throws Exception { // function variable
      FunctionPointer pointer = scopes.index(scope, name, list);
      
      if(pointer != null) {
         return new FunctionCall(pointer, scope, scope, list);
      }
      return null;
   }
   
   public FunctionCall resolveScope(Scope scope, String name, Object... list) throws Exception { // function variable
      FunctionPointer pointer = scopes.index(scope, name, list);
      
      if(pointer != null) {
         return new FunctionCall(pointer, scope, scope, list);
      }
      return null;
   }

   public FunctionCall resolveModule(Scope scope, Module module, String name, Type... list) throws Exception {
      FunctionPointer pointer = modules.index(module, name, list);
      
      if(pointer != null) {
         return new FunctionCall(pointer, scope, module, list);
      }
      return null;
   }
   
   public FunctionCall resolveModule(Scope scope, Module module, String name, Object... list) throws Exception {
      FunctionPointer pointer = modules.index(module, name, list);
      
      if(pointer != null) {
         return new FunctionCall(pointer, scope, module, list);
      }
      return null;
   }

   public FunctionCall resolveStatic(Scope scope, Type type, String name, Type... list) throws Exception {
      FunctionPointer pointer = statics.index(type, name, list);
      
      if(pointer != null) {
         return new FunctionCall(pointer, scope, null, list);
      }
      return null;
   }
   
   public FunctionCall resolveStatic(Scope scope, Type type, String name, Object... list) throws Exception {
      FunctionPointer pointer = statics.index(type, name, list);
      
      if(pointer != null) {
         return new FunctionCall(pointer, scope, null, list);
      }
      return null;
   }
   
   public FunctionCall resolveFunction(Scope scope, Type delegate, String name, Type... list) throws Exception {
      FunctionPointer pointer = delegates.match(delegate, name, list);
      
      if(pointer != null) {
         return new FunctionCall(pointer, scope, delegate, list);
      }
      return null;
   }
   
   public FunctionCall resolveFunction(Scope scope, Delegate delegate, String name, Object... list) throws Exception {
      FunctionPointer pointer = delegates.match(delegate, name, list);
      
      if(pointer != null) {
         return new FunctionCall(pointer, scope, delegate, list);
      }
      return null;
   }
   
   public FunctionCall resolveInstance(Scope scope, Type source, String name, Type... list) throws Exception {
      FunctionPointer pointer = instances.index(source, name, list);
      
      if(pointer != null) {
         return new FunctionCall(pointer, scope, source, list);
      }
      return null;
   }

   public FunctionCall resolveInstance(Scope scope, Object source, String name, Object... list) throws Exception {
      FunctionPointer pointer = instances.index(source, name, list);
      
      if(pointer != null) {
         return new FunctionCall(pointer, scope, source, list);
      }
      return null;
   }
}