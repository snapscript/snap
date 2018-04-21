package org.snapscript.tree.function;

import static org.snapscript.core.constraint.Constraint.NONE;

import java.util.ArrayList;
import java.util.List;

import org.snapscript.core.function.FunctionSignature;
import org.snapscript.core.function.Parameter;
import org.snapscript.core.function.ParameterBuilder;
import org.snapscript.core.function.Signature;
import org.snapscript.core.module.Module;
import org.snapscript.core.scope.Scope;

public class ParameterList {
   
   private ParameterMatchChecker checker;
   private ParameterDeclaration[] list;
   private ParameterBuilder builder;
   private Signature signature;
   
   public ParameterList(ParameterDeclaration... list) {
      this.checker = new ParameterMatchChecker(list);
      this.builder = new ParameterBuilder();
      this.list = list;
   }
   
   public Signature create(Scope scope) throws Exception{
      return create(scope, null);
   }
   
   public Signature create(Scope scope, String prefix) throws Exception{
      Module module = scope.getModule();
      
      if(signature == null) {
         List<Parameter> parameters = new ArrayList<Parameter>();
         
         if(prefix != null) {
            Parameter parameter = builder.create(NONE, prefix); // this is constrained by type
            parameters.add(parameter);
         }
         boolean variable = checker.isVariable(scope);
         boolean absolute = checker.isAbsolute(scope);
         
         for(int i = 0; i < list.length; i++) {
            ParameterDeclaration declaration = list[i];
            
            if(declaration != null) {
               Parameter parameter = declaration.get(scope);
               parameters.add(parameter);
            }
         }
         signature = new FunctionSignature(parameters, module, null, absolute, variable);
      }
      return signature;
   }
}