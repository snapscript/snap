package org.snapscript.core.variable.bind;

import java.util.Map;

import org.snapscript.core.Entity;
import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.convert.proxy.ProxyWrapper;
import org.snapscript.core.variable.MapValue;
import org.snapscript.core.variable.Value;

public class MapResult implements VariableResult<Map> {
   
   private final Constraint constraint;
   private final ProxyWrapper wrapper;
   private final Entity source;
   private final String name;
   
   public MapResult(ProxyWrapper wrapper, Entity source, Constraint constraint, String name){      
      this.constraint = constraint;
      this.wrapper = wrapper;
      this.source = source;
      this.name = name;
   }   

   @Override
   public Constraint getConstraint(Constraint left) {
      return constraint;
   }

   @Override
   public Value getValue(Map left) {      
      return new MapValue(wrapper, left, source, name);
   }

}
