package org.snapscript.core.property;

import java.util.ArrayList;
import java.util.List;

import org.snapscript.core.type.Type;
import org.snapscript.core.variable.Constant;
import org.snapscript.core.annotation.Annotation;
import org.snapscript.core.constraint.Constraint;

public class ConstantProperty implements Property<Object> {

   private final List<Annotation> annotations;
   private final Constraint constraint;
   private final Constant constant;
   private final Type type;
   private final String name;
   private final int modifiers;
   
   public ConstantProperty(String name, Type type, Constraint constraint, Object value, int modifiers){
      this.annotations = new ArrayList<Annotation>();
      this.constant = new Constant(value);
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
   public Object getValue(Object source) {
      return constant.getValue();
   }

   @Override
   public void setValue(Object source, Object value) {
      constant.setValue(value);
   }
   
   @Override
   public String toString(){
      return name;
   }
}