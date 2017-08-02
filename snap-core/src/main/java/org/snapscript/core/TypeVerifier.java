package org.snapscript.core;

import static org.snapscript.core.convert.Score.INVALID;

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
      Score score = checker.cast(type, actual);
      
      return score.compareTo(INVALID) > 0;
   }
   
   public boolean isArray(Type type) throws Exception {
      Type entry = type.getEntry();
      
      if(entry != null) {
         return true;
      }
      return false;
   }
}