package org.snapscript.core.property;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.snapscript.core.type.Type;
import org.snapscript.core.annotation.Annotation;
import org.snapscript.core.constraint.DeclarationConstraint;
import org.snapscript.core.constraint.Constraint;

public class MapProperty implements Property<Map> {

   private final Constraint constraint;
   private final Type type;
   private final String name;
   private final int modifiers;
   
   public MapProperty(String name, Type type, int modifiers){
      this.constraint = new DeclarationConstraint(null);
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