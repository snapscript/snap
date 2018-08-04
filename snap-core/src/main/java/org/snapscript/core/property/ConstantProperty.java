package org.snapscript.core.property;

import static org.snapscript.core.ModifierType.CONSTANT;

import java.util.ArrayList;
import java.util.List;

import org.snapscript.core.Bug;
import org.snapscript.core.annotation.Annotation;
import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.type.Type;
import org.snapscript.core.variable.Constant;
import org.snapscript.core.variable.ValueData;

public class ConstantProperty implements Property<Object> {

   private final List<Annotation> annotations;
   private final Constraint constraint;
   private final Constant constant;
   private final Type source;
   private final String name;
   private final int modifiers;
   
   public ConstantProperty(String name, Type source, Constraint constraint, Object value, int modifiers){
      this.annotations = new ArrayList<Annotation>();
      this.constant = new Constant(value, source);
      this.constraint = constraint;
      this.modifiers = modifiers;
      this.source = source;
      this.name = name;
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
   public Type getHandle() {
      return null;
   }
   
   @Override
   public Type getSource(){
      return source;
   }
   
   @Override
   public String getName(){
      return name;
   }
   
   @Override
   public int getModifiers() {
      return modifiers | CONSTANT.mask;
   }
   
   @Override
   public Object getValue(Object source) {
      return constant.getValue();
   }

   @Bug
   @Override
   public void setValue(Object source, Object value) {
      constant.setData(new ValueData(value,this.source));
   }
   
   @Override
   public String toString(){
      return name;
   }
}