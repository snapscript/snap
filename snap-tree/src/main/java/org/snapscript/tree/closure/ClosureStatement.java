package org.snapscript.tree.closure;

import org.snapscript.core.Compilation;
import org.snapscript.core.Evaluation;
import org.snapscript.core.ModifierType;
import org.snapscript.core.Statement;
import org.snapscript.core.module.Module;
import org.snapscript.core.module.Path;
import org.snapscript.tree.ExpressionStatement;
import org.snapscript.tree.ModifierList;
import org.snapscript.tree.resume.AwaitReturnStatement;

public class ClosureStatement implements Compilation {

   private final ExpressionStatement expression;
   private final AwaitReturnStatement await;
   private final ModifierList modifiers;
   private final Statement statement;

   public ClosureStatement(ModifierList modifiers, Statement statement, Evaluation expression){
      this.expression = new ExpressionStatement(expression);
      this.await = new AwaitReturnStatement(expression);
      this.statement = statement;
      this.modifiers = modifiers;
   }

   @Override
   public Statement compile(Module module, Path path, int line) throws Exception {
      if(statement == null) {
         int mask = modifiers.getModifiers();

         if(ModifierType.isAsync(mask)) {
            return await.compile(module, path, line);
         }
         return expression.compile(module, path, line);
      }
      return statement;
   }
}
