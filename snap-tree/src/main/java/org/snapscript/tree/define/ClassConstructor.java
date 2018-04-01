package org.snapscript.tree.define;

import org.snapscript.core.Scope;
import org.snapscript.core.Statement;
import org.snapscript.core.Type;
import org.snapscript.core.TypeBody;
import org.snapscript.core.Allocation;
import org.snapscript.core.TypePart;
import org.snapscript.tree.ModifierList;
import org.snapscript.tree.annotation.AnnotationList;
import org.snapscript.tree.function.ParameterList;

public class ClassConstructor extends MemberConstructor {
   
   public ClassConstructor(AnnotationList annotations, ModifierList modifiers, ParameterList parameters, Statement body){  
      super(annotations, modifiers, parameters, null, body);
   }  
   
   public ClassConstructor(AnnotationList annotations, ModifierList modifiers, ParameterList parameters, TypePart part, Statement body){  
      super(annotations, modifiers, parameters, part, body);
   } 
   
   @Override
   public Allocation define(TypeBody body, Type type, Scope scope) throws Exception {
      return assemble(body, type, scope, true);
   }
}