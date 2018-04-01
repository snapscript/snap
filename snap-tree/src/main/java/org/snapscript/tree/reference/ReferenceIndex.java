package org.snapscript.tree.reference;

import org.snapscript.core.Evaluation;
import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.scope.Value;
import org.snapscript.tree.Argument;
import org.snapscript.tree.collection.CollectionIndex;

public class ReferenceIndex extends Evaluation {
   
   private final Argument argument;
  
   public ReferenceIndex(Argument argument) {     
      this.argument = argument;
   }
   
   @Override
   public void define(Scope scope) throws Exception {
      argument.define(scope);
   }
   
   @Override
   public Constraint compile(Scope scope, Constraint left) throws Exception {      
      Evaluation value = new IndexValue(null, left);
      CollectionIndex index = new CollectionIndex(value, argument);
      
      return index.compile(scope, left);
   }

   @Override
   public Value evaluate(Scope scope, Object left) throws Exception {
      Evaluation value = new IndexValue(left, null);
      CollectionIndex index = new CollectionIndex(value, argument);
      
      return index.evaluate(scope, left);
   }
   
   private static class IndexValue extends Evaluation {
      
      private final Constraint constraint;
      private final Object value;
      
      private IndexValue(Object value, Constraint constraint) {
         this.constraint = constraint;
         this.value = value;
      }
      
      @Override
      public Constraint compile(Scope scope, Constraint left) throws Exception {
         return constraint;
      }

      @Override
      public Value evaluate(Scope scope, Object left) throws Exception {
         return Value.getTransient(value);
      }
   }
}