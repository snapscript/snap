package org.snapscript.tree.define;

import static org.snapscript.core.Reserved.ENUM_NAME;
import static org.snapscript.core.Reserved.ENUM_ORDINAL;

import org.snapscript.core.scope.ScopeState;
import org.snapscript.core.scope.instance.Instance;
import org.snapscript.core.type.TypeState;
import org.snapscript.core.type.Type;
import org.snapscript.core.variable.Value;

public class EnumConstantGenerator extends TypeState {
   
   private final String name;
   private final int index;
   
   public EnumConstantGenerator(String name, int index) {
      this.index = index;
      this.name = name;
   }

   public void generate(Instance instance, Type type) throws Exception {
      ScopeState state = instance.getState();
      Value key = Value.getConstant(name);
      Value ordinal = Value.getConstant(index);
      
      state.addValue(ENUM_NAME, key);
      state.addValue(ENUM_ORDINAL, ordinal);
   }
}