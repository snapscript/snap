package org.snapscript.parse;

import java.util.ArrayList;
import java.util.List;

public class GrammarIndexer {
   
   private final List<String> literals;
   private final List<String> values;

   public GrammarIndexer() {
      this.literals = new ArrayList<String>();
      this.values = new ArrayList<String>();
   }
   
   public List<String> list(){
      return literals;
   }
   
   public String literal(String value) {
      int index = literals.indexOf(value);
      
      if (index == -1) {
         literals.add(value);
         return value;
      }
      return literals.get(index);
   }

   public String resolve(int index) {
      return literals.get(index);
   }

   public int index(String value) {
      int index = values.indexOf(value);
      int size = values.size();
      
      if (index == -1) {
         values.add(value);
         return size;
      }
      return index;
   }

   public String value(int index) {
      return values.get(index);
   }
}