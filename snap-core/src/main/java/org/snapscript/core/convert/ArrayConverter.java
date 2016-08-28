package org.snapscript.core.convert;

import static org.snapscript.core.convert.Score.EXACT;
import static org.snapscript.core.convert.Score.INVALID;
import static org.snapscript.core.convert.Score.SIMILAR;
import static org.snapscript.core.convert.Score.TRANSIENT;

import java.lang.reflect.Array;
import java.util.List;

import org.snapscript.core.InternalStateException;
import org.snapscript.core.Type;

public class ArrayConverter extends ConstraintConverter {

   private final ConstraintMatcher matcher;
   private final ProxyWrapper wrapper;
   private final Type type;
   
   public ArrayConverter(ConstraintMatcher matcher, ProxyWrapper wrapper, Type type) {
      this.wrapper = wrapper;
      this.matcher = matcher;
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
               return score(object, type);
            }
            return EXACT;
         }
         if(List.class.isInstance(object)) {
            return score((List)object, type);
         }
         return INVALID;
      }
      return EXACT;
   }
   
   private Score score(Object list, Type type) throws Exception {
      Type entry = type.getEntry();
      
      if(entry != null) {   
         int length = Array.getLength(list);
         ConstraintConverter converter = matcher.match(entry);
         
         if(length > 0) {
            Score total = TRANSIENT; // temporary
            
            for(int i = 0; i < length; i++) {
               Object element = Array.get(list, i);
               Object object = wrapper.fromProxy(element);
               Score score = converter.score(object);
   
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
   
   private Score score(List list, Type type) throws Exception {
      Type entry = type.getEntry();
      
      if(entry != null) {  
         int length = list.size();
         ConstraintConverter converter = matcher.match(entry);
         
         if(length > 0) {
            Score total = TRANSIENT; // temporary
            
            for(int i = 0; i < length; i++) {
               Object element = list.get(i);
               Object object = wrapper.fromProxy(element);
               Score score = converter.score(object);
   
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
               return convert(object, type);
            }
            return object;
         }
         if(List.class.isInstance(object)) {
            return convert((List)object, type);
         }
         throw new InternalStateException("Array can not be converted from " + actual);
      }
      return object;
   }
   
   private Object convert(Object list, Type type) throws Exception {
      Type entry = type.getEntry();
      
      if(entry != null) {   
         int length = Array.getLength(list);
         Class require = type.getType(); 
         Class component = require.getComponentType();
         ConstraintConverter converter = matcher.match(entry);
         Object array = Array.newInstance(component, length);   
         
         for(int i = 0; i < length; i++) {
            Object element = Array.get(list, i);
            Object object = wrapper.fromProxy(element);
            
            if(object != null) {
               Score score = converter.score(object);
   
               if(score.compareTo(INVALID) == 0) {
                  throw new InternalStateException("Array element is not '" + require + "'");
               }
               element = converter.convert(object);
            }
            Array.set(array, i, element);
         }
         return array;
      }
      throw new InternalStateException("Array element is not a list");
   }
   
   private Object convert(List list, Type type) throws Exception {
      Type entry = type.getEntry();
      
      if(entry != null) { 
         int length = list.size();
         Class require = type.getType();
         Class component = require.getComponentType();
         ConstraintConverter converter = matcher.match(entry);
         Object array = Array.newInstance(component, length); 
         
         for(int i = 0; i < length; i++) {
            Object element = list.get(i);
            Object object = wrapper.fromProxy(element);
            
            if(object != null) {
               Score score = converter.score(object);
   
               if(score.compareTo(INVALID) == 0) {
                  throw new InternalStateException("Array element is not '" + require + "'");
               }
               element = converter.convert(object);
            }
            Array.set(array, i, element);
         }
         return array;
      }
      throw new InternalStateException("Array element is not a list");
   }
}
