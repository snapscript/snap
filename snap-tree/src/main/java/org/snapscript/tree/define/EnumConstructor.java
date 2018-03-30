package org.snapscript.tree.define;

import org.snapscript.core.Scope;
import org.snapscript.core.Statement;
import org.snapscript.core.Type;
import org.snapscript.core.TypeFactory;
import org.snapscript.tree.ModifierList;
import org.snapscript.tree.annotation.AnnotationList;
import org.snapscript.tree.function.ParameterList;

public class EnumConstructor extends ClassConstructor {

   public EnumConstructor(AnnotationList annotations, ModifierList modifiers, ParameterList parameters, Statement body) {
      super(annotations, modifiers, parameters, body);
   }

   @Override
   public TypeFactory define(TypeFactory statements, Type type, Scope scope) throws Exception {
      return assemble(statements, type, scope, false);
   }
}