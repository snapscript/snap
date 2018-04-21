package org.snapscript.core.property;

import static java.util.Collections.EMPTY_LIST;
import static org.snapscript.core.ModifierType.CONSTANT;
import static org.snapscript.core.ModifierType.STATIC;
import static org.snapscript.core.Reserved.TYPE_THIS;

import java.util.List;

import org.snapscript.core.InternalStateException;
import org.snapscript.core.type.Type;
import org.snapscript.core.annotation.Annotation;
import org.snapscript.core.constraint.DeclarationConstraint;
import org.snapscript.core.constraint.Constraint;

public class ThisProperty implements Property {
   
   private final Constraint constraint;
   private final Type type;
   
   public ThisProperty(Type type) {
      this.constraint = new DeclarationConstraint(type);
      this.type = type;
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
   public Type getType() {
      return type;
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