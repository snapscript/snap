package org.snapscript.tree.define;

import org.snapscript.core.Statement;
import org.snapscript.core.Type;
import org.snapscript.core.define.Initializer;
import org.snapscript.tree.ModifierList;
import org.snapscript.tree.annotation.AnnotationList;
import org.snapscript.tree.function.ParameterList;

public class EnumConstructor extends ClassConstructor {

   public EnumConstructor(AnnotationList annotations, ModifierList modifiers, ParameterList parameters, Statement body) {
      super(annotations, modifiers, parameters, body);
   }

   @Override
   public Initializer compile(Initializer statements, Type type) throws Exception {
      return compile(statements, type, false);
   }
}
