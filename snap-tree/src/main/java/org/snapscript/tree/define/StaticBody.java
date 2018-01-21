package org.snapscript.tree.define;

import org.snapscript.core.Scope;
import org.snapscript.core.Statement;
import org.snapscript.core.Type;
import org.snapscript.core.TypeFactory;

public class StaticBody extends Statement {

   private final TypeFactory factory;
   private final Type type;
   
   public StaticBody(TypeFactory factory, Type type) {
      this.factory = factory;
      this.type = type;
   }

   @Override
   public void compile(Scope scope) throws Exception {
      factory.compile(scope, type);
   }
}