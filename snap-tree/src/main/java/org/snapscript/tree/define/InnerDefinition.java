
package org.snapscript.tree.define;

import java.util.List;

import org.snapscript.core.Result;
import org.snapscript.core.Scope;
import org.snapscript.core.Statement;
import org.snapscript.core.Type;
import org.snapscript.core.TypeFactory;
import org.snapscript.core.property.ConstantPropertyBuilder;
import org.snapscript.core.property.Property;

public class InnerDefinition implements TypePart {
   
   private final ConstantPropertyBuilder builder;
   private final Statement statement;
   
   public InnerDefinition(Statement statement) {
      this.builder = new ConstantPropertyBuilder();
      this.statement = statement;
   }

   @Override
   public TypeFactory define(TypeFactory factory, Type outer) throws Exception {
      Scope scope = outer.getScope();
      statement.define(scope);
      return null;
   }

   @Override
   public TypeFactory compile(TypeFactory factory, Type outer) throws Exception {
      List<Property> properties = outer.getProperties();
      Scope scope = outer.getScope();
      Result result = statement.compile(scope);
      Type inner = result.getValue();
      
      if(inner != null) {
         String name = inner.getName();
         String prefix = outer.getName();
         String key = name.replace(prefix+'$', ""); // get the class name
         Property property = builder.createConstant(key, inner, inner);
         
         properties.add(property);
      }
      return null;
   }
}
