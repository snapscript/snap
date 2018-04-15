package org.snapscript.core.convert;

import static org.snapscript.core.convert.Score.EXACT;

import org.snapscript.core.InternalArgumentException;
import org.snapscript.core.type.Type;
import org.snapscript.core.convert.proxy.ProxyWrapper;
import org.snapscript.core.type.Category;
import org.snapscript.core.type.TypeExtractor;

public class ObjectConverter extends ConstraintConverter {
   
   private final CastChecker checker;
   private final TypeExtractor extractor;
   private final ProxyWrapper wrapper;
   private final Type constraint;
   
   public ObjectConverter(TypeExtractor extractor, CastChecker checker, ProxyWrapper wrapper, Type constraint) {
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
         
         if(require != real) {
            return checker.toType(actual, constraint);            
         }
      }
      return EXACT;
   }

   @Override
   public Score score(Object object) throws Exception { // argument type
      if(object != null) {
         Type match = extractor.getType(object);
      
         if(!match.equals(constraint)) {
            return checker.toType(match, constraint, object);           
         }
      }
      return EXACT;
   }
   
   @Override
   public Object assign(Object object) throws Exception {
      if(object != null) {
         Type match = extractor.getType(object);
         
         if(match != constraint) {
            Category category = match.getCategory();
            Score score = checker.toType(match, constraint, object);
            
            if(score.isInvalid()) {
               Class require = constraint.getType();
               
               if(require == null) {
                  throw new InternalArgumentException("Conversion from " + match + " to '" + constraint + "' is not possible");
               }
               return convert(object);
            }
            
            if(category.isFunction()) {
               return convert(object);
            }
         }
      }
      return object;
   }

   @Override
   public Object convert(Object object) throws Exception {
      if(object != null) {
         Class require = constraint.getType();
         
         if(require != null) {
            return wrapper.toProxy(object, require);
         }
      }
      return object;
   }
}