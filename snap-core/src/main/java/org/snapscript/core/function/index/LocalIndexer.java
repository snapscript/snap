package org.snapscript.core.function.index;

import java.util.List;

import org.snapscript.core.ModifierType;
import org.snapscript.core.convert.Score;
import org.snapscript.core.function.ArgumentConverter;
import org.snapscript.core.function.Function;
import org.snapscript.core.function.Signature;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.scope.index.LocalScopeFinder;
import org.snapscript.core.stack.ThreadStack;
import org.snapscript.core.type.Type;
import org.snapscript.core.variable.Value;

public class LocalIndexer {
   
   private final LocalFunctionIndexer indexer;
   private final LocalScopeFinder finder;
   private final ThreadStack stack;
   
   public LocalIndexer(ThreadStack stack, FunctionIndexer indexer) {
      this.indexer = new LocalFunctionIndexer(indexer);
      this.finder = new LocalScopeFinder();
      this.stack = stack;
   }
   
   public FunctionPointer index(Scope scope, String name, Type... types) throws Exception { // match function variable    
      Value value = finder.findFunction(scope, name);
      
      if(value != null) {
         Type type = value.getType(scope);
         
         if(type != null) {
            List<Function> functions = type.getFunctions();
            int modifiers = type.getModifiers();
            
            if(ModifierType.isFunction(modifiers) && !functions.isEmpty()) {
               Function function = functions.get(0);
               Signature signature = function.getSignature();
               ArgumentConverter match = signature.getConverter();
               Score score = match.score(types);
               
               if(score.isValid()) {
                  return new TracePointer(function, stack);
               }
            }
         }
      }
      return indexer.index(scope, name, types);
   }
   
   public FunctionPointer index(Scope scope, String name, Object... values) throws Exception { // match function variable
      Value value = finder.findFunction(scope, name);
      
      if(value != null) {
         Object object = value.getValue();
         
         if(object != null) {
            if(Function.class.isInstance(object)) {
               Function function = (Function)object;
               Signature signature = function.getSignature();
               ArgumentConverter match = signature.getConverter();
               Score score = match.score(values);
               
               if(score.isValid()) {
                  return new TracePointer(function, stack);
               }
            }
         }
      }
      return indexer.index(scope, name, values);
   }
}