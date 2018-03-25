package org.snapscript.tree.define;

import org.snapscript.core.Scope;
import org.snapscript.core.Statement;
import org.snapscript.core.Type;
import org.snapscript.core.TypeFactory;
import org.snapscript.core.TypePart;

public class InnerDefinition extends TypePart {
   
   private final Statement statement;
   
   public InnerDefinition(Statement statement) {
      this.statement = statement;
   }

   @Override
   public TypeFactory create(TypeFactory factory, Type outer) throws Exception {
      Scope scope = outer.getScope();
      statement.create(scope);
      return null;
   }
   
   @Override
   public TypeFactory define(TypeFactory factory, Type outer) throws Exception {
      Scope scope = outer.getScope();
      statement.define(scope);
      return null;
   }

   @Override
   public TypeFactory compile(TypeFactory factory, Type outer) throws Exception {
      Scope scope = outer.getScope();
      statement.compile(scope);
      return null;
   }
}