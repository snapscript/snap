package org.snapscript.core.constraint.transform;

import java.util.List;
import java.util.Map;

import org.snapscript.core.InternalStateException;
import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.type.Type;

public class TableIndex implements GenericIndex {
   
   private final Map<String, Integer> table;
   private final GenericTypeMapper mapper;
   private final Type type;
   
   public TableIndex(Type type, Map<String, Integer> table) {
      this.mapper = new GenericTypeMapper(type);
      this.table = table;
      this.type = type;
   }
   
   @Override
   public Constraint getType(Constraint constraint, String name){
      Integer index = table.get(name);
      
      if(index != null) {
         List<Constraint> constraints = getTypes(constraint);
         int count = constraints.size();
         
         if(index >= count) {
            throw new InternalStateException("No generic parameter at " + index +" for " + type);         
         }
         Constraint result = constraints.get(index);

         if(result == null) {
            throw new InternalStateException("No generic parameter at " + index +" for " + type);    
         }
         return mapper.map(result);
      }
      return null;
   }
   
   private List<Constraint> getTypes(Constraint constraint) {
      Scope scope = type.getScope();
      List<Constraint> generics = constraint.getGenerics(scope);
      List<Constraint> constraints = type.getConstraints();
      int require = constraints.size();
      int actual = generics.size();
      
      if(actual > 0) { // the generics were declared
         if(require != actual) {
            throw new InternalStateException("Invalid generic parameters for " + type);
         }
         return generics;
      } 
      return constraints; // no generics declared
   }
}
