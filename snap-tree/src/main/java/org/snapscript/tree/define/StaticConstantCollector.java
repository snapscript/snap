package org.snapscript.tree.define;

import static org.snapscript.core.Reserved.TYPE_CLASS;

import java.util.List;
import java.util.Set;

import org.snapscript.core.Context;
import org.snapscript.core.ModifierType;
import org.snapscript.core.module.Module;
import org.snapscript.core.property.Property;
import org.snapscript.core.property.PropertyValue;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.scope.ScopeState;
import org.snapscript.core.type.Type;
import org.snapscript.core.type.TypeExtractor;

public class StaticConstantCollector {

   private final StaticConstantIndexer indexer;
   
   public StaticConstantCollector() {
      this.indexer = new StaticConstantIndexer(TYPE_CLASS);
   }
   
   public void collect(Type type) throws Exception {
      Module module = type.getModule();
      Context context = module.getContext();
      TypeExtractor extractor = context.getExtractor();
      Set<Type> types = extractor.getTypes(type); // get hierarchy
      
      if(!types.isEmpty()) {
         Set<String> names = indexer.index(type);
         Scope scope = type.getScope();
         ScopeState state = scope.getState();
   
         for(Type next : types) {
            if(next != type) {
               List<Property> properties = next.getProperties();
               
               for(Property property : properties) {
                  String name = property.getName();
                  String alias = property.getAlias();
                  int modifiers = property.getModifiers();
                  
                  if(ModifierType.isStatic(modifiers)) {
                     PropertyValue value = new PropertyValue(property, null, name);
                     
                     if(names.add(alias)) { // ensure only supers are added
                        state.addValue(alias, value);
                     }
                  }
               }
            }
         }
      }
   }
}