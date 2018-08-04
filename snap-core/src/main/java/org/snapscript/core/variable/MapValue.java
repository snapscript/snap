package org.snapscript.core.variable;

import java.util.Map;

import org.snapscript.core.Entity;
import org.snapscript.core.convert.proxy.ProxyWrapper;
import org.snapscript.core.type.Type;

public class MapValue extends DataValue {
   
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
   public Type getType() {         
      return source.getScope().getModule().getContext().getExtractor().getType(getValue());
   }

   @Override
   public Data getData() {
      return this;
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
   public void setData(Data value){
      Object object = value.getValue();
      Object proxy = wrapper.toProxy(object);
      
      if(object != null) {
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