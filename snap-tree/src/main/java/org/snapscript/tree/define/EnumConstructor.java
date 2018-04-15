package org.snapscript.tree.define;

import org.snapscript.core.Statement;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.type.TypeState;
import org.snapscript.core.type.Type;
import org.snapscript.core.type.TypeBody;
import org.snapscript.tree.ModifierList;
import org.snapscript.tree.annotation.AnnotationList;
import org.snapscript.tree.function.ParameterList;

public class EnumConstructor extends ClassConstructor {

   public EnumConstructor(AnnotationList annotations, ModifierList modifiers, ParameterList parameters, Statement body) {
      super(annotations, modifiers, parameters, body);
   }

   @Override
   public TypeState define(TypeBody body, Type type, Scope scope) throws Exception {
      return assemble(body, type, scope, false);
   }
}