package org.snapscript.tree.constraint;

import java.util.List;

import org.snapscript.core.Compilation;
import org.snapscript.core.Evaluation;
import org.snapscript.core.InternalStateException;
import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.module.Module;
import org.snapscript.core.module.Path;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.type.Type;

public class TypeConstraint implements Compilation {
   
   private final Evaluation evaluation;
   private final ConstraintList list;
   
   public TypeConstraint(Evaluation evaluation) {
      this(evaluation, null);
   }
   
   public TypeConstraint(Evaluation evaluation, ConstraintList list) {
      this.evaluation = evaluation;
      this.list = list;
   }

   @Override
   public Object compile(Module module, Path path, int line) throws Exception {
      return new CompileResult(evaluation, list, path, line);
   }
   
   private static class CompileResult extends Constraint { 
   
      private GenericConstraint constraint;
      private List<Type> list;
      private Type type;
      private Path path;
      private int line;
      
      public CompileResult(Evaluation constraint, ConstraintList list, Path path, int line) {
         this.constraint = new GenericConstraint(constraint, list, path, line);
         this.path = path;
         this.line = line;
      }

      @Override
      public List<Type> getGenerics(Scope scope) {
         if(list == null) {
            List<Type> result = constraint.getGenerics(scope);
            
            if(list == null) {
               throw new InternalStateException("No constraint in " + path + " at line " + line);
            }
            list = result;
         }
         return list;
      }
      
      @Override
      public Type getType(Scope scope) {
         if(type == null) {
            Type result = constraint.getType(scope);
            
            if(result == null) {
               throw new InternalStateException("No constraint in " + path + " at line " + line);
            }
            type = result;
         }
         return type;
      }
   }
}