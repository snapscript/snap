package org.snapscript.core.constraint.transform;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.type.Type;
import org.snapscript.core.type.TypeCache;

public class GenericParameterIndexer {
   
   private final TypeCache<GenericIndex> indexes;
   
   public GenericParameterIndexer(){
      this.indexes = new TypeCache<GenericIndex>();
   }

   public GenericIndex index(Type type){ // give me the named parameters
      GenericIndex index = indexes.fetch(type);
      
      if(index == null) {
         index = create(type);
         indexes.cache(type, index);
      }
      return index;
   }
   
   private GenericIndex create(Type type) {
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
         return new TableIndex(type, table);
      }
      return new EmptyIndex();
   }
}
