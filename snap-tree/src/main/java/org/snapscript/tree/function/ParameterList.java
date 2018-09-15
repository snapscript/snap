package org.snapscript.tree.function;

import java.util.List;

import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.constraint.ConstraintMapper;
import org.snapscript.core.function.Signature;
import org.snapscript.core.scope.Scope;

public class ParameterList {
   
   private ParameterListCompiler compiler;
   private ConstraintMapper mapper;
   private Signature signature;
   
   public ParameterList(ParameterDeclaration... list) {
      this.compiler = new ParameterListCompiler(list);
      this.mapper = new ConstraintMapper();
   }
   
   public Signature create(Scope scope, List<Constraint> generics) throws Exception{
      return create(scope, generics, null);
   }

   public Signature create(Scope scope, List<Constraint> generics, String prefix) throws Exception{
      if(signature == null) {
         List<Constraint> constraints = mapper.map(scope, generics);
         
         if(prefix == null) {
            signature = compiler.compile(scope, constraints);
         } else {
            signature = compiler.compile(scope, constraints, prefix);
         }
      }
      return signature;
   }
}