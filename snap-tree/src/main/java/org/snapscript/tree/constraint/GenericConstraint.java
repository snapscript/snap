package org.snapscript.tree.constraint;

import java.util.Collections;
import java.util.List;

import org.snapscript.core.Evaluation;
import org.snapscript.core.InternalStateException;
import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.module.Path;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.type.Type;
import org.snapscript.core.variable.Value;

public class GenericConstraint extends Constraint {

   private final ConstraintList list;
   private final Evaluation type;
   private final Path path;
   private final int line;
   
   public GenericConstraint(Evaluation type, ConstraintList list, Path path, int line) {
      this.type = type;
      this.list = list;
      this.path = path;
      this.line = line;
   }
   
   @Override
   public List<Type> getGenerics(Scope scope) {
      try {
         if(list != null) {
            Value value = list.evaluate(scope, null);
            
            if(value != null) {
               return value.getValue();
            }
         }
      }catch(Exception e) {
         throw new InternalStateException("Invalid constraint in " + path + " at line " + line, e);
      }
      return Collections.emptyList();
   }
   
   @Override
   public Type getType(Scope scope) {
      try {
         if(type != null) {
            Value value = type.evaluate(scope, null);
            
            if(value != null) {
               return value.getValue();
            }
         }
      }catch(Exception e) {
         throw new InternalStateException("Invalid constraint in " + path + " at line " + line, e);
      }
      return null;
   }
}
