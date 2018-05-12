package org.snapscript.core.attribute;

import java.util.concurrent.atomic.AtomicReference;

import org.snapscript.core.scope.Scope;

public class AttributeTypeBinder {
   
   private final AtomicReference<AttributeType> reference;
   private final AttributeTypeBuilder resolver;
   
   public AttributeTypeBinder(Attribute attribute) {
      this.reference = new AtomicReference<AttributeType>();
      this.resolver = new AttributeTypeBuilder(attribute); 
   }
   
   public AttributeType bind(Scope scope) {
      AttributeType type = reference.get();
      
      if(type == null) {
         type = resolver.create(scope);
         reference.set(type);
      }
      return type;
   }



}
