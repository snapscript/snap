package org.snapscript.core.variable.bind;

import java.util.Map;

import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.variable.Value;

public class MapResult implements VariableResult<Map> {

   private final Constraint constraint;
   private final String name;
   
   public MapResult(String name, Constraint constraint){
      this.constraint = constraint;
      this.name = name;
   }   

   @Override
   public Constraint getConstraint(Constraint left) {
      return constraint;
   }

   @Override
   public Value getValue(Map left) {
      Object value = left.get(name);
      return Value.getTransient(value);
   }

}
