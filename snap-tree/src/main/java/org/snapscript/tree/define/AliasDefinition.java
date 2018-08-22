package org.snapscript.tree.define;

import org.snapscript.tree.annotation.AnnotationList;
import org.snapscript.tree.constraint.AliasName;

public class AliasDefinition extends ClassDefinition {

   public AliasDefinition(AnnotationList annotations, AliasName name, TypeHierarchy hierarchy) {
      super(annotations, name, hierarchy);
   }
}

