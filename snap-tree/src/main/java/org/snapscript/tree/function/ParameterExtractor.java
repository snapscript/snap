package org.snapscript.tree.function;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.snapscript.core.Index;
import org.snapscript.core.InternalStateException;
import org.snapscript.core.Local;
import org.snapscript.core.Scope;
import org.snapscript.core.State;
import org.snapscript.core.Table;
import org.snapscript.core.Type;
import org.snapscript.core.convert.CompatibilityChecker;
import org.snapscript.core.function.Parameter;
import org.snapscript.core.function.Signature;

public class ParameterExtractor {
   
   private final CompatibilityChecker checker;
   private final Signature signature;
   private final boolean closure;
   
   public ParameterExtractor(Signature signature) {
      this(signature, false);
   }
   
   public ParameterExtractor(Signature signature, boolean closure) {
      this.checker = new CompatibilityChecker();
      this.signature = signature;
      this.closure = closure;
   }
   
   public void compile(Scope scope) throws Exception {
      List<Parameter> parameters = signature.getParameters();
      int size = parameters.size();
      
      if(size > 0) {
         Index index = scope.getIndex();
         
         for(int i = 0; i < size; i++) {
            Parameter parameter = parameters.get(i);
            String name = parameter.getName();

            index.index(name);
         }
      }
   }

   public Scope extract(Scope scope, Object[] arguments) throws Exception {
      List<Parameter> parameters = signature.getParameters();
      Scope inner = scope.getStack();
      int size = parameters.size();
      
      if(size > 0) {
         State state = inner.getState();
         Table table = inner.getTable();
         
         for(int i = 0; i < size; i++) {
            Parameter parameter = parameters.get(i);
            String name = parameter.getName();
            Object argument = arguments[i];
            Local local = create(inner, argument, i);
            
            if(closure) {
               state.add(name, local); 
            }
            table.add(i, local);
         }
         return inner;
      }
      return inner;
   }

   private Local create(Scope scope, Object value, int index) throws Exception {
      List<Parameter> parameters = signature.getParameters();
      Parameter parameter = parameters.get(index);
      Type type = parameter.getType();
      String name = parameter.getName();
      int length = parameters.size();
      
      if(index >= length -1) {
         if(signature.isVariable()) {
            Object[] list = (Object[])value;
            
            for(int i = 0; i < list.length; i++) {
               Object entry = list[i];
               
               if(!checker.compatible(scope, entry, type)) {
                  throw new InternalStateException("Parameter '" + name + "...' does not match constraint '" + type + "'");
               }
            }
            return Local.getReference(value, name, type);
         }
      }
      if(!checker.compatible(scope, value, type)) {
         throw new InternalStateException("Parameter '" + name + "' does not match constraint '" + type + "'");
      }
      return Local.getReference(value, name, type);         
   }
}