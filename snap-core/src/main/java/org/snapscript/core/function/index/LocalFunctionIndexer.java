package org.snapscript.core.function.index;

import static org.snapscript.core.Reserved.TYPE_CONSTRUCTOR;
import static org.snapscript.core.type.Phase.DEFINE;

import org.snapscript.common.Progress;
import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.function.Function;
import org.snapscript.core.function.Invocation;
import org.snapscript.core.module.Module;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.type.Phase;
import org.snapscript.core.type.Type;

public class LocalFunctionIndexer {

   private final FunctionIndexer indexer;
   
   public LocalFunctionIndexer(FunctionIndexer indexer) {
      this.indexer = indexer;
   }
   
   public FunctionPointer index(Scope scope, String name, Type... types) throws Exception { 
      Module module = scope.getModule();   
      Type type = module.getType(name);
      
      if(type != null) {
         return resolve(type, name, types);
      }
      return null;
   }
   
   private FunctionPointer resolve(Type type, String name, Type... types) throws Exception { 
      Module module = type.getModule();
      Class real = type.getType();
      
      if(real == null) {
         Type[] array = new Type[types.length + 1];
         
         for(int i = 0; i < types.length; i++) {
            array[i + 1] = types[i];
         }
         array[0] = module.getType(Type.class);
         types = array;
      }
      return indexer.index(type, TYPE_CONSTRUCTOR, types);      
   }
   
   public FunctionPointer index(Scope scope, String name, Object... values) throws Exception {
      Module module = scope.getModule();   
      Type type = module.getType(name);
      
      if(type != null) {
         FunctionPointer pointer = resolve(type, name, values);
         
         if(pointer != null) {
            return new ConstructorPointer(pointer, type);
         }
      }
      return null;
   }
   
   private FunctionPointer resolve(Type type, String name, Object... values) throws Exception {
      Class real = type.getType();
         
      if(real == null) {
         Object[] array = new Object[values.length + 1];
         
         for(int i = 0; i < values.length; i++) {
            array[i + 1] = values[i];
         }
         array[0] = type;
         values = array;
      }
      return indexer.index(type, TYPE_CONSTRUCTOR, values);      
   }
   
   private static class ConstructorPointer implements FunctionPointer {
      
      private final ConstructorInvocation invocation;
      private final FunctionPointer pointer;
      
      public ConstructorPointer(FunctionPointer pointer, Type type) {
         this.invocation = new ConstructorInvocation(pointer, type);
         this.pointer = pointer;
      }

      @Override
      public Constraint getConstraint(Scope scope, Constraint left) {
         return pointer.getConstraint(scope, left);
      }

      @Override
      public Function getFunction() {
         return pointer.getFunction();
      }

      @Override
      public Invocation getInvocation() {
         return invocation;
      }
   }
   
   private static class ConstructorInvocation implements Invocation {
      
      private final FunctionPointer pointer;
      private final Type type;
      
      public ConstructorInvocation(FunctionPointer pointer, Type type) {
         this.pointer = pointer;
         this.type = type;
      }

      @Override
      public Object invoke(Scope scope, Object object, Object... list) throws Exception {
         Invocation invocation = pointer.getInvocation();
         Class real = type.getType();
         
         if(real == null) {
            Object[] array = new Object[list.length + 1];
            
            for(int i = 0; i < list.length; i++) {
               array[i + 1] = list[i];
            }
            array[0] = type;
            list = array;
         }
         return invocation.invoke(scope, null, list);
      }
   }
}
