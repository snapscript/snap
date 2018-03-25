package org.snapscript.tree.variable;

import java.util.concurrent.atomic.AtomicReference;

import org.snapscript.core.Constraint;
import org.snapscript.core.Scope;
import org.snapscript.core.Type;
import org.snapscript.core.Value;
import org.snapscript.core.property.Property;
import org.snapscript.core.property.PropertyValue;

public class ObjectPointer implements VariablePointer<Object> {
   
   private final AtomicReference<Property> reference;
   private final ConstantResolver resolver;
   private final String name;
   
   public ObjectPointer(ConstantResolver resolver, String name) {
      this.reference = new AtomicReference<Property>();
      this.resolver = resolver;
      this.name = name;
   }
   
   @Override
   public Constraint check(Scope scope, Constraint left) {
      Property accessor = reference.get();
      
      if(accessor == null) {
         Type type = left.getType(scope);
         Property match = resolver.matchFromType(scope, type, name);
         
         if(match != null) {
            reference.set(match);
            return match.getConstraint();
         }
         return null;
      }
      return accessor.getConstraint();
   }
   
   @Override
   public Value get(Scope scope, Object left) {
      Property accessor = reference.get();
      
      if(accessor == null) {
         Property match = resolver.matchFromObject(scope, left, name);
         
         if(match != null) {
            reference.set(match);
            return new PropertyValue(match, left, name);
         }
         return null;
      }
      return new PropertyValue(accessor, left, name);
   }

}