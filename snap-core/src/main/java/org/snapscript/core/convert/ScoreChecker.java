package org.snapscript.core.convert;

public class ScoreChecker {

   private final Class[] types;
   private final Score[] scores;
   
   public ScoreChecker(Class[] types, Score[] scores) {
      this.types = types;
      this.scores = scores;
   }
   
   public Score score(Class type) {
      for(int i = 0; i < types.length; i++) {
         Class next = types[i];
         
         if(type == next) {
            return scores[i];
         }
      }
      return null;
   }
}
