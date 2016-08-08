package org.snapscript.tree.define;

import static org.snapscript.core.Reserved.TYPE_CLASS;
import static org.snapscript.core.Reserved.TYPE_SUPER;
import static org.snapscript.core.Reserved.TYPE_THIS;

import java.util.List;

import org.snapscript.core.Scope;
import org.snapscript.core.State;
import org.snapscript.core.Type;
import org.snapscript.core.Value;
import org.snapscript.core.ValueType;
import org.snapscript.core.property.Property;

public class ClassConstantInitializer {
   
   private final ConstantPropertyBuilder builder;
   
   public ClassConstantInitializer() {
      this.builder = new ConstantPropertyBuilder();
   }

   public void declare(Scope scope, Type type) throws Exception {
      declareConstant(scope, TYPE_THIS, type);
      declareConstant(scope, TYPE_SUPER, type);
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
      Value constant = ValueType.getConstant(value);
      State state = scope.getState();

      properties.add(property);
      state.addConstant(name, constant);
   }
}
