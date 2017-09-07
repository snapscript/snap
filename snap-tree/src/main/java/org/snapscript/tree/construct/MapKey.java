package org.snapscript.tree.construct;

import java.util.concurrent.atomic.AtomicInteger;

import org.snapscript.core.Bug;
import org.snapscript.core.Counter;
import org.snapscript.core.Evaluation;
import org.snapscript.core.Scope;
import org.snapscript.core.State;
import org.snapscript.core.Table;
import org.snapscript.core.Value;
import org.snapscript.tree.NameReference;

public class MapKey extends Evaluation {
   
   private final NameReference reference;
   private final AtomicInteger index;
   
   public MapKey(Evaluation key) {
      this.reference = new NameReference(key);
      this.index = new AtomicInteger(-1);
   }
   
   @Bug
   @Override
   public void compile(Scope scope) throws Exception{
      String name = reference.getName(scope);
      Counter counter = scope.getCounter();
      int depth = counter.get(name);

      index.set(depth);
   }
   
   @Override
   public Value evaluate(Scope scope, Object left) throws Exception{
      String name = reference.getName(scope);
      int depth = index.get();
      
      if(depth == -1){
         State state = scope.getState(); 
         Value value = state.getScope(name);
         
         if(value != null) { 
            return value;
         }
      }else {
         Table table = scope.getTable(); // here we use the stack
         Value value = table.get(depth);
         
         if(value != null) { 
            return value;
         }
      }
      return Value.getTransient(name);
   }
}