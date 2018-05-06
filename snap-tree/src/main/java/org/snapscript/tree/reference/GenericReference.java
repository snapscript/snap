package org.snapscript.tree.reference;

import java.util.List;

import org.snapscript.core.Compilation;
import org.snapscript.core.Evaluation;
import org.snapscript.core.InternalStateException;
import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.constraint.GenericConstraint;
import org.snapscript.core.constraint.TypeConstraint;
import org.snapscript.core.module.Module;
import org.snapscript.core.module.Path;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.type.Type;
import org.snapscript.core.variable.Value;

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
      return new CompileResult(evaluation, list, module, path, line);
   }
   
   private static class CompileResult extends Evaluation{ 

      private final GenericArgumentList list;
      private final Evaluation type;
      private final Path path;
      private final int line;
      
      public CompileResult(Evaluation type, GenericArgumentList list, Module module, Path path, int line) {
         this.type = type;
         this.list = list;
         this.path = path;
         this.line = line;
      }
      
      public Value evaluate(Scope scope, Object left) {      
         Constraint constraint = create(scope, left);
         return Value.getTransient(constraint);
      }

      private Constraint create(Scope scope, Object left) {      
         try {
            Value value = type.evaluate(scope, null);
            String name = value.getName(scope);
            Type type = value.getValue();
            
            if(list != null) {
               List<Constraint> generics = list.create(scope);         
               return new GenericConstraint(type, generics, name);
            }
            return new TypeConstraint(type, name);
         }catch(Exception e) {
            throw new InternalStateException("Invalid constraint in " + path + " at line " + line, e);
         }
      }
   }
}