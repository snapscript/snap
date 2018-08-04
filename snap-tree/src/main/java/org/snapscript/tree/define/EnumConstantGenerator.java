package org.snapscript.tree.define;

import static org.snapscript.core.Reserved.ENUM_NAME;
import static org.snapscript.core.Reserved.ENUM_ORDINAL;
import static org.snapscript.core.variable.Constant.INTEGER;
import static org.snapscript.core.variable.Constant.STRING;

import org.snapscript.core.scope.State;
import org.snapscript.core.scope.instance.Instance;
import org.snapscript.core.type.Type;
import org.snapscript.core.type.TypeState;
import org.snapscript.core.variable.Value;

public class EnumConstantGenerator extends TypeState {
   
   private final String name;
   private final int index;
   
   public EnumConstantGenerator(String name, int index) {
      this.index = index;
      this.name = name;
   }

   public void generate(Instance instance, Type type) throws Exception {
      State state = instance.getState();
      Value key = Value.getConstant(name, type, STRING);
      Value ordinal = Value.getConstant(index, type, INTEGER);
      
      state.add(ENUM_NAME, key);
      state.add(ENUM_ORDINAL, ordinal);
   }
}