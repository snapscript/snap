package org.snapscript.tree.define;

import static org.snapscript.core.ModifierType.CONSTANT;
import static org.snapscript.core.ModifierType.PUBLIC;
import static org.snapscript.core.Reserved.TYPE_CLASS;
import static org.snapscript.core.Reserved.TYPE_THIS;
import static org.snapscript.core.constraint.Constraint.TYPE;

import java.util.List;

import org.snapscript.core.scope.Scope;
import org.snapscript.core.scope.State;
import org.snapscript.core.scope.Value;
import org.snapscript.core.type.Type;
import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.property.ConstantPropertyBuilder;
import org.snapscript.core.property.Property;

public class ClassConstantGenerator {
   
   private final ConstantPropertyBuilder builder;
   
   public ClassConstantGenerator() {
      this.builder = new ConstantPropertyBuilder();
   }

   public void generate(Scope scope, Type type) throws Exception {
      Constraint constraint = Constraint.getVariable(type);
      
      generateConstant(scope, TYPE_THIS, type, null, constraint);
      generateConstant(scope, TYPE_CLASS, type, type, TYPE);
   }
   
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