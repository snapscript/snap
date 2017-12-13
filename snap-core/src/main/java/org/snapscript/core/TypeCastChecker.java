package org.snapscript.core;

import static org.snapscript.core.convert.Score.EXACT;
import static org.snapscript.core.convert.Score.INVALID;
import static org.snapscript.core.convert.Score.SIMILAR;

import java.util.Set;

import org.snapscript.core.convert.ConstraintMatcher;
import org.snapscript.core.convert.FunctionComparator;
import org.snapscript.core.convert.Score;
import org.snapscript.core.function.Function;
import org.snapscript.core.function.FunctionFinder;

public class TypeCastChecker {

   private final FunctionComparator comparator;
   private final FunctionFinder finder;
   private final TypeExtractor extractor;
   private final TypeLoader loader;
   
   public TypeCastChecker(ConstraintMatcher matcher, TypeExtractor extractor, TypeLoader loader) {
      this.finder = new FunctionFinder(extractor, loader);
      this.comparator = new FunctionComparator(matcher);
      this.extractor = extractor;
      this.loader = loader;
   }
   
   public Score cast(Class actual, Type constraint) throws Exception {
      Type type = loader.loadType(actual);

      if(!actual.equals(constraint)) {
         return cast(type, constraint);
      }
      return EXACT;
   }
   
   public Score cast(Type actual, Type constraint) throws Exception {
      if(!actual.equals(constraint)) {
         Set<Type> list = extractor.getTypes(actual);
         
         if(list.isEmpty()) {
            return INVALID;
         }
         if(list.contains(constraint)) {
            return SIMILAR;
         }
         return INVALID;
      }
      return EXACT;
   }
   
   public Score cast(Type type, Type constraint, Object value) throws Exception {
      if(Function.class.isInstance(value)) {
         Class real = constraint.getType(); // this is the functional type interface
         Function require = null;
         
         if(real != null) {
            require = finder.find(real);
         } else {
            require = finder.find(constraint);
         }
         if(require != null) {
            return comparator.compare((Function)value, require);
         }
      }
      return cast(type, constraint);
   }
}