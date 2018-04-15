package org.snapscript.core.convert;

import static org.snapscript.core.Reserved.ANY_TYPE;
import static org.snapscript.core.Reserved.TYPE_CONSTRUCTOR;

import java.lang.reflect.Proxy;

import org.snapscript.core.Context;
import org.snapscript.core.function.Function;
import org.snapscript.core.module.Module;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.type.AnyLoader;
import org.snapscript.core.type.Category;
import org.snapscript.core.type.Type;

public class TypeInspector {
   
   private final AnyLoader loader;

   public TypeInspector() {
      this.loader = new AnyLoader();
   }
   
   public boolean isAny(Type type) {
      String name = type.getName();
      
      if(name.equals(ANY_TYPE)) {
         Scope scope = type.getScope();
         Type base = loader.loadType(scope);
         
         return type == base;
      }
      return false; // null is valid
   }
   
   public boolean isProxy(Type type) {
      Class real = type.getType();
      
      if(real != null) { 
         return Proxy.class.isAssignableFrom(real);
      }
      return false; // null is valid
   }

   public boolean isArray(Type type) throws Exception {
      Category category = type.getCategory();
      
      if(category.isArray()){
         return true;
      }
      return false;
   }
   
   public boolean isFunction(Type type) throws Exception {
      Category category = type.getCategory();

      if(category.isFunction()){
         return true;
      }
      return false;
   }   

   public boolean isSame(Type type, Class require) throws Exception {
      Class actual = type.getType();
      
      if(actual == require) {
         return true;
      }
      return false;
   }
   
   public boolean isConstructor(Type type, Function function) {
      Type parent = function.getType();
      String name = function.getName();
      
      if(name.equals(TYPE_CONSTRUCTOR)) {
         return parent == type;
      }
      return false;
   }
   
   public boolean isSuperConstructor(Type type, Function function) {
      Type parent = function.getType();
      String name = function.getName();
      
      if(name.equals(TYPE_CONSTRUCTOR)) {
         return parent != type;
      }
      return false;
   }
   
   public boolean isCompatible(Type type, Object value) throws Exception {
      if(type != null) {
         Module module = type.getModule();
         Context context = module.getContext();
         ConstraintMatcher matcher = context.getMatcher();
         ConstraintConverter converter = matcher.match(type);
         Score score = converter.score(value);
         
         return score.isValid();
      }
      return true;
   }
}