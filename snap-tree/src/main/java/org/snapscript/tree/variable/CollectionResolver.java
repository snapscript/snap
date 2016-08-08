package org.snapscript.tree.variable;

import static org.snapscript.core.Reserved.PROPERTY_LENGTH;

import java.util.Collection;

import org.snapscript.core.Scope;
import org.snapscript.core.TypeTraverser;
import org.snapscript.core.Value;
import org.snapscript.core.ValueType;

public class CollectionResolver implements ValueResolver<Collection> {
   
   private final ObjectResolver resolver;
   private final String name;
   
   public CollectionResolver(TypeTraverser extractor, String name) {
      this.resolver = new ObjectResolver(extractor, name);
      this.name = name;
   }
   
   @Override
   public Value resolve(Scope scope, Collection left) {
      if(name.equals(PROPERTY_LENGTH)) {
         int length = left.size();
         return ValueType.getConstant(length);
      }
      return resolver.resolve(scope, left);
   }
}