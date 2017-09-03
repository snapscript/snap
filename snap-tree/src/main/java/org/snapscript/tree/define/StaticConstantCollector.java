package org.snapscript.tree.define;

import static org.snapscript.core.Reserved.TYPE_CLASS;
import static org.snapscript.core.Reserved.TYPE_THIS;

import java.util.List;
import java.util.Set;

import org.snapscript.core.Context;
import org.snapscript.core.ModifierType;
import org.snapscript.core.Module;
import org.snapscript.core.Scope;
import org.snapscript.core.State;
import org.snapscript.core.Type;
import org.snapscript.core.TypeExtractor;
import org.snapscript.core.property.Property;
import org.snapscript.core.property.PropertyValue;

public class StaticConstantCollector {

   private final StaticConstantIndexer indexer;
   
   public StaticConstantCollector() {
      this.indexer = new StaticConstantIndexer(TYPE_THIS, TYPE_CLASS);
   }
   
   public void collect(Type type) throws Exception {
      Module module = type.getModule();
      Context context = module.getContext();
      TypeExtractor extractor = context.getExtractor();
      Set<Type> types = extractor.getTypes(type); // get hierarchy
      
      if(!types.isEmpty()) {
         Set<String> names = indexer.index(type);
         Scope scope = type.getScope();
         State state = scope.getState();
   
         for(Type next : types) {
            if(next != type) {
               List<Property> properties = next.getProperties();
               
               for(Property property : properties) {
                  String name = property.getName();
                  int modifiers = property.getModifiers();
                  
                  if(ModifierType.isStatic(modifiers)) {
                     PropertyValue value = new PropertyValue(property, null, name);
                     
                     if(names.add(name)) {
                        state.addScope(name, value);
                     }
                  }
               }
            }
         }
      }
   }
}