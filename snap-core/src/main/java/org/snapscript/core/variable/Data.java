package org.snapscript.core.variable;

import org.snapscript.core.type.Type;

public interface Data {
   char getCharacter();
   double getDouble();
   long getLong();
   int getInteger();   
   Number getNumber();
   String getString(); 
   Type getType();
   <T> T getValue();
}
