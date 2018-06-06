package org.snapscript.core.constraint.transform;

import java.util.List;
import java.util.Map;

import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.constraint.ConstraintMapper;
import org.snapscript.core.error.InternalStateException;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.type.Type;

public class PositionMapper {
   
   private final Map<String, Integer> positions;
   private final ConstraintMapper mapper;
   private final ConstraintSource source;
   private final Type type;
   
   public PositionMapper(Type type, Map<String, Integer> positions) {
      this.source = new ConstraintSource(type);
      this.mapper = new ConstraintMapper();
      this.positions = positions;
      this.type = type;
   }
   
   public Constraint resolve(Constraint constraint, String name){
      Integer position = positions.get(name);
      
      if(position != null) {
         List<Constraint> constraints = source.getConstraints(constraint);
         int count = constraints.size();
         
         if(position >= count) {
            throw new InternalStateException("No generic parameter at " + position +" for " + type);         
         }
         Scope scope = type.getScope();
         Constraint result = constraints.get(position);

         if(result == null) {
            throw new InternalStateException("No generic parameter at " + position +" for " + type);    
         }
         return mapper.map(scope, result);
      }
      return null;
   }
}
