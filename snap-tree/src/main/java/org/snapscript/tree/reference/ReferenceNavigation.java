package org.snapscript.tree.reference;

import org.snapscript.core.Compilation;
import org.snapscript.core.Evaluation;
import org.snapscript.core.Module;
import org.snapscript.core.Path;
import org.snapscript.core.Scope;
import org.snapscript.core.Type;
import org.snapscript.core.Value;
import org.snapscript.parse.StringToken;

public class ReferenceNavigation implements Compilation {
   
   private final StringToken operator;
   private final Evaluation part;
   private final Evaluation next;

   public ReferenceNavigation(Evaluation part) {
      this(part, null, null);
   }
   
   public ReferenceNavigation(Evaluation part, StringToken operator, Evaluation next) {
      this.operator = operator;
      this.part = part;
      this.next = next;
   }

   @Override
   public Object compile(Module module, Path path, int line) throws Exception {
      if(next != null) {
         return new CompileResult(part, operator, next);
      }
      return part;
   }
   
   private static class CompileResult extends Evaluation {
   
      private final ReferenceOperator operator;
      private final Evaluation part;
      private final Evaluation next;
      
      public CompileResult(Evaluation part, StringToken operator, Evaluation next) {
         this.operator = ReferenceOperator.resolveOperator(operator);
         this.part = part;
         this.next = next;
      }
      
      @Override
      public void compile(Scope scope) throws Exception {
         next.compile(scope);
         part.compile(scope);
      }
      
      @Override
      public Type validate(Scope scope, Type left) throws Exception {
         Type value = part.validate(scope, left);         
         
         if(value != null) {
            return next.validate(scope, value);
         }
         return value;
      } 
      
      @Override
      public Value evaluate(Scope scope, Object left) throws Exception {
         Value value = part.evaluate(scope, left);         
         
         if(operator != null) {
            return operator.operate(scope, next, value);
         }
         return value;
      }   
   }
}