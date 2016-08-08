package org.snapscript.core.convert;

import static org.snapscript.core.convert.Score.EXACT;

import org.snapscript.core.Type;

public class AnyConverter extends ConstraintConverter {
   
   private final ProxyWrapper wrapper;
   
   public AnyConverter(ProxyWrapper wrapper) {
      this.wrapper = wrapper;
   }
   
   @Override
   public Score score(Type type) throws Exception {
      return EXACT;
   }
   
   @Override
   public Score score(Object value) throws Exception {
      return EXACT;
   }
   
   @Override
   public Object convert(Object object) {
      return wrapper.toProxy(object);
   }
}
