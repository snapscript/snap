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

public class GenericConstraint implements Compilation {
   
   private final Evaluation evaluation;
   private final ConstraintList list;
   
   public GenericConstraint(Evaluation evaluation) {
      this(evaluation, null);
   }
   
   public GenericConstraint(Evaluation evaluation, ConstraintList list) {
      this.evaluation = evaluation;
      this.list = list;
   }

   @Override
   public Object compile(Module module, Path path, int line) throws Exception {
      return new CompileResult(evaluation, list, module, path, line);
   }
   
   private static class CompileResult extends Constraint { 
   
      private ConstraintDescription description;
      private ConstraintDeclaration constraint;
      private List<Constraint> list;
      private String name;
      private Type type;
      private Path path;
      private int line;
      
      public CompileResult(Evaluation constraint, ConstraintList list, Module module, Path path, int line) {
         this.constraint = new ConstraintDeclaration(constraint, list, path, line);
         this.description = new ConstraintDescription(this, module);
         this.path = path;
         this.line = line;
      }

      @Override
      public List<Constraint> getGenerics(Scope scope) {
         if(list == null) {
            List<Constraint> result = constraint.getGenerics(scope);
            
            if(result == null) {
               throw new InternalStateException("No constraint in " + path + " at line " + line);
            }
            list = result;
         }
         return list;
      }
      
      @Override
      public Type getType(Scope scope) {
         if(type == null) {
            Constraint result = constraint.getType(scope);
            
            if(result == null) {
               throw new InternalStateException("No constraint in " + path + " at line " + line);
            }
            name = result.getName(scope);
            type = result.getType(scope);
         }
         return type;
      }
      
      @Override
      public String getName(Scope scope) {
         if(name == null) {
            Constraint result = constraint.getType(scope);
            
            if(result == null) {
               throw new InternalStateException("No constraint in " + path + " at line " + line);
            }
            name = result.getName(scope);
            type = result.getType(scope);
         }
         return name;
      }
      
      @Override
      public String toString() {
         return description.toString();
      }
   }
}