package org.snapscript.core.type.extend;

public class FloatExtension implements NumberExtension<Float> {

   public FloatExtension() {
      super();
   }

   @Override
   public Float abs(Float number) {
      return Math.abs(number);
   }

   @Override
   public Double ceil(Float number) {
      return Math.ceil(number);
   }

   @Override
   public Double floor(Float number) {
      return Math.floor(number);
   }

   @Override
   public Integer round(Float number) {
      return Math.round(number);
   }
}
