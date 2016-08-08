package org.snapscript.tree;

import org.snapscript.core.Evaluation;
import org.snapscript.core.InternalStateException;
import org.snapscript.core.Scope;
import org.snapscript.core.Value;

public class NameExtractor {

   private Evaluation evaluation;
   private String name;
   
   public NameExtractor(Evaluation evaluation) {
      this.evaluation = evaluation;
   }
   
   public String extract(Scope scope) throws Exception {
      if(name == null) {
         Value value = evaluation.evaluate(scope, null);
         String identifier = value.getString();
         
         if(identifier == null) {
            throw new InternalStateException("Name evaluated to null");
         }
         name = identifier;
      }
      return name;
      
   }
}
