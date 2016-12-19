package org.snapscript.core.stack;

import org.snapscript.core.Evaluation;
import org.snapscript.core.Value;

public class ValueReference {

   private Evaluation evaluation;
   private Address address;
   
   public ValueReference(Evaluation evaluation) {
      this.evaluation = evaluation;
   }
   
   public Value get(State2 state) throws Exception {
      if(address == null) {
         Value value = evaluation.evaluate(null, null);
         String name = value.getValue();
         
         address = state.address(name);
      }
      return state.get(address);
   }
}
