package org.snapscript.tree.construct;

import java.util.concurrent.atomic.AtomicInteger;

import org.snapscript.core.Evaluation;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.scope.State;
import org.snapscript.core.scope.index.Index;
import org.snapscript.core.scope.index.Table;
import org.snapscript.core.variable.Value;
import org.snapscript.tree.NameReference;

public class MapKey extends Evaluation {
   
   private final NameReference reference;
   private final AtomicInteger offset;
   
   public MapKey(Evaluation key) {
      this.reference = new NameReference(key);
      this.offset = new AtomicInteger(-1);
   }
   
   @Override
   public void define(Scope scope) throws Exception{
      String name = reference.getName(scope);
      Index index = scope.getIndex();
      int depth = index.get(name);

      offset.set(depth);
   }
   
   @Override
   public Value evaluate(Scope scope, Object left) throws Exception{
      String name = reference.getName(scope);
      int depth = offset.get();
      
      if(depth == -1){
         State state = scope.getState(); 
         Value value = state.get(name);
         
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