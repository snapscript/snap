package org.snapscript.tree.define;

import static org.snapscript.core.Reserved.TYPE_CLASS;
import static org.snapscript.core.Reserved.TYPE_SUPER;
import static org.snapscript.core.Reserved.TYPE_THIS;

import java.util.List;
import java.util.Set;

import org.snapscript.core.ModifierType;
import org.snapscript.core.Scope;
import org.snapscript.core.State;
import org.snapscript.core.Type;
import org.snapscript.core.TypeTraverser;
import org.snapscript.core.property.Property;
import org.snapscript.core.property.PropertyValue;

public class StaticConstantCollector {

   private final StaticConstantIndexer indexer;
   private final TypeTraverser traverser;
   
   public StaticConstantCollector() {
      this.indexer = new StaticConstantIndexer(TYPE_THIS, TYPE_SUPER, TYPE_CLASS);
      this.traverser = new TypeTraverser();
   }
   
   public void collect(Type type) {
      Set<Type> types = traverser.traverse(type); // get hierarchy
      Set<String> names = indexer.index(type);
      Scope scope = type.getScope();
      State state = scope.getState();

      for(Type base : types) {
         if(type != base) {
            List<Property> properties = base.getProperties();
            
            for(Property property : properties) {
               String name = property.getName();
               int modifiers = property.getModifiers();
               
               if(ModifierType.isStatic(modifiers)) {
                  PropertyValue value = new PropertyValue(property, null, name);
                  
                  if(names.add(name)) {
                     state.addVariable(name, value);
                  }
               }
            }
         }
      }
   }
}
