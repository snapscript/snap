package org.snapscript.core.scope.extract;

import static org.snapscript.core.scope.extract.ScopePolicy.COMPILE_EVALUATE;

import java.util.List;

import org.snapscript.core.Context;
import org.snapscript.core.ModifierType;
import org.snapscript.core.function.Function;
import org.snapscript.core.module.Module;
import org.snapscript.core.property.Property;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.scope.ScopeState;
import org.snapscript.core.stack.ThreadStack;
import org.snapscript.core.type.Type;
import org.snapscript.core.variable.Value;

public class EvaluationExtractor implements ScopeExtractor{
   
   private final ScopePolicyExtractor extractor;
   
   public EvaluationExtractor() {
      this.extractor = new ScopePolicyExtractor(COMPILE_EVALUATE);
   }
   
   @Override
   public Scope extract(Scope original) {
      Module module = original.getModule();
      Context context = module.getContext();
      ThreadStack stack = context.getStack();
      Function function = stack.current(); // we can determine the function type    
      
      return extract(original, function);
   }
   
   public Scope extract(Scope original, Function function) {
      Scope extracted = extractor.extract(original);      
      
      if(function != null) {
         Type source = function.getSource();
         ScopeState state = original.getState();
         ScopeState update = extracted.getState();
         List<Property> properties = source.getProperties();
         
         for(Property property : properties) {
            int modifiers = property.getModifiers();
            
            if(ModifierType.isPrivate(modifiers)) {
               String name = property.getName();
               String alias = property.getAlias();
               Value value = state.getValue(alias);
               Value current = update.getValue(name);
               
               if(current == null) {
                  update.addValue(name, value);
               }
            }
         }
      }
      return extracted;
   } 
}
