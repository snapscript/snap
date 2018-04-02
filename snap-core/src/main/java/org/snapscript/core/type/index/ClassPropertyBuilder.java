package org.snapscript.core.type.index;

import java.util.ArrayList;
import java.util.List;

import org.snapscript.core.InternalStateException;
import org.snapscript.core.type.Type;
import org.snapscript.core.property.ClassProperty;
import org.snapscript.core.property.Property;
import org.snapscript.core.property.ThisProperty;

public class ClassPropertyBuilder {

   private final TypeIndexer indexer;
   
   public ClassPropertyBuilder(TypeIndexer indexer){
      this.indexer = indexer;
   }

   public List<Property> create(Class source) throws Exception {
      Type type = indexer.loadType(source);
      Type constraint = indexer.loadType(Type.class);
      
      if(type == null) {
         throw new InternalStateException("Could not load type for " + source);
      }
      List<Property> properties = new ArrayList<Property>();
      Property thisProperty = new ThisProperty(type);
      Property classProperty = new ClassProperty(type, constraint);
      
      properties.add(thisProperty);
      properties.add(classProperty);
      
      return properties;        
   }
}