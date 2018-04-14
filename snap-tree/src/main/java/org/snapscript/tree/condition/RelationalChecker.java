package org.snapscript.tree.condition;

import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.snapscript.core.Context;
import org.snapscript.core.module.Module;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.scope.State;
import org.snapscript.core.type.Type;
import org.snapscript.core.type.TypeExtractor;
import org.snapscript.core.variable.Value;

public class RelationalChecker {
   
   public RelationalChecker() {
     super();
   }
   
   public boolean isInstance(Scope scope, Object left, Object right) {
      if(right != null && left != null) {
         try {
            Module module = scope.getModule();
            Context context = module.getContext();
            TypeExtractor extractor = context.getExtractor();
            Type actual = extractor.getType(left);
            Set<Type> types = extractor.getTypes(actual);
            Type require = (Type)right;
            
            return types.contains(require);
         }catch(Exception e) {
            return false;
         }
      }
      return false;
   }
   
   public boolean isMatch(Scope scope, Object left, Object right) {
      if(right != null && left != null) {
         try {
            Pattern pattern = (Pattern)right;
            String text = String.valueOf(left);
            Matcher matcher = pattern.matcher(text);
        
            if(matcher.matches()) {
               State state = scope.getState();
               int count = matcher.groupCount();
            
               for(int i = 0; i <= count; i++) {
                  String index = String.valueOf(i);
                  String match = matcher.group(i);
                  Value value = Value.getTransient(match);
                  
                  state.add(index, value);
               }
               return true;
            }
         }catch(Exception e) {
            return false;
         }
      }
      return false;
   }
}