package org.snapscript.core.bind;

import org.snapscript.common.Cache;
import org.snapscript.common.CopyOnWriteCache;
import org.snapscript.core.Type;
import org.snapscript.core.function.Function;

public class FunctionGroupCache {

   private final Cache<String, FunctionGroup> groups;
   private final FunctionKeyBuilder builder;
   private final FunctionMatcher matcher;
   
   public FunctionGroupCache(FunctionMatcher matcher, FunctionKeyBuilder builder) {
      this.groups = new CopyOnWriteCache<String, FunctionGroup>();
      this.matcher = matcher;
      this.builder = builder;
   }
   
   public Function resolve(String name, Type... list) throws Exception {
      FunctionGroup group = groups.fetch(name);
      
      if(group != null) {
         return group.resolve(name, list);
      }
      return null;
   }
   
   public Function resolve(String name, Object... list) throws Exception {
      FunctionGroup group = groups.fetch(name);
      
      if(group != null) {
         return group.resolve(name, list);
      }
      return null;
   }

   public void update(Function function) {
      String name = function.getName();
      FunctionGroup group = groups.fetch(name);
      
      if(group == null) {
         group = new FunctionGroup(matcher, builder);
         groups.cache(name, group);
      }
      group.update(function);
   }
}
