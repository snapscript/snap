package org.snapscript.tree.define;

import org.snapscript.core.Evaluation;
import org.snapscript.core.Type;

public class MemberFieldData {

   private final Evaluation value;
   private final String name;
   private final Type type;
   
   public MemberFieldData(String name, Type type, Evaluation value) {
      this.value = value;
      this.name = name;
      this.type = type;
   }

   public String getName() {
      return name;
   }

   public Evaluation getValue() {
      return value;
   }

   public Type getConstraint() {
      return type;
   }
}
