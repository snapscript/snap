package org.snapscript.tree.reference;

import org.snapscript.core.Bug;
import org.snapscript.core.Constraint;
import org.snapscript.core.Evaluation;
import org.snapscript.core.Identity;
import org.snapscript.core.Scope;
import org.snapscript.core.Value;
import org.snapscript.tree.Argument;
import org.snapscript.tree.collection.CollectionIndex;

public class ReferenceIndex extends Evaluation {
   
   private final Argument argument;
  
   public ReferenceIndex(Argument argument) {     
      this.argument = argument;
   }
   
   @Override
   public void compile(Scope scope) throws Exception {
      argument.compile(scope);
   }
   
   @Bug("is this right???")
   @Override
   public Constraint validate(Scope scope, Constraint left) throws Exception {
      return argument.validate(scope, left);
   }

   @Override
   public Value evaluate(Scope scope, Object left) throws Exception {
      Evaluation value = new Identity(left);
      CollectionIndex index = new CollectionIndex(value, argument);
      
      return index.evaluate(scope, left);
   }
}