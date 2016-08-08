package org.snapscript.core.property;

import java.util.ArrayList;
import java.util.List;

import org.snapscript.core.Scope;
import org.snapscript.core.Type;
import org.snapscript.core.annotation.Annotation;
import org.snapscript.core.function.Accessor;
import org.snapscript.core.function.ScopeAccessor;

public class ScopeProperty implements Property<Scope> {

   private final List<Annotation> annotations;
   private final Accessor<Scope> accessor;
   private final String name;
   private final Type type;
   private final int modifiers;
   
   public ScopeProperty(String name, Type type, int modifiers){
      this.annotations = new ArrayList<Annotation>();
      this.accessor = new ScopeAccessor(name);
      this.modifiers = modifiers;
      this.name = name;
      this.type = type;
   }
   
   @Override
   public List<Annotation> getAnnotations(){
      return annotations;
   }
   
   @Override
   public Type getType(){
      return type;
   }
   
   @Override
   public Type getConstraint() {
      return null;
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
