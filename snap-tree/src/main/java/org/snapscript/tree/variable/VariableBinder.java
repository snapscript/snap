package org.snapscript.tree.variable;

import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.convert.proxy.ProxyWrapper;
import org.snapscript.core.error.ErrorHandler;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.scope.Value;
import org.snapscript.tree.variable.pointer.VariablePointer;
import org.snapscript.tree.variable.pointer.VariablePointerResolver;

public class VariableBinder {

   private final VariablePointerResolver resolver;
   private final ErrorHandler handler;
   private final ProxyWrapper wrapper;
   private final String name;
   
   public VariableBinder(ErrorHandler handler, ProxyWrapper wrapper, String name) {
      this.resolver = new VariablePointerResolver(name);
      this.wrapper = wrapper;
      this.handler = handler;
      this.name = name;
   }
   
   public Constraint check(Scope scope) throws Exception {
      VariablePointer pointer = resolver.resolve(scope);
      Constraint value = pointer.check(scope, null);
      
      if(value == null) {
         handler.handleCompileError(scope, name);
      }
      return value;
   }
   
   public Constraint check(Scope scope, Constraint left) throws Exception {
      VariablePointer pointer = resolver.resolve(scope, left);
      Constraint value = pointer.check(scope, left);
      
      if(value == null) {
         handler.handleCompileError(scope, name);
      }
      return value;
   }
   
   public Value bind(Scope scope) throws Exception {
      VariablePointer pointer = resolver.resolve(scope);
      Value value = pointer.get(scope, null);
      
      if(value == null) {
         handler.handleRuntimeError(scope, name);
      }
      return value;
   }
   
   public Value bind(Scope scope, Object left) throws Exception {
      Object object = wrapper.fromProxy(left); // what about double wrapping?
      VariablePointer pointer = resolver.resolve(scope, object);
      Value value = pointer.get(scope, object);
      
      if(value == null) {
         handler.handleRuntimeError(scope, name);
      }
      return value;
   }
}