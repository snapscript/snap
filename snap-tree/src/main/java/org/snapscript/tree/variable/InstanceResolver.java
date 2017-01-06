package org.snapscript.tree.variable;

import static org.snapscript.core.Reserved.TYPE_THIS;

import org.snapscript.core.Scope;
import org.snapscript.core.State;
import org.snapscript.core.Value;
import org.snapscript.core.define.Instance;

public class InstanceResolver implements ValueResolver<Object> {
   
   private final ValueResolver resolver;
   
   public InstanceResolver(String name) {
      this.resolver = new LocalResolver(name);
   }
   
   @Override
   public Value resolve(Scope scope, Object left) {
      Instance instance = (Instance)scope;
      State state = instance.getState();
      Value value = state.get(TYPE_THIS);
      Scope outer = value.getValue();
      
      return resolver.resolve(outer, left);
   }
}