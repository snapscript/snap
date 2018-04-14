package org.snapscript.core.function.index;

import java.util.HashMap;
import java.util.Map;

import org.snapscript.core.type.Type;
import org.snapscript.core.function.Function;

public class FunctionIndexPartition {

   private final Map<String, FunctionIndexGroup> groups;
   private final FunctionKeyBuilder builder;
   private final FunctionReducer matcher;
   
   public FunctionIndexPartition(FunctionReducer matcher, FunctionKeyBuilder builder) {
      this.groups = new HashMap<String, FunctionIndexGroup>();
      this.matcher = matcher;
      this.builder = builder;
   }
   
   public FunctionPointer resolve(String name, Type... list) throws Exception {
      FunctionIndexGroup group = groups.get(name);
      
      if(group != null) {
         return group.resolve(list);
      }
      return null;
   }
   
   public FunctionPointer resolve(String name, Object... list) throws Exception {
      FunctionIndexGroup group = groups.get(name);
      
      if(group != null) {
         return group.resolve(list);
      }
      return null;
   }

   public void index(FunctionPointer pointer) throws Exception {
      Function function = pointer.getFunction();
      String name = function.getName();
      FunctionIndexGroup group = groups.get(name);
      
      if(group == null) {
         group = new FunctionIndexGroup(matcher, builder, name);
         groups.put(name, group);
      }
      group.index(pointer);
   }
}