package org.snapscript.core.type.index;

public class ClassBound {

   private final Class bound;
   private final String name;

   public ClassBound(Class bound) {
      this(bound, null);
   }
   
   public ClassBound(Class bound, String name) {
      this.bound = bound;
      this.name = name;
   }
   
   public Class getBound() {
      return bound;
   }
   
   public String getName() {
      return name;
   }
}
