package org.snapscript.core.constraint.transform;

import java.util.ArrayList;
import java.util.List;

import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.constraint.GenericConstraint;
import org.snapscript.core.type.Type;

public class GenericConstraintBuilder {
   
   private final ConstraintTransform[] list;
   private final Type type;
   
   public GenericConstraintBuilder(Type type, ConstraintTransform[] list) {
      this.list = list;
      this.type = type;
   }
   
   public Constraint create(Constraint origin){
      List<Constraint> constraints = new ArrayList<Constraint>();

      for(ConstraintTransform entry : list){
         ConstraintRule rule = entry.apply(origin);
         Constraint constraint = rule.getSource();
         
         constraints.add(constraint);
      }
      return new GenericConstraint(type, constraints);
   }
}
