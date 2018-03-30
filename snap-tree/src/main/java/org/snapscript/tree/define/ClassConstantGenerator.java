package org.snapscript.tree.define;

import static org.snapscript.core.ModifierType.CONSTANT;
import static org.snapscript.core.ModifierType.PUBLIC;
import static org.snapscript.core.Reserved.TYPE_CLASS;
import static org.snapscript.core.Reserved.TYPE_THIS;

import java.util.List;

import org.snapscript.core.Constraint;
import org.snapscript.core.Scope;
import org.snapscript.core.State;
import org.snapscript.core.Type;
import org.snapscript.core.Value;
import org.snapscript.core.property.ConstantPropertyBuilder;
import org.snapscript.core.property.Property;

public class ClassConstantGenerator {
   
   private final ConstantPropertyBuilder builder;
   
   public ClassConstantGenerator() {
      this.builder = new ConstantPropertyBuilder();
   }

   public void generate(Scope scope, Type type) throws Exception {
      generateConstant(scope, TYPE_THIS, type, null, Constraint.getInstance(type));
      generateConstant(scope, TYPE_CLASS, type, type, Constraint.getInstance(Type.class));
   }
//   
//   protected void generateConstant(Scope scope, String name, Type type) throws Exception {
//      List<Property> properties = type.getProperties();
//      Property property = builder.createConstant(name);
//      
//      properties.add(property);
//   }
//   
//   protected void generateConstant(Scope scope, String name, Type type, Object value) throws Exception {
//      List<Property> properties = type.getProperties();
//      Property property = builder.createConstant(name, value);
//      Value constant = Value.getBlank(value, null, PUBLIC.mask | CONSTANT.mask);
//      State state = scope.getState();
//
//      properties.add(property);
//      state.add(name, constant);
//   }
   
   protected void generateConstant(Scope scope, String name, Type parent, Object value, Constraint constraint) throws Exception {
      List<Property> properties = parent.getProperties();
      Type field = constraint.getType(scope);
      Property property = builder.createConstant(name, value, parent, constraint);
      Value constant = Value.getBlank(value, field, PUBLIC.mask | CONSTANT.mask);
      State state = scope.getState();

      properties.add(property);
      state.add(name, constant);
   }
   
   
}