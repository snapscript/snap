package org.snapscript.tree.function;

import java.util.List;

import org.snapscript.core.Bug;
import org.snapscript.core.InternalStateException;
import org.snapscript.core.Local;
import org.snapscript.core.Scope;
import org.snapscript.core.State;
import org.snapscript.core.Type;
import org.snapscript.core.convert.CompatibilityChecker;
import org.snapscript.core.function.Parameter;
import org.snapscript.core.function.Signature;

public class ParameterExtractor {
   
   private final CompatibilityChecker checker;
   private final Signature signature;
   
   @Bug("this crap is for the SuperAllocator...")
   private final boolean global;
   
   public ParameterExtractor(Signature signature) {
      this(signature, false);
   }
   
   public ParameterExtractor(Signature signature, boolean global) {
      this.checker = new CompatibilityChecker();
      this.signature = signature;
      this.global = global;
   }
   
   public void compile(Scope scope) throws Exception {
      List<Parameter> parameters = signature.getParameters();
      int size = parameters.size();
      
      if(size > 0) {
         State state = scope.getState();
         
         for(int i = 0; i < size; i++) {
            Parameter parameter = parameters.get(i);
            String name = parameter.getName();

            state.addLocal(name);
         }
      }
   }

   public Scope extract(Scope scope, Object[] arguments) throws Exception {
      List<Parameter> parameters = signature.getParameters();
      Scope inner = scope.getInner();
      int size = parameters.size();
      
      if(size > 0) {
         State state = inner.getState();
         
         for(int i = 0; i < size; i++) {
            Parameter parameter = parameters.get(i);
            String name = parameter.getName();
            Object argument = arguments[i];
            Local local = create(inner, argument, i);
            
            if(global) {
               state.addScope(name, local);
            }
            state.addLocal(i, local);
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