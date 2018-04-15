package org.snapscript.tree.define;

import org.snapscript.core.Statement;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.type.Type;
import org.snapscript.core.type.TypeBody;
import org.snapscript.core.type.TypePart;
import org.snapscript.core.type.TypeState;

public class InnerTypeDefinition extends TypePart {
   
   private final Statement statement;
   
   public InnerTypeDefinition(Statement statement) {
      this.statement = statement;
   }

   @Override
   public void create(TypeBody body, Type outer, Scope scope) throws Exception {
      statement.create(scope);
   }
   
   @Override
   public TypeState define(TypeBody body, Type outer, Scope scope) throws Exception {
      statement.define(scope);
      return new InnerTypeCompiler(statement);
   }
}