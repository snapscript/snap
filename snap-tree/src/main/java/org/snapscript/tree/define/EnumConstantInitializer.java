package org.snapscript.tree.define;

import static org.snapscript.core.Reserved.ENUM_VALUES;
import static org.snapscript.core.Reserved.TYPE_CLASS;
import static org.snapscript.core.Reserved.TYPE_SUPER;
import static org.snapscript.core.Reserved.TYPE_THIS;

import java.util.List;

import org.snapscript.core.Scope;
import org.snapscript.core.Type;

public class EnumConstantInitializer extends ClassConstantInitializer {

   public EnumConstantInitializer() {
      super();
   }
   
   public void declare(Scope scope, Type type, List values) throws Exception {
      declareConstant(scope, TYPE_THIS, type);
      declareConstant(scope, TYPE_SUPER, type);
      declareConstant(scope, TYPE_CLASS, type, type);
      declareConstant(scope, ENUM_VALUES, type, values);
   }
}
