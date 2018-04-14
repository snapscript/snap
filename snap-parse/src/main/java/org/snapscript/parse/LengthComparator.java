package org.snapscript.parse;

import java.util.Comparator;

public class LengthComparator implements Comparator<String>{
   
   public LengthComparator() {
      super();
   }

   @Override
   public int compare(String left, String right) {
      int leftLength = left.length();
      int rightLength = right.length();
      
      if(leftLength < rightLength) {
         return 1;
      }
      if(leftLength == rightLength) {
         return 0;
      }
      return -1; 
   }
}