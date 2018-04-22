package org.snapscript.core.type.index;

import static org.snapscript.core.Reserved.ANY_TYPE;
import static org.snapscript.core.Reserved.DEFAULT_PACKAGE;

import java.util.ArrayList;
import java.util.List;

import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.type.Type;

public class ClassHierarchyIndexer {

   private final TypeIndexer indexer;
   
   public ClassHierarchyIndexer(TypeIndexer indexer) {
      this.indexer = indexer;
   }
   
   public List<Constraint> index(Class source) throws Exception {
      List<Constraint> hierarchy = new ArrayList<Constraint>();
      
      if(source == Object.class) {
         Type base = indexer.loadType(DEFAULT_PACKAGE, ANY_TYPE);
         Constraint constraint = Constraint.getConstraint(base);

         hierarchy.add(constraint);
      } else {
         Class[] interfaces = source.getInterfaces();
         Class base = source.getSuperclass(); // the super class
         
         if(base != null) {
            Constraint constraint = Constraint.getConstraint(base);
            hierarchy.add(constraint);
         }
         for (Class entry : interfaces) {
            Constraint constraint = Constraint.getConstraint(entry);
            hierarchy.add(constraint);
         }
      }
      return hierarchy;
   }
}