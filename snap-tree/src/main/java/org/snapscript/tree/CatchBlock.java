package org.snapscript.tree;

import org.snapscript.core.Statement;
import org.snapscript.tree.function.ParameterDeclaration;

public class CatchBlock {
   
   private final ParameterDeclaration declaration;
   private final Statement statement;
   
   public CatchBlock(ParameterDeclaration declaration, Statement statement) {
      this.declaration = declaration;
      this.statement = statement;
   }

   public ParameterDeclaration getDeclaration() {
      return declaration;
   }

   public Statement getStatement() {
      return statement;
   }
}