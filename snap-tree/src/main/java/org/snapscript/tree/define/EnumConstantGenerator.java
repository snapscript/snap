package org.snapscript.tree.define;

import static org.snapscript.core.Reserved.ENUM_VALUES;
import static org.snapscript.core.Reserved.TYPE_CLASS;
import static org.snapscript.core.Reserved.TYPE_THIS;

import java.util.List;

import org.snapscript.core.Scope;
import org.snapscript.core.Type;

public class EnumConstantGenerator extends ClassConstantGenerator {

   public EnumConstantGenerator() {
      super();
   }
   
   public void generate(Scope scope, Type type, List values) throws Exception {
      generateConstant(scope, TYPE_THIS, type);
      generateConstant(scope, TYPE_CLASS, type, type);
      generateConstant(scope, ENUM_VALUES, type, values);
   }
}