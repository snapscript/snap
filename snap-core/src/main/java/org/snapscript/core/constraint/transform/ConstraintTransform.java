package org.snapscript.core.constraint.transform;

import java.util.ArrayList;
import java.util.List;

import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.constraint.GenericConstraint;
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
   public GenericHandle getHandle(Constraint origin){
      Constraint constraint = getConstraint(origin);
      
      if(index != null) {
         return new GenericHandle(constraint, index);
      }
      return new GenericHandle(constraint);
   }
   
   private Constraint getConstraint(Constraint origin){
      List<Constraint> constraints = new ArrayList<Constraint>();

      for(GenericTransform entry : list){
         GenericHandle handle = entry.getHandle(origin);
         Constraint constraint = handle.getType();
         
         constraints.add(constraint);
      }
      return new GenericConstraint(type, constraints);
   }
}
