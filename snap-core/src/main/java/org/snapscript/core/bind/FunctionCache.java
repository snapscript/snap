package org.snapscript.core.bind;

import java.util.HashMap;
import java.util.Map;

import org.snapscript.core.Type;
import org.snapscript.core.function.Function;

public class FunctionCache {

   private final Map<String, FunctionGroup> groups;
   private final FunctionKeyBuilder builder;
   private final FunctionSearcher matcher;
   
   public FunctionCache(FunctionSearcher matcher, FunctionKeyBuilder builder) {
      this.groups = new HashMap<String, FunctionGroup>();
      this.matcher = matcher;
      this.builder = builder;
   }
   
   public FunctionCall resolve(String name, Type... list) throws Exception {
      FunctionGroup group = groups.get(name);
      
      if(group != null) {
         return group.resolve(name, list);
      }
      return null;
   }
   
   public FunctionCall resolve(String name, Object... list) throws Exception {
      FunctionGroup group = groups.get(name);
      
      if(group != null) {
         return group.resolve(name, list);
      }
      return null;
   }

   public void update(FunctionCall call) throws Exception {
      Function function = call.getFunction();
      String name = function.getName();
      FunctionGroup group = groups.get(name);
      
      if(group == null) {
         group = new FunctionGroup(matcher, builder);
         groups.put(name, group);
      }
      group.update(call);
   }
}
