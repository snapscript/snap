package org.snapscript.tree.variable;

import java.util.concurrent.atomic.AtomicInteger;

import org.snapscript.core.Evaluation;
import org.snapscript.core.Index;
import org.snapscript.core.Scope;
import org.snapscript.core.State;
import org.snapscript.core.Table;
import org.snapscript.core.Value;
import org.snapscript.tree.NameReference;

public class Variable extends Evaluation {
   
   private final NameReference reference;
   private final VariableBinder binder;
   private final AtomicInteger offset;
   
   public Variable(Evaluation identifier) {
      this.reference = new NameReference(identifier);
      this.binder = new VariableBinder(reference);
      this.offset = new AtomicInteger(-1);
   }

   @Override
   public void compile(Scope scope) throws Exception{
      String name = reference.getName(scope);
      Index index = scope.getIndex();
      int depth = index.get(name);

      offset.set(depth);
   }
   
   @Override
   public Value evaluate(Scope scope, Object left) throws Exception{
      String name = reference.getName(scope);
      
      if(left == null) {
         int depth = offset.get();
         
         if(depth == -1){
            State state = scope.getState();
            Value value = state.get(name);
            
            if(value != null) { 
               return value;
            }
         }else {
            Table table = scope.getTable();
            Value value = table.get(depth);

            if(value != null) { 
               return value;
            }
         }
      }
      return binder.bind(scope, left, name);
   }  
}