package org.snapscript.core.type.index;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.snapscript.core.constraint.Constraint;

public class GenericIndexer {
   
   private final GenericConstraintExtractor extractor;
   private final ClassBoundResolver resolver;
   
   public GenericIndexer(TypeIndexer indexer){
      this.extractor = new GenericConstraintExtractor(indexer);
      this.resolver = new ClassBoundResolver();
   }
   
   public List<Constraint> index(Class type) {
      ClassBound[] bounds = resolver.resolveType(type);
      
      if(bounds.length > 0) {
         List<Constraint> constraints = new ArrayList<Constraint>();
         
         for(int i = 0; i < bounds.length; i++) {       
            ClassBound bound = bounds[i];
            String name = bound.getName();
            Class actual = bound.getBound();
            Constraint constraint = extractor.extractType(actual, name);
            
            constraints.add(constraint);
         }
         return constraints;
      }
      return Collections.emptyList();
   }
}
