package org.snapscript.core.constraint;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.snapscript.core.module.Module;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.scope.Value;
import org.snapscript.core.type.Type;

public abstract class Constraint {

   public static final Constraint NONE = new VariableConstraint(null);
   public static final Constraint CONSTANT = new ConstantConstraint(null);
   public static final Constraint INTEGER = new ClassConstraint(Integer.class);
   public static final Constraint LONG = new ClassConstraint(Long.class);
   public static final Constraint FLOAT = new ClassConstraint(Float.class);
   public static final Constraint DOUBLE = new ClassConstraint(Double.class);
   public static final Constraint SHORT = new ClassConstraint(Short.class);
   public static final Constraint BYTE = new ClassConstraint(Byte.class);
   public static final Constraint STRING = new ClassConstraint(String.class);
   public static final Constraint BOOLEAN = new ClassConstraint(Boolean.class);
   public static final Constraint SET = new ClassConstraint(Set.class);
   public static final Constraint LIST = new ClassConstraint(List.class);
   public static final Constraint MAP = new ClassConstraint(Map.class);
   public static final Constraint ITERABLE = new ClassConstraint(Iterable.class);  
   public static final Constraint TYPE = new ClassConstraint(Type.class);
   
   public static Constraint getVariable(Type type) {
      return new VariableConstraint(type);
   }
   
   public static Constraint getStatic(Type type) {
      return new StaticConstraint(type);
   }
   
   public static Constraint getModule(Module module) {
      return new ModuleConstraint(module);
   }
   
   public static Constraint getVariable(Class type) {
      return new ClassConstraint(type);
   }

   public static Constraint getFinal(Class type) {
      return new ConstantConstraint(type);
   }

   public static Constraint getVariable(Object value) {
      return new ObjectConstraint(value);
   }
   
   public static Constraint getVariable(Value value) {
      return new ValueConstraint(value);
   }
   
   public boolean isVariable() {
      return true;
   }
   
   public boolean isStatic() {
      return false;
   }
   
   public boolean isModule() {
      return false;
   }   
   
   public boolean isConstant() {
      return false;
   }
   
   public abstract Type getType(Scope scope);
}