package org.snapscript.tree.define;

import static org.snapscript.core.Reserved.ENUM_NAME;
import static org.snapscript.core.Reserved.ENUM_ORDINAL;

import org.snapscript.core.scope.State;
import org.snapscript.core.scope.Value;
import org.snapscript.core.scope.instance.Instance;
import org.snapscript.core.type.Allocation;
import org.snapscript.core.type.Type;

public class EnumConstantGenerator extends Allocation {
   
   private final String name;
   private final int index;
   
   public EnumConstantGenerator(String name, int index) {
      this.index = index;
      this.name = name;
   }

   public void generate(Instance instance, Type type) throws Exception {
      State state = instance.getState();
      Value key = Value.getConstant(name);
      Value ordinal = Value.getConstant(index);
      
      state.add(ENUM_NAME, key);
      state.add(ENUM_ORDINAL, ordinal);
   }
}