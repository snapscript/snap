package org.snapscript.tree.define;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.snapscript.core.function.Function;
import org.snapscript.core.property.Property;
import org.snapscript.core.type.Type;
import org.snapscript.core.type.index.FunctionPropertyCollector;

public class FunctionPropertyGenerator {
   
   private final FunctionPropertyCollector collector;
   
   public FunctionPropertyGenerator() {
      this.collector = new FunctionPropertyCollector();
   }
   
   public void generate(Type type) throws Exception {
      List<Function> functions = type.getFunctions();
      List<Property> properties = type.getProperties();
      
      if(!functions.isEmpty()) {
         Set<String> ignore = new HashSet<String>();
         
         for(Property property : properties) {
            String name = property.getName();
            
            if(name != null) {
               ignore.add(name);
            }
         }
         List<Property> extended = collector.collect(functions, ignore);
         
         if(!extended.isEmpty()) {
            properties.addAll(extended);
         }
      }
   }
}