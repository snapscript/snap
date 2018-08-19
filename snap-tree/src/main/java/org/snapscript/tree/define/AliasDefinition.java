package org.snapscript.tree.define;

import static org.snapscript.core.result.Result.NORMAL;

import org.snapscript.core.Compilation;
import org.snapscript.core.Execution;
import org.snapscript.core.NoExecution;
import org.snapscript.core.Statement;
import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.module.Module;
import org.snapscript.core.module.Path;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.scope.State;
import org.snapscript.tree.constraint.AliasName;

public class AliasDefinition implements Compilation {
     
   private final Statement alias;

   public AliasDefinition(AliasName name, Constraint constraint) {
      this.alias = new CompileResult(name, constraint);      
   }

   @Override
   public Statement compile(Module module, Path path, int line) throws Exception {
      return alias;
   }
   
   private static class CompileResult extends Statement {   
      
      private final Constraint constraint;
      private final Execution execution;
      private final AliasName name;
      
      public CompileResult(AliasName name, Constraint constraint) {
         this.execution = new NoExecution(NORMAL);
         this.constraint = constraint;
         this.name = name;
      }
      
      @Override
      public void create(Scope outer) throws Exception {
         String alias = name.getName(outer);
         State state = outer.getState();

         state.addConstraint(alias, constraint);               
      }

      @Override
      public Execution compile(Scope scope, Constraint returns) throws Exception {
         return execution;
      }
   }
}
