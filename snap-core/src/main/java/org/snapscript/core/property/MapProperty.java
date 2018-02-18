package org.snapscript.core.property;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.snapscript.core.ConstantConstraint;
import org.snapscript.core.Constraint;
import org.snapscript.core.Type;
import org.snapscript.core.annotation.Annotation;

public class MapProperty implements Property<Map> {

   private final Constraint constraint;
   private final Type type;
   private final String name;
   private final int modifiers;
   
   public MapProperty(String name, Type type, int modifiers){
      this.constraint = new ConstantConstraint(null);
      this.modifiers = modifiers;
      this.name = name;
      this.type = type;
   }
   
   @Override
   public List<Annotation> getAnnotations(){
      return Collections.emptyList();
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
   public Object getValue(Map source) {
      return source.get(name);
   }

   @Override
   public void setValue(Map source, Object value) {
      source.put(name, value);
   }
   
   @Override
   public String toString(){
      return name;
   }
}