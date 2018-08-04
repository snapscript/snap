package org.snapscript.tree;

import org.snapscript.core.Evaluation;
import org.snapscript.core.error.InternalStateException;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.variable.Value;

public class NameReference {

   private Evaluation identifier;
   private String name;
   
   public NameReference(Evaluation identifier) {
      this.identifier = identifier;
   }
   
   public String getName(Scope scope) throws Exception {
      if(name == null) {
         Value value = identifier.evaluate(scope, null);
         String identifier = value.getData().getString();
         
         if(identifier == null) {
            throw new InternalStateException("Name evaluated to null");
         }
         name = identifier;
      }
      return name;
      
   }
}