package org.snapscript.tree.variable;

import org.snapscript.core.Constraint;
import org.snapscript.core.InternalStateException;
import org.snapscript.core.Scope;
import org.snapscript.core.Type;
import org.snapscript.core.Value;
import org.snapscript.core.convert.ProxyWrapper;

public class VariableBinder {

   private final VariablePointerResolver resolver;
   private final ProxyWrapper wrapper;
   private final String name;
   
   public VariableBinder(ProxyWrapper wrapper, String name) {
      this.resolver = new VariablePointerResolver(name);
      this.wrapper = wrapper;
      this.name = name;
   }
   
   public Constraint check(Scope scope) throws Exception {
      VariablePointer pointer = resolver.resolve(scope);
      Constraint value = pointer.check(scope, null);
      
      if(value == null) {
         throw new InternalStateException("Could not resolve '" + name +"' in scope");
      }
      return value;
   }
   
   public Constraint check(Scope scope, Type left) throws Exception {
      VariablePointer pointer = resolver.resolve(scope, left);
      Constraint value = pointer.check(scope, left);
      
      if(value == null) {
         throw new InternalStateException("Could not resolve '" + name +"' in scope");
      }
      return value;
   }
   
   public Value bind(Scope scope) throws Exception {
      VariablePointer pointer = resolver.resolve(scope);
      Value value = pointer.get(scope, null);
      
      if(value == null) {
         throw new InternalStateException("Could not resolve '" + name +"' in scope");
      }
      return value;
   }
   
   public Value bind(Scope scope, Object left) throws Exception {
      Object object = wrapper.fromProxy(left); // what about double wrapping?
      VariablePointer pointer = resolver.resolve(scope, object);
      Value value = pointer.get(scope, object);
      
      if(value == null) {
         throw new InternalStateException("Could not resolve '" + name +"' in scope");
      }
      return value;
   }
}