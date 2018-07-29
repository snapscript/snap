package org.snapscript.tree;

import org.snapscript.core.Compilation;
import org.snapscript.core.Evaluation;
import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.module.Module;
import org.snapscript.core.module.Path;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.variable.Value;

public class Expression implements Compilation {

   private final Evaluation[] list;
   
   public Expression(Evaluation... list) {
      this.list = list;
   }

   @Override
   public Object compile(Module module, Path path, int line) throws Exception {
      if(list.length > 1) {
         return new CompileResult(list);
      }
      return list[0];
   }

   private static class CompileResult extends Evaluation {
   
      private final Evaluation[] list;
      
      public CompileResult(Evaluation... list) {
         this.list = list;
      }
   
      @Override
      public void define(Scope scope) throws Exception {
         for(int i = 0; i < list.length; i++){
            list[i].define(scope);
         }
      }
      
      @Override
      public Constraint compile(Scope scope, Constraint left) throws Exception {
         return list[list.length -1].compile(scope, left);
      }
      
      @Override
      public Value evaluate(Scope scope, Object left) throws Exception {
         Value value = list[0].evaluate(scope, left);
         
         for(int i = 1; i < list.length; i++){
            value = list[i].evaluate(scope, left);
         }
         return value;
      }
   }
}