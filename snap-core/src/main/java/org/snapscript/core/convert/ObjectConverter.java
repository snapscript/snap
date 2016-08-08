package org.snapscript.core.convert;

import static org.snapscript.core.convert.Score.EXACT;

import org.snapscript.core.Type;
import org.snapscript.core.TypeCastChecker;
import org.snapscript.core.TypeExtractor;

public class ObjectConverter extends ConstraintConverter {
   
   private final TypeCastChecker checker;
   private final TypeExtractor extractor;
   private final ProxyWrapper wrapper;
   private final Type constraint;
   
   public ObjectConverter(TypeExtractor extractor, TypeCastChecker checker, ProxyWrapper wrapper, Type constraint) {
      this.constraint = constraint;
      this.extractor = extractor;
      this.wrapper = wrapper;
      this.checker = checker;
   }
   
   @Override
   public Score score(Type actual) throws Exception {
      if(actual != null) {
         Class real = actual.getType();
         Class require = constraint.getType();
         
         if(require == real) {
            return EXACT;
         }
         return checker.cast(actual, constraint);
      }
      return EXACT;
   }

   @Override
   public Score score(Object value) throws Exception { // argument type
      Type match = extractor.extract(value);
      
      if(match != null) {
         if(match.equals(constraint)) {
            return EXACT;
         }
         return checker.cast(value, constraint);
      }
      return EXACT;
   }
   
   @Override
   public Object convert(Object object) {
      Class require = constraint.getType();
      
      if(require != null) {
         return wrapper.toProxy(object, require);
      }
      return object;
   }
}