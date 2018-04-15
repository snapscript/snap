package org.snapscript.core.convert;

import static org.snapscript.core.convert.Score.EXACT;

import org.snapscript.core.InternalArgumentException;
import org.snapscript.core.type.Type;
import org.snapscript.core.convert.proxy.ProxyWrapper;
import org.snapscript.core.type.Category;
import org.snapscript.core.type.TypeExtractor;

public class FunctionConverter extends ConstraintConverter {
   
   private final CastChecker checker;
   private final TypeExtractor extractor;
   private final ProxyWrapper wrapper;
   private final Type constraint;
   
   public FunctionConverter(TypeExtractor extractor, CastChecker checker, ProxyWrapper wrapper, Type constraint) {
      this.constraint = constraint;
      this.extractor = extractor;
      this.wrapper = wrapper;
      this.checker = checker;
   }
   
   @Override
   public Score score(Type actual) throws Exception {
      if(actual != null) {
         return checker.toFunction(actual, constraint);
      }
      return EXACT;
   }

   @Override
   public Score score(Object object) throws Exception { // argument type
      if(object != null) {
         Type match = extractor.getType(object);
         Category category = match.getCategory();
         
         if(category.isProxy()) {
            Object real = wrapper.fromProxy(object);
            
            if(real != object) {
               return score(real);
            }
         }
         return checker.toFunction(match, constraint, object);
      }
      return EXACT;
   }
   
   @Override
   public Object assign(Object object) throws Exception {
      if(object != null) {
         Type match = extractor.getType(object);
         Category category = match.getCategory();
         
         if(category.isProxy()) {
            Object real = wrapper.fromProxy(object);
            
            if(real != object) {
               return assign(real);
            }
         }
         Score score = checker.toFunction(match, constraint, object);
         
         if(score.isInvalid()) {
            throw new InternalArgumentException("Conversion from " + match + " to '" + constraint + "' is not possible");
         }
      }
      return object;
   }

   @Override
   public Object convert(Object object) throws Exception {
      if(object != null) {
         Type match = extractor.getType(object);
         Category category = match.getCategory();
         
         if(category.isProxy()) {
            Object real = wrapper.fromProxy(object);
            
            if(real != object) {
               return assign(real);
            }
         }
         Score score = checker.toFunction(match, constraint, object);
         
         if(score.isInvalid()) {
            throw new InternalArgumentException("Conversion from " + match + " to '" + constraint + "' is not possible");
         }
      }
      return object;
   }
}