package org.snapscript.tree.function;

import java.util.List;

import org.snapscript.core.Bug;
import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.convert.TypeInspector;
import org.snapscript.core.error.InternalStateException;
import org.snapscript.core.function.Parameter;
import org.snapscript.core.function.Signature;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.scope.State;
import org.snapscript.core.scope.index.Index;
import org.snapscript.core.scope.index.Local;
import org.snapscript.core.scope.index.Table;
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
         Index index = scope.getIndex();
         
         for(int i = 0; i < required; i++) {
            Parameter parameter = parameters.get(i);
            String name = parameter.getName();
            int depth = index.get(name);
            
            if(depth == -1) {
               index.index(name);
            }
         }
      }
   }

   public Scope extract(Scope scope, Object[] arguments) throws Exception {
      List<Parameter> parameters = signature.getParameters();
      List<Constraint> generics = signature.getGenerics();
      int required = parameters.size();
      int optional = generics.size();

      if(optional + required > 0) {
         Scope inner = scope.getStack();      
         Table table = inner.getTable();
         State state = inner.getState();
         
         for(int i = 0; i < optional; i++) {
            Constraint constraint = generics.get(i);
            table.addConstraint(i, constraint);
         }
         for(int i = 0; i < required; i++) {
            Parameter parameter = parameters.get(i);
            String name = parameter.getName();
            Object argument = arguments[i];
            Local local = create(inner, argument, i);
            
            if(closure) {
               state.add(name, local); 
            }
            table.addLocal(i, local);
         }   
         return inner;
      }
      return scope;
   }

   private Local create(Scope scope, Object value, int index) throws Exception {
      List<Parameter> parameters = signature.getParameters();
      Parameter parameter = parameters.get(index);
      Constraint constraint = parameter.getConstraint();
      Type type = constraint.getType(scope);
      String name = parameter.getName();
      int length = parameters.size();
      
      if(index >= length -1) {
         if(signature.isVariable()) {
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
   
   @Bug
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