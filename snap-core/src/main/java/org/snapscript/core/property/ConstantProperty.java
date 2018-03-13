package org.snapscript.core.property;

import java.util.ArrayList;
import java.util.List;

import org.snapscript.core.Constant;
import org.snapscript.core.ConstantConstraint;
import org.snapscript.core.Constraint;
import org.snapscript.core.Type;
import org.snapscript.core.annotation.Annotation;

public class ConstantProperty implements Property<Object> {

   private final List<Annotation> annotations;
   private final Constraint constraint;
   private final Constant constant;
   private final Type type;
   private final String name;
   private final int modifiers;
   
   public ConstantProperty(String name, Type type, Type constraint, Object value, int modifiers){
      this.constraint = new ConstantConstraint(constraint);
      this.annotations = new ArrayList<Annotation>();
      this.constant = new Constant(value, type);
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