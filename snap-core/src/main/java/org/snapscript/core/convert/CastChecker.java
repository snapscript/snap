package org.snapscript.core.convert;

import static org.snapscript.core.convert.Score.EXACT;
import static org.snapscript.core.convert.Score.INVALID;
import static org.snapscript.core.convert.Score.SIMILAR;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.snapscript.core.InternalStateException;
import org.snapscript.core.function.ClosureFunctionFinder;
import org.snapscript.core.function.Function;
import org.snapscript.core.type.Category;
import org.snapscript.core.type.Type;
import org.snapscript.core.type.TypeExtractor;
import org.snapscript.core.type.TypeLoader;

public class CastChecker {

   private final FunctionComparator comparator;
   private final ClosureFunctionFinder finder;
   private final TypeExtractor extractor;
   
   public CastChecker(ConstraintMatcher matcher, TypeExtractor extractor, TypeLoader loader) {
      this.comparator = new FunctionComparator(matcher);
      this.finder = new ClosureFunctionFinder(comparator, extractor, loader);
      this.extractor = extractor;
   }
   
   public Score toType(Type actual, Type constraint) throws Exception {
      if(!actual.equals(constraint)) {
         Set<Type> list = extractor.getTypes(actual);
         
         if(list.isEmpty()) {
            return INVALID;
         }
         if(list.contains(constraint)) {
            return SIMILAR;
         }
         Category category = actual.getCategory();
         
         if(category.isFunction()) {
            return toFunction(constraint, actual);
         }
         return INVALID;
      }
      return EXACT;
   }
   
   public Score toType(Type actual, Type constraint, Object value) throws Exception {
      if(!actual.equals(constraint)) {
         Set<Type> list = extractor.getTypes(actual);
         
         if(list.isEmpty()) {
            return INVALID;
         }
         if(list.contains(constraint)) {
            return SIMILAR;
         }
         Category category = actual.getCategory();
         
         if(category.isFunction()) {
            return toFunction(constraint, actual);
         }
         return INVALID;
      }
      return EXACT;
   }
   
   public Score toFunction(Type actual, Type constraint) throws Exception {
      Category category = constraint.getCategory();
      
      if(category.isFunction()) {
         Function possible = finder.findFunctional(actual);
         List<Function> functions = constraint.getFunctions();
         Iterator<Function> iterator = functions.iterator();
         
         if(iterator.hasNext()) {
            Function required = iterator.next();
         
            if(possible != null) {         
               return comparator.compare(possible, required);         
            }
         }
      }
      return INVALID;
   }
   
   public Score toFunction(Type actual, Type constraint, Object value) throws Exception {
      Type type = extractor.getType(value);
      Category category = type.getCategory();
      
      if(category.isFunction()) {
         Function possible = finder.findFunctional(type);
         List<Function> functions = constraint.getFunctions();
         Iterator<Function> iterator = functions.iterator();
         
         if(iterator.hasNext()) {
            Function required = iterator.next();
            
            if(possible != null) {
               return comparator.compare(possible, required);
            }
         }
      }
      return INVALID;
   }
}