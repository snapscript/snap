package org.snapscript.core.property;

import java.util.ArrayList;
import java.util.List;

import org.snapscript.core.scope.Scope;
import org.snapscript.core.type.Type;
import org.snapscript.core.type.annotation.Annotation;
import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.function.Accessor;
import org.snapscript.core.function.ScopeAccessor;

public class ScopeProperty implements Property<Scope> {

   private final List<Annotation> annotations;
   private final Accessor<Scope> accessor;
   private final Constraint constraint;
   private final String name;
   private final Type type;
   private final int modifiers;
   
   public ScopeProperty(String name, Type type, Constraint constraint, int modifiers){
      if(constraint == null){
         throw new IllegalStateException();
      }
      this.annotations = new ArrayList<Annotation>();
      this.accessor = new ScopeAccessor(name);
      this.constraint = constraint;
      this.modifiers = modifiers;
      this.name = name;
      this.type = type;
   }
   
   @Override
   public List<Annotation> getAnnotations(){
      return annotations;
   }
   
   @Override
   public Constraint getConstraint() {
      return constraint;
   }
   
   @Override
   public Type getType(){
      return type;
   }
   
   @Override
   public String getName(){
      return name;
   }
   
   @Override
   public int getModifiers() {
      return modifiers;
   }
   
   @Override
   public Object getValue(Scope source) {
      return accessor.getValue(source);
   }

   @Override
   public void setValue(Scope source, Object value) {
      accessor.setValue(source, value);
   }
   
   @Override
   public String toString(){
      return name;
   }
}