package org.snapscript.core.index;

import static org.snapscript.core.Reserved.ANY_TYPE;
import static org.snapscript.core.Reserved.DEFAULT_PACKAGE;

import java.util.ArrayList;
import java.util.List;

import org.snapscript.core.Type;

public class ClassHierarchyIndexer {

   private final TypeIndexer indexer;
   
   public ClassHierarchyIndexer(TypeIndexer indexer) {
      this.indexer = indexer;
   }
   
   public List<Type> index(Class source) throws Exception {
      List<Type> hierarchy = new ArrayList<Type>();
      
      if(source == Object.class) {
         Type base = indexer.defineType(DEFAULT_PACKAGE, ANY_TYPE);
         
         if(base != null) {
            hierarchy.add(base);
         }
      } else {
         Class[] interfaces = source.getInterfaces();
         Class base = source.getSuperclass(); // the super class
         
         if(base != null) {
            Type type = indexer.loadType(base); // the super type
         
            if(type != null) {
               hierarchy.add(type);
            }
         }
         for (Class entry : interfaces) {
            Type type = indexer.loadType(entry);
            
            if(type != null) {
               hierarchy.add(type);
            }
         }
      }
      return hierarchy;
   }
}
