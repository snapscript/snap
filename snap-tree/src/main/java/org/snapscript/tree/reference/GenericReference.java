package org.snapscript.tree.reference;

import org.snapscript.core.Compilation;
import org.snapscript.core.Evaluation;
import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.module.Module;
import org.snapscript.core.module.Path;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.type.Type;

public class GenericReference implements Compilation {
   
   private final GenericArgumentList list;
   private final Evaluation evaluation;
   
   public GenericReference(Evaluation evaluation) {
      this(evaluation, null);
   }
   
   public GenericReference(Evaluation evaluation, GenericArgumentList list) {
      this.evaluation = evaluation;
      this.list = list;
   }

   @Override
   public Evaluation compile(Module module, Path path, int line) throws Exception {
      return new CompileResult(evaluation, list, path, line);
   }
   
   private static class CompileResult extends ConstraintReference { 

      private final GenericDeclaration declaration;
      
      public CompileResult(Evaluation type, GenericArgumentList list, Path path, int line) {
         this.declaration = new GenericDeclaration(type, list, path, line);
      }

      @Override
      protected ConstraintValue create(Scope scope) throws Exception {
         Constraint constraint = declaration.declare(scope);
         Type type = constraint.getType(scope);
         
         return new ConstraintValue(type, constraint);
      }      
   }
}