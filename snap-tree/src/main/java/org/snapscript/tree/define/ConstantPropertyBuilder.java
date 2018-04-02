package org.snapscript.tree.define;

import static org.snapscript.core.ModifierType.CONSTANT;
import static org.snapscript.core.ModifierType.STATIC;

import java.util.List;

import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.function.Accessor;
import org.snapscript.core.function.AccessorProperty;
import org.snapscript.core.function.ScopeAccessor;
import org.snapscript.core.function.StaticAccessor;
import org.snapscript.core.property.Property;
import org.snapscript.core.type.Type;
import org.snapscript.core.type.TypeBody;
import org.snapscript.tree.constraint.DeclarationConstraint;

public class ConstantPropertyBuilder {
   
   public ConstantPropertyBuilder() {
      super();
   }
   
   public Property createStaticProperty(TypeBody body, String name, Type type, Constraint constraint) {
      Constraint constant = new DeclarationConstraint(constraint, STATIC.mask | CONSTANT.mask);
      Accessor accessor = new StaticAccessor(body, type, name);
      Property property = new AccessorProperty(name, type, constant, accessor, STATIC.mask | CONSTANT.mask);
      
      if(type != null) {
         List<Property> properties = type.getProperties();
         properties.add(property);
      }
      return property;
   }

   public Property createInstanceProperty(String name, Type type, Constraint constraint) {
      Constraint constant = new DeclarationConstraint(constraint, STATIC.mask | CONSTANT.mask);
      Accessor accessor = new ScopeAccessor(name);
      Property property = new AccessorProperty(name, type, constant, accessor, CONSTANT.mask); // is this the correct type!!??
      
      if(type != null) {
         List<Property> properties = type.getProperties();
         properties.add(property);
      }
      return property;
   }
}