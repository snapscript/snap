package org.snapscript.parse;

import java.util.Comparator;

public class LengthComparator implements Comparator<String>{
   
   private boolean reverse;
   
   public LengthComparator() {
      this(false);
   }
   
   public LengthComparator(boolean reverse) {
      this.reverse = reverse;
   }

   @Override
   public int compare(String left, String right) {
      Integer leftLength = left.length();
      Integer rightLength = right.length();
      
      if(reverse) {
         return leftLength.compareTo(rightLength);
      }
      return rightLength.compareTo(leftLength);
   }
}
