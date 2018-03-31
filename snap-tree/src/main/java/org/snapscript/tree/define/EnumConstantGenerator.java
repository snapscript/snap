package org.snapscript.tree.define;

import static org.snapscript.core.Reserved.ENUM_NAME;
import static org.snapscript.core.Reserved.ENUM_ORDINAL;
import static org.snapscript.core.Reserved.ENUM_VALUES;
import static org.snapscript.core.Reserved.TYPE_CLASS;
import static org.snapscript.core.Reserved.TYPE_THIS;
import static org.snapscript.core.constraint.Constraint.INTEGER;
import static org.snapscript.core.constraint.Constraint.LIST;
import static org.snapscript.core.constraint.Constraint.STRING;
import static org.snapscript.core.constraint.Constraint.TYPE;

import java.util.List;

import org.snapscript.core.Scope;
import org.snapscript.core.Type;
import org.snapscript.core.constraint.Constraint;

public class EnumConstantGenerator extends ClassConstantGenerator {

   public EnumConstantGenerator() {
      super();
   }
   
   public void generate(Scope scope, Type type, List values) throws Exception {
      Constraint constraint = Constraint.getVariable(type);
      
      generateConstant(scope, TYPE_THIS, type, null, constraint);      
      generateConstant(scope, TYPE_CLASS, type, type, TYPE);
      generateConstant(scope, ENUM_VALUES, type, values, LIST);
      generateConstant(scope, ENUM_NAME, type, null, STRING); // might declare name as property many times
      generateConstant(scope, ENUM_ORDINAL, type, null, INTEGER);
   }
}