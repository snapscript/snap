package org.snapscript.tree.define;

import org.snapscript.core.Scope;
import org.snapscript.core.Statement;
import org.snapscript.core.Type;
import org.snapscript.core.define.Initializer;

public class InnerDefinition implements TypePart {
   
   private final Statement statement;
   
   public InnerDefinition(Statement statement) {
      this.statement = statement;
   }

   @Override
   public Initializer define(Initializer initializer, Type type) throws Exception {
      Scope scope = type.getScope();
      statement.define(scope);
      return null;
   }

   @Override
   public Initializer compile(Initializer initializer, Type type) throws Exception {
      Scope scope = type.getScope();
      statement.compile(scope);
      return null;
   }
}
