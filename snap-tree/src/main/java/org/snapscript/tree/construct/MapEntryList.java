package org.snapscript.tree.construct;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.snapscript.core.Context;
import org.snapscript.core.Evaluation;
import org.snapscript.core.Module;
import org.snapscript.core.Scope;
import org.snapscript.core.Value;
import org.snapscript.core.ValueType;
import org.snapscript.core.convert.ProxyWrapper;

public class MapEntryList implements Evaluation{
   
   private final MapEntry[] list;
   
   public MapEntryList(MapEntry... list) {
      this.list = list;
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
      return ValueType.getTransient(map);
   }
}