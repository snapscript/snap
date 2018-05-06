package org.snapscript.core.constraint.transform;

import java.util.ArrayList;
import java.util.List;

import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.constraint.GenericConstraint;
import org.snapscript.core.type.Type;

public class GenericTransform implements ConstraintTransform{
   
   private final ConstraintTransform[] list;
   private final ConstraintIndex index;
   private final Type type;
   
   public GenericTransform(Type type, ConstraintIndex index, ConstraintTransform[] list) {
      this.index = index;
      this.list = list;
      this.type = type;
   }
   
   @Override
   public ConstraintHandle apply(Constraint origin){
      Constraint constraint = create(origin);
      
      if(index != null) {
         return new ConstraintHandle(constraint, index);
      }
      return new ConstraintHandle(constraint);
   }
   
   private Constraint create(Constraint origin){
      List<Constraint> constraints = new ArrayList<Constraint>();

      for(ConstraintTransform entry : list){
         ConstraintHandle handle = entry.apply(origin);
         Constraint constraint = handle.getType();
         
         constraints.add(constraint);
      }
      return new GenericConstraint(type, constraints);
   }
}
