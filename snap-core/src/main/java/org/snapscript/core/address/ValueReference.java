package org.snapscript.core.address;

import org.snapscript.core.Address;
import org.snapscript.core.Evaluation;
import org.snapscript.core.State;
import org.snapscript.core.Value;

public class ValueReference {

   private Evaluation evaluation;
   private Address address;
   
   public ValueReference(Evaluation evaluation) {
      this.evaluation = evaluation;
   }
   
   public Value get(State state) throws Exception {
      if(address == null) {
         Value value = evaluation.evaluate(null, null);
         String name = value.getValue();
         
         address = state.address(name);
      }
      return state.get(address);
   }
}
