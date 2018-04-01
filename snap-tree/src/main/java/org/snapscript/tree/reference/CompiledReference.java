package org.snapscript.tree.reference;

import static org.snapscript.core.type.Phase.DEFINED;

import org.snapscript.common.Progress;
import org.snapscript.core.Evaluation;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.scope.Value;
import org.snapscript.core.type.Phase;
import org.snapscript.core.type.Type;
import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.error.InternalStateException;

public class CompiledReference extends TypeReference {
   
   private Evaluation reference;
   private Constraint constraint;
   private Value type;
   private long duration;

   public CompiledReference(Evaluation reference) {
      this(reference, 10000);
   }
   
   public CompiledReference(Evaluation reference, long duration) {
      this.reference = reference;
      this.duration = duration;
   }
   
   @Override
   public Constraint compile(Scope scope, Constraint left) throws Exception {
      if(constraint == null) {
         Constraint value = reference.compile(scope, left);
         Type result = value.getType(scope);
         Progress<Phase> progress = result.getProgress();
         
         if(!progress.wait(DEFINED, duration)) {
            throw new InternalStateException("Type '" + result + "' not compiled");
         }
         constraint = value;
      }
      return constraint;
   }
   
   @Override
   public Value evaluate(Scope scope, Object left) throws Exception {
      if(type == null) {
         Value value = reference.evaluate(scope, left);
         Type result = value.getValue();
         Progress<Phase> progress = result.getProgress();
         
         if(!progress.wait(DEFINED, duration)) {
            throw new InternalStateException("Type '" + result + "' not compiled");
         }
         type = value;
      }
      return type;
   } 
}