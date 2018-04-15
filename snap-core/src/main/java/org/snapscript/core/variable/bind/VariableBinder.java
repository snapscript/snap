package org.snapscript.core.variable.bind;

import static org.snapscript.core.error.Reason.REFERENCE;

import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.convert.proxy.ProxyWrapper;
import org.snapscript.core.error.ErrorHandler;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.type.Type;
import org.snapscript.core.variable.Value;
import org.snapscript.core.variable.index.VariableIndexer;
import org.snapscript.core.variable.index.VariablePointer;

public class VariableBinder {

   private final VariableIndexer resolver;
   private final ErrorHandler handler;
   private final ProxyWrapper wrapper;
   private final String name;
   
   public VariableBinder(ErrorHandler handler, ProxyWrapper wrapper, String name) {
      this.resolver = new VariableIndexer(name);
      this.wrapper = wrapper;
      this.handler = handler;
      this.name = name;
   }
   
   public Constraint compile(Scope scope) throws Exception {
      VariablePointer pointer = resolver.index(scope);
      Constraint value = pointer.compile(scope, null);
      
      if(value == null) {
         handler.handleCompileError(REFERENCE, scope, name);
      }
      return value;
   }
   
   public Constraint compile(Scope scope, Constraint left) throws Exception {
      VariablePointer pointer = resolver.index(scope, left);
      Constraint value = pointer.compile(scope, left);
      Type type = left.getType(scope);
      
      if(value == null) {
         handler.handleCompileError(REFERENCE, scope, type, name);
      }
      return value;
   }
   
   public Value bind(Scope scope) throws Exception {
      VariablePointer pointer = resolver.index(scope);
      Value value = pointer.get(scope, null);
      
      if(value == null) {
         handler.handleRuntimeError(REFERENCE, scope, name);
      }
      return value;
   }
   
   public Value bind(Scope scope, Object left) throws Exception {
      Object object = wrapper.fromProxy(left); // what about double wrapping?
      VariablePointer pointer = resolver.index(scope, object);
      Value value = pointer.get(scope, object);
      
      if(value == null) {
         handler.handleRuntimeError(REFERENCE, scope, object, name);
      }
      return value;
   }
}