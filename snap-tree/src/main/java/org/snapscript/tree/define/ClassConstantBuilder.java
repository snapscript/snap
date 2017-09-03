package org.snapscript.tree.define;

import static org.snapscript.core.ModifierType.CONSTANT;
import static org.snapscript.core.ModifierType.PUBLIC;
import static org.snapscript.core.Reserved.TYPE_CLASS;
import static org.snapscript.core.Reserved.TYPE_THIS;

import java.util.List;

import org.snapscript.core.Scope;
import org.snapscript.core.State;
import org.snapscript.core.Type;
import org.snapscript.core.Value;
import org.snapscript.core.ValueType;
import org.snapscript.core.property.ConstantPropertyBuilder;
import org.snapscript.core.property.Property;

public class ClassConstantBuilder {
   
   private final ConstantPropertyBuilder builder;
   
   public ClassConstantBuilder() {
      this.builder = new ConstantPropertyBuilder();
   }

   public void declare(Scope scope, Type type) throws Exception {
      declareConstant(scope, TYPE_THIS, type);
      declareConstant(scope, TYPE_CLASS, type, type);
   }
   
   protected void declareConstant(Scope scope, String name, Type type) throws Exception {
      List<Property> properties = type.getProperties();
      Property property = builder.createConstant(name);
      
      properties.add(property);
   }
   
   protected void declareConstant(Scope scope, String name, Type type, Object value) throws Exception {
      List<Property> properties = type.getProperties();
      Property property = builder.createConstant(name, value);
      Value constant = ValueType.getBlank(value, null, PUBLIC.mask | CONSTANT.mask);
      State state = scope.getState();

      properties.add(property);
      state.addScope(name, constant);
   }
   
   protected void declareConstant(Scope scope, String name, Type type, Type parent, Object value) throws Exception {
      List<Property> properties = type.getProperties();
      Property property = builder.createConstant(name, value, type);
      Value constant = ValueType.getBlank(value, parent, PUBLIC.mask | CONSTANT.mask);
      State state = scope.getState();

      properties.add(property);
      state.addScope(name, constant);
   }
}