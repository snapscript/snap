package org.snapscript.tree.define;

import org.snapscript.core.type.TypePart;
import org.snapscript.tree.annotation.AnnotationList;

public class AbstractClassDefinition extends ClassDefinition {

   public AbstractClassDefinition(AnnotationList annotations, AbstractClassName name, TypeHierarchy hierarchy, TypePart... parts) {
      super(annotations, name, hierarchy, parts);
   }
}
