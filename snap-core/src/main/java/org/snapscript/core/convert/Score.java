/*
 * Score.java December 2016
 *
 * Copyright (C) 2016, Niall Gallagher <niallg@users.sf.net>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or 
 * implied. See the License for the specific language governing 
 * permissions and limitations under the License.
 */

package org.snapscript.core.convert;

public class Score implements Comparable<Score> {
   
   public static final Score EXACT = new Score(100, true);
   public static final Score SIMILAR = new Score(70, true);
   public static final Score COMPATIBLE = new Score(20, true);
   public static final Score TRANSIENT = new Score(20, false);
   public static final Score POSSIBLE = new Score(10, true);
   public static final Score INVALID = new Score(0, true);

   private final boolean cache;
   private final Double score;
   
   public Score(double score) {
      this(score, true);
   }
   
   public Score(double score, boolean cache) {
      this.score = score;
      this.cache = cache;
   }
   
   public boolean isFinal() {
      return cache;
   }
   
   @Override
   public int compareTo(Score other) {
      return score.compareTo(other.score);
   }
   
   @Override
   public String toString() {
      return score.toString();
   }
   
   public static Score sum(Score left, Score right) {
      return new Score(left.score + right.score, left.cache && right.cache);
   }
   
   public static Score average(Score left, Score right) {
      return new Score((left.score + right.score) / 2, left.cache && right.cache);
   }
}
