package org.snapscript.tree.constraint;

import org.snapscript.core.Compilation;
import org.snapscript.core.Evaluation;
import org.snapscript.core.InternalStateException;
import org.snapscript.core.Module;
import org.snapscript.core.Path;
import org.snapscript.core.Scope;
import org.snapscript.core.Type;
import org.snapscript.core.Value;

public class TypeConstraint implements Compilation {
   
   private final Evaluation evaluation;
   
   public TypeConstraint(Evaluation evaluation) {
      this.evaluation = evaluation;
   }

   @Override
   public Object compile(Module module, Path path, int line) throws Exception {
      return new CompileResult(evaluation, path, line);
   }
   
   private static class CompileResult implements Constraint { 
   
      private Evaluation constraint;
      private Path path;
      private Type type;
      private int line;
      
      public CompileResult(Evaluation constraint, Path path, int line) {
         this.constraint = constraint;
         this.path = path;
         this.line = line;
      }
      
      @Override
      public Type getType(Scope scope) {
         if(type == null) {
            Type constraint = getConstraint(scope);
            
            if(constraint == null) {
               throw new InternalStateException("No constraint in " + path + " at line " + line);
            }
            type = constraint;
         }
         return type;
      }
      
      private Type getConstraint(Scope scope) {
         try {
            Value value = constraint.evaluate(scope, null);
            
            if(value != null) {
               return value.getValue();
            }
         }catch(Exception e) {
            throw new InternalStateException("Invalid constraint in " + path + " at line " + line, e);
         }
         return null;
      }
   }

}