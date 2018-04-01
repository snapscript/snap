package org.snapscript.core.function;

import java.lang.reflect.Member;
import java.util.Collections;
import java.util.List;

import org.snapscript.core.type.Type;
import org.snapscript.core.convert.NoArgumentConverter;

public class EmptySignature implements Signature {

   private final ArgumentConverter converter;
   
   public EmptySignature() {
      this.converter = new NoArgumentConverter();
   }
   
   @Override
   public ArgumentConverter getConverter() {
      return converter;
   }

   @Override
   public List<Parameter> getParameters() {
      return Collections.emptyList();
   }

   @Override
   public Type getDefinition() {
      return null;
   }

   @Override
   public Member getSource() {
      return null;
   }

   @Override
   public boolean isVariable() {
      return false;
   }

   @Override
   public boolean isAbsolute() {
      return true;
   }
   
   @Override
   public boolean isInvalid() {
      return true;
   }
}