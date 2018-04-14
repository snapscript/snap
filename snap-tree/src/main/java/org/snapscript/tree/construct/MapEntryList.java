package org.snapscript.tree.construct;

import static org.snapscript.core.constraint.Constraint.NONE;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.snapscript.core.Context;
import org.snapscript.core.Evaluation;
import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.convert.proxy.ProxyWrapper;
import org.snapscript.core.module.Module;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.variable.Value;

public class MapEntryList extends Evaluation{
   
   private final MapEntry[] list;
   
   public MapEntryList(MapEntry... list) {
      this.list = list;
   }
   
   @Override
   public void define(Scope scope) throws Exception{
      for(int i = 0; i < list.length; i++){
         list[i].define(scope);
      }
   }
   
   @Override
   public Constraint compile(Scope scope, Constraint left) throws Exception {
      for(int i = 0; i < list.length; i++){
         list[i].compile(scope);
      }
      return NONE;
   }
   
   @Override
   public Value evaluate(Scope scope, Object left) throws Exception{
      Map map = new LinkedHashMap();
      
      for(int i = 0; i < list.length; i++){
         Entry entry = list[i].create(scope);
         Module module = scope.getModule();
         Context context = module.getContext();
         ProxyWrapper wrapper = context.getWrapper();
         Object key = entry.getKey();
         Object value = entry.getValue();
         Object keyProxy = wrapper.toProxy(key);
         Object valueProxy = wrapper.toProxy(value);
         
         map.put(keyProxy, valueProxy);
      }
      return Value.getTransient(map);
   }
}