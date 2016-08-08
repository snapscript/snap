package org.snapscript.tree.variable;

import static org.snapscript.core.Reserved.PROPERTY_LENGTH;

import java.lang.reflect.Array;

import org.snapscript.core.Scope;
import org.snapscript.core.TypeTraverser;
import org.snapscript.core.Value;
import org.snapscript.core.ValueType;

public class ArrayResolver implements ValueResolver<Object> {
   
   private final ObjectResolver resolver;
   private final String name;
   
   public ArrayResolver(TypeTraverser extractor, String name) {
      this.resolver = new ObjectResolver(extractor, name);
      this.name = name;
   }
   
   @Override
   public Value resolve(Scope scope, Object left) {
      if(name.equals(PROPERTY_LENGTH)) {
         int length = Array.getLength(left);
         return ValueType.getConstant(length);
      }
      return resolver.resolve(scope, left);
   }
}