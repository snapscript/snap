package org.snapscript.core.constraint;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.snapscript.core.Module;
import org.snapscript.core.Scope;
import org.snapscript.core.Type;
import org.snapscript.core.Value;

public abstract class Constraint {

   public static final Constraint NONE = new IdentityConstraint(null);
   public static final Constraint INTEGER = new ClassConstraint(Integer.class);
   public static final Constraint LONG = new ClassConstraint(Long.class);
   public static final Constraint STRING = new ClassConstraint(String.class);
   public static final Constraint BOOLEAN = new ClassConstraint(Boolean.class);
   public static final Constraint SET = new ClassConstraint(Set.class);
   public static final Constraint LIST = new ClassConstraint(List.class);
   public static final Constraint MAP = new ClassConstraint(Map.class);
   public static final Constraint ITERABLE = new ClassConstraint(Iterable.class);  
   public static final Constraint TYPE = new ClassConstraint(Type.class);

   public static Constraint getNone() {
      return new IdentityConstraint(null);
   }
   
   public static Constraint getInstance(Type type) {
      return new IdentityConstraint(type);
   }
   
   public static Constraint getStatic(Type type) {
      return new StaticConstraint(type);
   }
   
   public static Constraint getModule(Module module) {
      return new ModuleConstraint(module);
   }

   public static Constraint getInstance(Class type) {
      return new ClassConstraint(type);
   }

   public static Constraint getInstance(Object value) {
      return new ObjectConstraint(value);
   }
   
   public static Constraint getInstance(Value value) {
      return new ValueConstraint(value);
   }
   
   public boolean isInstance() {
      return true;
   }
   
   public boolean isStatic() {
      return false;
   }
   
   public boolean isModule() {
      return false;
   }   
   
   public abstract Type getType(Scope scope);
}