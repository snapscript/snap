package org.snapscript.tree.define;

import static org.snapscript.core.ModifierType.CONSTANT;
import static org.snapscript.core.ModifierType.STATIC;

import java.util.List;

import org.snapscript.core.InternalStateException;
import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.function.Accessor;
import org.snapscript.core.function.AccessorProperty;
import org.snapscript.core.function.StaticAccessor;
import org.snapscript.core.property.Property;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.type.TypeState;
import org.snapscript.core.type.Category;
import org.snapscript.core.type.Type;
import org.snapscript.core.type.TypeBody;
import org.snapscript.tree.ArgumentList;
import org.snapscript.tree.NameReference;

public class EnumValue {
   
   private final NameReference reference; 
   private final ArgumentList arguments;
   
   public EnumValue(EnumKey key) {
      this(key, null);
   }
   
   public EnumValue(EnumKey key, ArgumentList arguments) { 
      this.reference = new NameReference(key);   
      this.arguments = arguments;
   }

   public TypeState define(TypeBody body, Type type, int index) throws Exception { 
      Scope scope = type.getScope();
      String name = reference.getName(scope);
      Constraint constraint = Constraint.getConstraint(type, CONSTANT.mask);
      List<Property> properties = type.getProperties();
      Category category = type.getCategory();
      
      if(!category.isEnum()) {
         throw new InternalStateException("Type '" + type + "' is not an enum");
      }
      Accessor accessor = new StaticAccessor(body, type, name);
      Property property = new AccessorProperty(name, type, constraint, accessor, STATIC.mask | CONSTANT.mask);
      
      properties.add(property);

      return new EnumInstance(name, arguments, index);
   }
}