package org.snapscript.core.type.extend;

public class DoubleExtension implements NumberExtension<Double> {

   public DoubleExtension() {
      super();
   }

   @Override
   public Double abs(Double number) {
      return Math.abs(number);
   }

   @Override
   public Double ceil(Double number) {
      return Math.ceil(number);
   }

   @Override
   public Double floor(Double number) {
      return Math.floor(number);
   }

   @Override
   public Long round(Double number) {
      return Math.round(number);
   }
}
