package org.snapscript.tree.define;

import org.snapscript.tree.constraint.TraitConstraint;

public class EnumHierarchy extends ClassHierarchy{
   
   public EnumHierarchy(TraitConstraint... traits) {
      super(null, traits);     
   }
}
