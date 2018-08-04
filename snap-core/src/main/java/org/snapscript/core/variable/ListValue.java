package org.snapscript.core.variable;

import java.util.List;

import org.snapscript.core.Entity;
import org.snapscript.core.convert.proxy.ProxyWrapper;

public class ListValue extends Value {
   
   private final ProxyWrapper wrapper;
   private final Entity source;
   private final Integer index;
   private final List list;
   
   public ListValue(ProxyWrapper wrapper, List list, Entity source, int index) {
      if(source == null){
         throw new IllegalStateException();
      }
      this.wrapper = wrapper;
      this.source = source;
      this.index = index;
      this.list = list;
   }
   
   @Override
   public Entity getSource() {
      return source;
   }
   
   @Override
   public Object getValue(){
      Object value = list.get(index);
      
      if(value != null) {
         return wrapper.fromProxy(value);
      }
      return value;
   }
   
   @Override
   public void setValue(Object value){
      Object proxy = wrapper.toProxy(value);
      int length = list.size();
      
      for(int i = length; i <= index; i++) {
         list.add(null);
      }
      list.set(index, proxy);
   }       
   
   @Override
   public String toString() {
      return String.valueOf(list);
   }
}