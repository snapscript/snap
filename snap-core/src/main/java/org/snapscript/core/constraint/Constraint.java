package org.snapscript.core.constraint;

import static java.util.Collections.EMPTY_LIST;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.snapscript.core.module.Module;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.type.Type;
import org.snapscript.core.variable.Value;

public abstract class Constraint {

   public static final Constraint NONE = new PrimitiveConstraint(null);
   public static final Constraint NUMBER = new PrimitiveConstraint(Number.class);
   public static final Constraint INTEGER = new PrimitiveConstraint(Integer.class);
   public static final Constraint LONG = new PrimitiveConstraint(Long.class);
   public static final Constraint FLOAT = new PrimitiveConstraint(Float.class);
   public static final Constraint DOUBLE = new PrimitiveConstraint(Double.class);
   public static final Constraint SHORT = new PrimitiveConstraint(Short.class);
   public static final Constraint BYTE = new PrimitiveConstraint(Byte.class);
   public static final Constraint STRING = new PrimitiveConstraint(String.class);
   public static final Constraint BOOLEAN = new PrimitiveConstraint(Boolean.class);
   public static final Constraint SET = new PrimitiveConstraint(Set.class);
   public static final Constraint LIST = new PrimitiveConstraint(List.class);
   public static final Constraint MAP = new PrimitiveConstraint(Map.class);
   public static final Constraint ITERABLE = new PrimitiveConstraint(Iterable.class);  
   public static final Constraint TYPE = new PrimitiveConstraint(Type.class);
   public static final Constraint OBJECT = new PrimitiveConstraint(Object.class);
   
   public static Constraint getConstraint(Module module) {
      return new ModuleConstraint(module);
   }

   public static Constraint getConstraint(Type type) {
      return new TypeConstraint(type);
   }
   
   public static Constraint getConstraint(Type type, int modifiers) {
      return new TypeConstraint(type, modifiers);
   }

   public static Constraint getConstraint(Object value) {
      return new ObjectConstraint(value);
   }
   
   public static Constraint getConstraint(Value value) {
      return new ValueConstraint(value);
   }
   
   public boolean isVariable() {
      return true;
   }

   public boolean isPrivate() {
      return false;
   }
   
   public boolean isClass() {
      return false;
   }
   
   public boolean isModule() {
      return false;
   }   
   
   public boolean isConstant() {
      return false;
   }   
   
   public List<Constraint> getGenerics(Scope scope) {
      return EMPTY_LIST;
   }
   
   public String getName(Scope  scope) {
      return null;
   }
   
   public abstract Type getType(Scope scope);
}