package org.snapscript.tree.define;

import org.snapscript.core.Statement;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.type.Type;
import org.snapscript.core.type.Allocation;
import org.snapscript.core.type.TypeBody;
import org.snapscript.core.type.TypePart;

public class InnerDefinition extends TypePart {
   
   private final Statement statement;
   
   public InnerDefinition(Statement statement) {
      this.statement = statement;
   }

   @Override
   public void create(TypeBody body, Type outer, Scope scope) throws Exception {
      statement.create(scope);
   }
   
   @Override
   public Allocation define(TypeBody body, Type outer, Scope scope) throws Exception {
      statement.define(scope);
      return null;
   }

   @Override
   public void compile(TypeBody body, Type outer, Scope scope) throws Exception {
      statement.compile(scope);
   }
}