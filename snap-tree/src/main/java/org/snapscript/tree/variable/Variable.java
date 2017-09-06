package org.snapscript.tree.variable;

import java.util.concurrent.atomic.AtomicInteger;

import org.snapscript.core.Counter;
import org.snapscript.core.Evaluation;
import org.snapscript.core.Scope;
import org.snapscript.core.State;
import org.snapscript.core.Value;
import org.snapscript.tree.NameReference;

public class Variable extends Evaluation {
   
   private final NameReference reference;
   private final VariableBinder binder;
   private final AtomicInteger index;
   
   public Variable(Evaluation identifier) {
      this.reference = new NameReference(identifier);
      this.binder = new VariableBinder();
      this.index = new AtomicInteger(-1);
   }

   @Override
   public void compile(Scope scope) throws Exception{
      String name = reference.getName(scope);
      Counter counter = scope.getCounter();
      int depth = counter.get(name);

      if(name.equals("line")){
         System.err.println();
      }
      index.set(depth);
   }
   
   @Override
   public Value evaluate(Scope scope, Object left) throws Exception{
      String name = reference.getName(scope);
      
      if(left == null) {
         int depth = index.get();
         
         if(depth == -1){
            State state = scope.getState();
            Value value = state.getScope(name);
            
            if(value != null) { 
               return value;
            }
         }else {
            State state = scope.getState(); // here we use the stack
            Value value = state.getLocal(depth);

            if(value != null) { 
               return value;
            }
         }
      }
      return binder.bind(scope, left, name);
   }  
}