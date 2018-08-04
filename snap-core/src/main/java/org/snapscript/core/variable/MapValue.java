package org.snapscript.core.variable;

import java.util.Map;

import org.snapscript.core.Entity;
import org.snapscript.core.convert.proxy.ProxyWrapper;

public class MapValue extends Value {
   
   private final ProxyWrapper wrapper;
   private final Entity source;
   private final Object key;
   private final Map map;
   
   public MapValue(ProxyWrapper wrapper, Map map, Entity source, Object key) {
      if(source == null){
         throw new IllegalStateException();
      }
      this.wrapper = wrapper;
      this.source = source;
      this.key = key;
      this.map = map;
   }
   
   @Override
   public Entity getSource(){
      return source;
   }
   
   @Override
   public Object getValue(){
      Object value = map.get(key);
      
      if(value != null) {
         return wrapper.fromProxy(value);
      }
      return value;
   }
   
   @Override
   public void setValue(Object value){
      Object proxy = wrapper.toProxy(value);
      
      if(value != null) {
         map.put(key, proxy);
      } else {
         map.remove(key);
      }
   }       
   
   @Override
   public String toString() {
      return String.valueOf(key);
   }
}