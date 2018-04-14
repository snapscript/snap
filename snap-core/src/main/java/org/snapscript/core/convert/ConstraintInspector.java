package org.snapscript.core.convert;

import org.snapscript.core.type.CastChecker;
import org.snapscript.core.type.Category;
import org.snapscript.core.type.Type;
import org.snapscript.core.type.TypeLoader;

public class ConstraintInspector {
   
   private final CastChecker checker;
   private final TypeLoader loader;
   
   public ConstraintInspector(TypeLoader loader, CastChecker checker) {
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