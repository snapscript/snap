package org.snapscript.core.convert;

import static org.snapscript.core.convert.Score.EXACT;
import static org.snapscript.core.convert.Score.INVALID;
import static org.snapscript.core.convert.Score.SIMILAR;
import static org.snapscript.core.convert.Score.TRANSIENT;

import java.lang.reflect.Array;
import java.util.List;

import org.snapscript.core.InternalStateException;
import org.snapscript.core.Type;
import org.snapscript.core.TypeLoader;

public class ArrayConverter extends ConstraintConverter {

   private final ConstraintMatcher matcher;
   private final TypeLoader loader;
   private final Type type;
   
   public ArrayConverter(TypeLoader loader, ConstraintMatcher matcher, Type type) {
      this.matcher = matcher;
      this.loader = loader;
      this.type = type;
   }
   
   @Override
   public Score score(Type actual) throws Exception {
      if(type != null) {
         Class require = type.getType();
         Class real = actual.getType();
         
         if(require != real) {
            return INVALID; // this should be better!!
         }
      }
      return EXACT;
   }
   
   @Override
   public Score score(Object object) throws Exception {
      if(object != null) {
         Class require = type.getType();
         Class actual = object.getClass();
         
         if(actual.isArray()) {
            if(require != actual) {
               return score(object, require);
            }
            return EXACT;
         }
         if(List.class.isInstance(object)) {
            return score((List)object, require);
         }
         return INVALID;
      }
      return EXACT;
   }
   
   private Score score(Object list, Class type) throws Exception {
      if(type.isArray()) {   
         int length = Array.getLength(list);
         Class entry = type.getComponentType(); 
         Type require = loader.loadType(entry);
         ConstraintConverter converter = matcher.match(require);
         
         if(length > 0) {
            Score total = TRANSIENT; // temporary
            
            for(int i = 0; i < length; i++) {
               Object element = Array.get(list, i);
               Score score = converter.score(element);
   
               if(score.compareTo(INVALID) == 0) {
                  return INVALID;
               }
               total = Score.average(score, total);
            }
            return total;
         }
         return SIMILAR;
      }
      return INVALID;
   }
   
   private Score score(List list, Class type) throws Exception {
      if(type.isArray()) {   
         int length = list.size();
         Class entry = type.getComponentType(); 
         Type require = loader.loadType(entry);
         ConstraintConverter converter = matcher.match(require);
         
         if(length > 0) {
            Score total = TRANSIENT; // temporary
            
            for(int i = 0; i < length; i++) {
               Object element = list.get(i);
               Score score = converter.score(element);
   
               if(score.compareTo(INVALID) == 0) {
                  return INVALID;
               }
               total = Score.average(score, total);
            }
            return total;
         }
         return SIMILAR; 
      }
      return INVALID;
   }
   
   @Override
   public Object convert(Object object) throws Exception {
      if(object != null) {
         Class require = type.getType();
         Class actual = object.getClass();
         
         if(actual.isArray()) {
            if(require != actual) {
               return convert(object, require);
            }
            return object;
         }
         if(List.class.isInstance(object)) {
            return convert((List)object, require);
         }
         throw new InternalStateException("Array can not be converted from " + actual);
      }
      return object;
   }
   
   private Object convert(Object list, Class type) throws Exception {
      if(type.isArray()) {   
         int length = Array.getLength(list);
         Class entry = type.getComponentType(); 
         Type require = loader.loadType(entry);
         ConstraintConverter converter = matcher.match(require);
         Object array = Array.newInstance(entry, length);   
         
         for(int i = 0; i < length; i++) {
            Object element = Array.get(list, i);
            
            if(element != null) {
               Score score = converter.score(element);
   
               if(score.compareTo(INVALID) == 0) {
                  throw new InternalStateException("Array element is not " + require);
               }
               element = converter.convert(element);
            }
            Array.set(array, i, element);
         }
         return array;
      }
      throw new InternalStateException("Array element is not a list");
   }
   
   private Object convert(List list, Class type) throws Exception {
      if(type.isArray()) {   
         int length = list.size();
         Class entry = type.getComponentType(); 
         Type require = loader.loadType(entry);
         ConstraintConverter converter = matcher.match(require);
         Object array = Array.newInstance(entry, length);   
         
         for(int i = 0; i < length; i++) {
            Object element = list.get(i);
            
            if(element != null) {
               Score score = converter.score(element);
   
               if(score.compareTo(INVALID) == 0) {
                  throw new InternalStateException("Array element is not " + require);
               }
               element = converter.convert(element);
            }
            Array.set(array, i, element);
         }
         return array;
      }
      throw new InternalStateException("Array element is not a list");
   }
}
