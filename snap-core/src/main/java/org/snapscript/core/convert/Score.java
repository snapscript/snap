package org.snapscript.core.convert;

import org.snapscript.core.Bug;

public class Score implements Comparable<Score> {
   
   public static final Score EXACT = new Score(100, true);
   public static final Score SIMILAR = new Score(70, true);
   public static final Score COMPATIBLE = new Score(20, true);
   public static final Score TRANSIENT = new Score(20, false);
   public static final Score POSSIBLE = new Score(10, true);
   public static final Score INVALID = new Score(0, true);
   
   public static Score sum(Score left, Score right) {
      return new Score(left.score + right.score, left.cache && right.cache);
   }
   
   public static Score average(Score left, Score right) {
      return new Score((left.score + right.score) / 2, left.cache && right.cache);
   }

   private final boolean cache;
   private final Double score;
   
   public Score(double score) {
      this(score, true);
   }
   
   public Score(double score, boolean cache) {
      this.score = score;
      this.cache = cache;
   }
   
   public double getScore() {
      return score;
   }
   
   @Bug("change this to isAbsolute to match the Signature.isAbsolute")
   public boolean isFinal() {
      return cache;
   }
   
   public boolean isInvalid() {
      return score <= 0;
   }
   
   public boolean isValid() {
      return score > 0;
   }
   
   @Override
   public int compareTo(Score other) {
      return score.compareTo(other.score);
   }
   
   @Override
   public String toString() {
      return score.toString();
   }
}