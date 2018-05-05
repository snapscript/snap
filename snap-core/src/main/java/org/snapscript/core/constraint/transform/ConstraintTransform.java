package org.snapscript.core.constraint.transform;

import java.util.ArrayList;
import java.util.List;

import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.constraint.DeclarationConstraint;
import org.snapscript.core.type.Type;

public class ConstraintTransform implements GenericTransform{
   
   private final GenericTransform[] list;
   private final GenericIndex index;
   private final Type type;
   
   public ConstraintTransform(Type type, GenericIndex index, GenericTransform[] list) {
      this.index = index;
      this.list = list;
      this.type = type;
   }
   
   @Override
   public GenericReference getReference(Constraint origin){
      Constraint result = getConstraint(origin);
      return new GenericReference(origin, result, index);
   }
   
   private Constraint getConstraint(Constraint origin){
      List<Constraint> constraints = new ArrayList<Constraint>();

      for(GenericTransform entry : list){
         GenericReference reference = entry.getReference(origin);
         Constraint constraint = reference.getType();
         
         constraints.add(constraint);
      }
      return new DeclarationConstraint(type, constraints);
   }
}
