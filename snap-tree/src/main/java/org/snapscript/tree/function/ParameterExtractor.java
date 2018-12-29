package org.snapscript.tree.function;

import java.util.List;

import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.convert.TypeInspector;
import org.snapscript.core.error.InternalStateException;
import org.snapscript.core.function.Parameter;
import org.snapscript.core.function.Signature;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.scope.ScopeState;
import org.snapscript.core.scope.index.Address;
import org.snapscript.core.scope.index.AddressCache;
import org.snapscript.core.scope.index.ScopeIndex;
import org.snapscript.core.scope.index.Local;
import org.snapscript.core.scope.index.ScopeTable;
import org.snapscript.core.type.Type;

public class ParameterExtractor {

   private final TypeInspector inspector;
   private final Signature signature;
   private final boolean closure;
   
   public ParameterExtractor(Signature signature) {
      this(signature, false);
   }
   
   public ParameterExtractor(Signature signature, boolean closure) {
      this.inspector = new TypeInspector();
      this.signature = signature;
      this.closure = closure;
   }
   
   public void define(Scope scope) throws Exception {
      List<Parameter> parameters = signature.getParameters();
      int required = parameters.size();

      if(required > 0) {     
         ScopeIndex index = scope.getIndex();
         
         for(int i = 0; i < required; i++) {
            Parameter parameter = parameters.get(i);
            Address address = parameter.getAddress();
            String name = parameter.getName();
            
            if(!index.contains(name)) {
               Address created = index.index(name);
               int actual = created.getOffset();
               int require = address.getOffset();
               
               if(actual != require) {
                  throw new InternalStateException("Parameter '" + name + "' has an invalid address");
               }
            }
         }
      }
   }

   public Scope extract(Scope scope, Object[] arguments) throws Exception {
      List<Parameter> parameters = signature.getParameters();
      List<Constraint> generics = signature.getGenerics();
      Scope inner = scope.getStack();    
      int required = parameters.size();
      int optional = generics.size();

      if(optional + required > 0) {  
         ScopeTable table = inner.getTable();
         ScopeState state = inner.getState();
         
         for(int i = 0; i < optional; i++) {
            Constraint constraint = generics.get(i);
            Address address = AddressCache.getAddress(i);
            
            table.addConstraint(address, constraint);
         }
         for(int i = 0; i < required; i++) {
            Parameter parameter = parameters.get(i);
            Address address = parameter.getAddress();
            String name = parameter.getName();
            Object argument = arguments[i];
            Local local = create(inner, argument, i);
            
            if(closure) {
               state.addValue(name, local); 
            }
            table.addValue(address, local);
         }   
      }
      return inner;
   }

   private Local create(Scope scope, Object value, int index) throws Exception {
      List<Parameter> parameters = signature.getParameters();
      Parameter parameter = parameters.get(index);
      int length = parameters.size();
      
      if(index >= length -1) {
         if(signature.isVariable()) {
            Constraint constraint = parameter.getConstraint();
            Type type = constraint.getType(scope);
            String name = parameter.getName();
            Object[] list = (Object[])value;
            
            for(int i = 0; i < list.length; i++) {
               Object entry = list[i];
               
               if(!inspector.isCompatible(type,  entry)) {
                  throw new InternalStateException("Parameter '" + name + "...' does not match constraint '" + type + "'");
               }
            }
            return create(scope, value, parameter);  
         }
      }
      return create(scope, value, parameter);   
   }

   private Local create(Scope scope, Object value, Parameter parameter) throws Exception {
      Constraint constraint = parameter.getConstraint();
      Type type = constraint.getType(scope);
      String name = parameter.getName();
      
      if(parameter.isConstant()) {
         return Local.getConstant(value, name, constraint);
      }
      return Local.getReference(value, name, constraint);       
   }
}