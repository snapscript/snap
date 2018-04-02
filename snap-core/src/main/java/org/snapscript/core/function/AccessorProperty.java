package org.snapscript.core.function;

import java.util.ArrayList;
import java.util.List;

import org.snapscript.core.type.Type;
import org.snapscript.core.annotation.Annotation;
import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.property.Property;

public class AccessorProperty<T> implements Property<T> {

   private final List<Annotation> annotations;
   private final Accessor<T> accessor;
   private final Constraint constraint;
   private final Type type;
   private final String name;
   private final int modifiers;
   
   public AccessorProperty(String name, Type type, Constraint constraint, Accessor<T> accessor, int modifiers){
      this.annotations = new ArrayList<Annotation>();
      this.constraint = constraint;
      this.modifiers = modifiers;
      this.accessor = accessor;
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
   public Object getValue(T source) {
      return accessor.getValue(source);
   }
   
   @Override
   public void setValue(T source, Object value) {
      accessor.setValue(source, value);;
   }
   
   @Override
   public String toString(){
      return name;
   }
}