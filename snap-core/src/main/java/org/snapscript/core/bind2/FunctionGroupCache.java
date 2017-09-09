package org.snapscript.core.bind2;

import org.snapscript.common.Cache;
import org.snapscript.common.CopyOnWriteCache;
import org.snapscript.core.Type;
import org.snapscript.core.bind.FunctionKeyBuilder;
import org.snapscript.core.function.Function;

public class FunctionGroupCache {

   private final Cache<String, FunctionGroup> groups;
   private final FunctionKeyBuilder builder;
   private final FunctionMatcher matcher;
   private final int filter;
   
   public FunctionGroupCache(FunctionMatcher matcher, FunctionKeyBuilder builder, int filter) {
      this.groups = new CopyOnWriteCache<String, FunctionGroup>();
      this.matcher = matcher;
      this.builder = builder;
      this.filter = filter;
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
         group = new FunctionGroup(matcher, builder, filter);
         groups.cache(name, group);
      }
      group.update(function);
   }
}
