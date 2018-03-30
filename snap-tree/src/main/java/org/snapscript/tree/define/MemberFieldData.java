package org.snapscript.tree.define;

import org.snapscript.core.Evaluation;
import org.snapscript.core.constraint.Constraint;

public class MemberFieldData {

   private final Constraint constraint;
   private final Evaluation value;
   private final String name;
   
   public MemberFieldData(String name, Constraint constraint, Evaluation value) {
      this.constraint = constraint;
      this.value = value;
      this.name = name;
   }

   public String getName() {
      return name;
   }

   public Evaluation getValue() {
      return value;
   }

   public Constraint getConstraint() {
      return constraint;
   }
}