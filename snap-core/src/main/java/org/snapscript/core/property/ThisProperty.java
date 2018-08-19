package org.snapscript.core.property;

import static java.util.Collections.EMPTY_LIST;
import static org.snapscript.core.ModifierType.CONSTANT;
import static org.snapscript.core.ModifierType.STATIC;
import static org.snapscript.core.Reserved.TYPE_THIS;

import java.util.List;

import org.snapscript.core.annotation.Annotation;
import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.constraint.StaticConstraint;
import org.snapscript.core.error.InternalStateException;
import org.snapscript.core.type.Type;

public class ThisProperty implements Property {
   
   private final Constraint constraint;
   private final Type source;
   
   public ThisProperty(Type source) {
      this.constraint = new StaticConstraint(source);
      this.source = source;
   }
   
   @Override
   public List<Constraint> getGenerics() {
      return EMPTY_LIST;
   }
   
   @Override
   public List<Annotation> getAnnotations(){
      return EMPTY_LIST;
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
   public Type getSource() {
      return source;
   }

   @Override
   public String getName() {
      return TYPE_THIS;
   }

   @Override
   public int getModifiers() {
      return STATIC.mask | CONSTANT.mask;
   }

   @Override
   public Object getValue(Object source) {
      return source;
   }

   @Override
   public void setValue(Object source, Object value) {
      throw new InternalStateException("Illegal modification of constant " + TYPE_THIS);
   }

   @Override
   public String toString() {
      return TYPE_THIS;
   }
}