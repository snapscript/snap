package org.snapscript.core.constraint.transform;

import java.util.List;
import java.util.Map;

import org.snapscript.core.InternalStateException;
import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.type.Type;

public class PositionIndex implements ConstraintIndex {
   
   private final Map<String, Integer> positions;
   private final ConstraintExtractor extractor;
   private final ConstraintPromoter promoter;
   private final Type type;
   
   public PositionIndex(Type type, Map<String, Integer> positions) {
      this.extractor = new ConstraintExtractor(type);
      this.promoter = new ConstraintPromoter(type);
      this.positions = positions;
      this.type = type;
   }
   
   @Override
   public Constraint resolve(Constraint constraint, String name){
      Integer position = positions.get(name);
      
      if(position != null) {
         List<Constraint> constraints = extractor.extract(constraint);
         int count = constraints.size();
         
         if(position >= count) {
            throw new InternalStateException("No generic parameter at " + position +" for " + type);         
         }
         Constraint result = constraints.get(position);

         if(result == null) {
            throw new InternalStateException("No generic parameter at " + position +" for " + type);    
         }
         return promoter.promote(result);
      }
      return null;
   }
}
