package org.snapscript.tree.reference;

import static org.snapscript.core.Phase.COMPILED;

import org.snapscript.common.Progress;
import org.snapscript.core.Evaluation;
import org.snapscript.core.InternalStateException;
import org.snapscript.core.Phase;
import org.snapscript.core.Scope;
import org.snapscript.core.Type;
import org.snapscript.core.Value;

public class CompiledReference extends TypeReference {
   
   private Evaluation reference;
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
   public Value evaluate(Scope scope, Object left) throws Exception {
      if(type == null) {
         Value value = reference.evaluate(scope, left);
         Type result = value.getValue();
         Progress<Phase> progress = result.getProgress();
         
         if(!progress.wait(COMPILED, duration)) {
            throw new InternalStateException("Type '" + result + "' not compiled");
         }
         type = value;
      }
      return type;
   } 
}
