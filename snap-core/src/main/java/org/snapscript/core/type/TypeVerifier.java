package org.snapscript.core.type;

import org.snapscript.core.type.Type;
import org.snapscript.core.convert.Score;

public class TypeVerifier {
   
   private final TypeCastChecker checker;
   private final TypeLoader loader;
   
   public TypeVerifier(TypeLoader loader, TypeCastChecker checker) {
      this.checker = checker;
      this.loader = loader;
   }

   public boolean isSame(Type type, Class require) throws Exception {
      Class actual = type.getType();
      
      if(actual == require) {
         return true;
      }
      return false;
   }
   
   public boolean isLike(Type type, Class require) throws Exception {
      Type actual = loader.loadType(require);
      Score score = checker.toType(type, actual);
      
      return score.isValid();
   }
   
   public boolean isFunction(Type type) throws Exception {
      Category category = type.getCategory();

      if(category.isFunction()){
         return true;
      }
      return false;
   }
   
   public boolean isArray(Type type) throws Exception {
      Category category = type.getCategory();
      
      if(category.isArray()){
         return true;
      }
      return false;
   }
}