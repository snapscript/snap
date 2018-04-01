package org.snapscript.tree.define;

import org.snapscript.core.Scope;
import org.snapscript.core.Statement;
import org.snapscript.core.Type;
import org.snapscript.core.TypeBody;
import org.snapscript.core.TypePart;
import org.snapscript.core.Allocation;

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