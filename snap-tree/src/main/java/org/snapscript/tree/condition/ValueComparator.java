/*
 * ValueComparator.java December 2016
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

package org.snapscript.tree.condition;

import org.snapscript.core.Value;

public enum ValueComparator {
   NUMERIC {
      @Override
      public int compare(Value left, Value right) {
         Double primary = left.getDouble();
         Double secondary = right.getDouble();

         if(primary != secondary) {      
            if(primary != null && secondary != null) {
               return primary.compareTo(secondary);
            }
            return primary == null ? -1 : 1;
         }
         return 0;
      }
   },
   COMPARABLE{
      @Override
      public int compare(Value left, Value right) {
         Comparable primary = left.getValue();
         Comparable secondary = right.getValue();

         if(primary != secondary) {
            if(primary != null && secondary != null) {
               return primary.compareTo(secondary);
            }
            return primary == null ? -1 : 1;
         }
         return 0;
      }
   },
   OBJECT{
      @Override
      public int compare(Value left, Value right) {
         Object primary = left.getValue();
         Object secondary = right.getValue();

         if(primary != secondary) {
            if(primary != null && secondary != null) {
               if(primary.equals(secondary)) {
                  return 0;
               }
               return -1;
            }
            return primary == null ? -1 : 1;
         }
         return 0;
      }
   };

   public abstract int compare(Value left, Value right);
   
   public static ValueComparator resolveComparator(Value left, Value right) {
      Object primary = left.getValue();
      Object secondary = right.getValue();

      if(primary != null && secondary != null) {
         if (primary instanceof Number && secondary instanceof Number) {
            return NUMERIC;
         }
         if (Comparable.class == primary && Comparable.class == secondary) {
            return COMPARABLE;
         }        
      }
      return OBJECT;
   }

}