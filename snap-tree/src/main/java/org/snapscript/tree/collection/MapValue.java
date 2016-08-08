package org.snapscript.tree.collection;

import java.util.Map;

import org.snapscript.core.Value;
import org.snapscript.core.convert.ProxyWrapper;

public class MapValue extends Value {
   
   private final ProxyWrapper wrapper;
   private final String name;
   private final Map map;
   
   public MapValue(ProxyWrapper wrapper, Map map, String name) {
      this.wrapper = wrapper;
      this.name = name;
      this.map = map;
   }
   
   @Override
   public Class getType() {
      return Object.class;
   }
   
   @Override
   public Object getValue(){
      Object value = map.get(name);
      
      if(value != null) {
         return wrapper.fromProxy(value);
      }
      return value;
   }
   
   @Override
   public void setValue(Object value){
      Object proxy = wrapper.toProxy(value);
      
      if(value != null) {
         map.put(name, proxy);
      } else {
         map.remove(name);
      }
   }       
   
   @Override
   public String toString() {
      return String.valueOf(map);
   }
}