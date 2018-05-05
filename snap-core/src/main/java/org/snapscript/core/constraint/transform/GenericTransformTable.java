package org.snapscript.core.constraint.transform;

import org.snapscript.common.Cache;
import org.snapscript.common.CopyOnWriteCache;
import org.snapscript.core.type.Type;

public class GenericTransformTable {
   
   private final Cache<Integer, GenericTransform> cache;
   
   public GenericTransformTable() {
      this.cache = new CopyOnWriteCache<Integer, GenericTransform>();
   }

   public GenericTransform fetch(Type type) {
      int index = type.getOrder();
      return cache.fetch(index);
   }
   
   public void cache(Type type, GenericTransform transform) {
      int index = type.getOrder();
      cache.cache(index, transform);
   }
}
