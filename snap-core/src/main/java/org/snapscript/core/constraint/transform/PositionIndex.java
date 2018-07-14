package org.snapscript.core.constraint.transform;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.constraint.TypeConstraint;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.type.Type;

public class PositionIndex implements ConstraintIndex {
   
   private final PositionMapper mapper;
   private final Type type;
   
   public PositionIndex(Type type, Map<String, Integer> positions) {
      this.mapper = new PositionMapper(type, positions);
      this.type = type;
   }

   @Override
   public Constraint update(Constraint source, Constraint change) {
      Scope scope = type.getScope();
      String name = change.getName(scope);
      
      if(name == null) {
         List<Constraint> generics = change.getGenerics(scope);
         Type type = change.getType(scope);
         int count = generics.size();
         
         if(count > 0) {
            List<Constraint> updated = new ArrayList<Constraint>();
            AtomicBoolean touch = new AtomicBoolean();
               
            for(Constraint generic : generics) {
               Constraint update = update(source, generic);
               
               touch.compareAndSet(false, update!= generic); // has anything at all changed
               updated.add(update);
            }
            if(touch.get()) {            
               return new TypeConstraint(type, updated);
            }
         }
         return change;
      }
      return mapper.resolve(source, name);
   }
}
