package org.snapscript.core.type.index;

import static org.snapscript.core.constraint.Constraint.TYPE;

import java.util.ArrayList;
import java.util.List;

import org.snapscript.core.error.InternalStateException;
import org.snapscript.core.property.ClassProperty;
import org.snapscript.core.property.Property;
import org.snapscript.core.type.Type;

public class ClassPropertyBuilder {

   private final TypeIndexer indexer;
   
   public ClassPropertyBuilder(TypeIndexer indexer){
      this.indexer = indexer;
   }

   public List<Property> create(Class source) throws Exception {
      Type type = indexer.loadType(source);
      
      if(type == null) {
         throw new InternalStateException("Could not load type for " + source);
      }
      List<Property> properties = new ArrayList<Property>();
      Property property = new ClassProperty(type, TYPE);
      
      properties.add(property);
      
      return properties;        
   }
}