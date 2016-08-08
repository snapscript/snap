package org.snapscript.tree.construct;

import java.util.Map.Entry;

import org.snapscript.core.Evaluation;
import org.snapscript.core.InternalStateException;
import org.snapscript.core.Scope;
import org.snapscript.core.Value;

public class MapEntry {
   
   private final Evaluation value;
   private final Evaluation key;
   
   public MapEntry(Evaluation key, Evaluation value) {
      this.value = value;
      this.key = key;
   }
   
   public Entry create(Scope scope) throws Exception{
      Value valueResult = value.evaluate(scope, null);
      Value keyResult = key.evaluate(scope, null);
      Object valueObject = valueResult.getValue();
      Object keyObject = keyResult.getValue();
      
      return new Pair(keyObject, valueObject);
   }
   
   private class Pair implements Entry {
      
      private final Object value;
      private final Object key;
      
      public Pair(Object key, Object value) {
         this.value = value;
         this.key = key;
      }

      @Override
      public Object getKey() {
         return key;
      }

      @Override
      public Object getValue() {
         return value;
      }

      @Override
      public Object setValue(Object value) {
         throw new InternalStateException("Modification of constant entry '" + key + "'");
      }
      
   }
}