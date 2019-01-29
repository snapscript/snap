package org.snapscript.core.type.extend;

import static java.math.RoundingMode.CEILING;
import static java.math.RoundingMode.FLOOR;

import java.math.BigDecimal;
import java.math.BigInteger;

public class BigDecimalExtension implements NumberExtension<BigDecimal> {

   public BigDecimalExtension() {
      super();
   }

   @Override
   public BigDecimal abs(BigDecimal number) {
      return number.abs();
   }

   @Override
   public BigDecimal ceil(BigDecimal number) {
      return number.setScale(0, CEILING);
   }

   @Override
   public BigDecimal floor(BigDecimal number) {
      return number.setScale(0, FLOOR);
   }

   @Override
   public BigInteger round(BigDecimal number) {
      return number.toBigInteger();
   }
}
