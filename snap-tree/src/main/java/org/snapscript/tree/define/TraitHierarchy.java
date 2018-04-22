package org.snapscript.tree.define;

import org.snapscript.tree.constraint.TraitConstraint;

public class TraitHierarchy extends ClassHierarchy{
   
   public TraitHierarchy(TraitConstraint... traits) {
      super(null, traits);     
   }
}
