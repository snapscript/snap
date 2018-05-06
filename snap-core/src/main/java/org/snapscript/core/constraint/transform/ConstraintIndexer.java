package org.snapscript.core.constraint.transform;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.type.Type;
import org.snapscript.core.type.TypeCache;

public class ConstraintIndexer {
   
   private final TypeCache<ConstraintIndex> indexes;
   
   public ConstraintIndexer(){
      this.indexes = new TypeCache<ConstraintIndex>();
   }

   public ConstraintIndex index(Type type){ // give me the named parameters
      ConstraintIndex index = indexes.fetch(type);
      
      if(index == null) {
         index = create(type);
         indexes.cache(type, index);
      }
      return index;
   }
   
   private ConstraintIndex create(Type type) {
      Scope scope = type.getScope();
      List<Constraint> generics = type.getConstraints(); 
      int count = generics.size();
      
      if(count > 0) {
         Map<String, Integer> table = new HashMap<String,Integer>();
         
         for(int i = 0; i < count; i++){
            Constraint constraint = generics.get(i);
            String name = constraint.getName(scope);
            
            if(name != null) {            
               table.put(name, i);
            }
         }
         return new PositionIndex(type, table);
      }
      return new EmptyIndex();
   }
}
