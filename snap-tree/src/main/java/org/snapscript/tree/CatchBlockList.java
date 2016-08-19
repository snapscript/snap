package org.snapscript.tree;

import org.snapscript.core.Result;
import org.snapscript.core.ResultType;
import org.snapscript.core.Scope;
import org.snapscript.core.State;
import org.snapscript.core.Statement;
import org.snapscript.core.Type;
import org.snapscript.core.Value;
import org.snapscript.core.ValueType;
import org.snapscript.core.convert.CompatibilityChecker;
import org.snapscript.core.error.ErrorCauseExtractor;
import org.snapscript.core.function.Parameter;
import org.snapscript.tree.function.ParameterDeclaration;

public class CatchBlockList {
   
   private final ErrorCauseExtractor extractor;
   private final CompatibilityChecker checker;
   private final CatchBlock[] blocks;
   
   public CatchBlockList(CatchBlock... blocks) {
      this.extractor = new ErrorCauseExtractor();
      this.checker = new CompatibilityChecker();
      this.blocks = blocks;
   }    
   
   public Result compile(Scope scope) throws Exception {  
      for(CatchBlock block : blocks) {
         Statement statement = block.getStatement();
         
         if(statement != null) {
            statement.compile(scope);
         }
      }
      return ResultType.getNormal();
   }

   public Result execute(Scope scope, Result result) throws Exception {
      Object data = result.getValue();
      
      for(CatchBlock block : blocks) {
         ParameterDeclaration declaration = block.getDeclaration();
         Statement statement = block.getStatement();
         Parameter parameter = declaration.get(scope);
         Type type = parameter.getType();
         String name = parameter.getName();

         if(data != null) {
            Object cause = extractor.extract(scope, data);
            
            if(checker.compatible(scope, cause, type)) {
               Scope compound = scope.getInner();
               State state = compound.getState();
               Value constant = ValueType.getConstant(cause);
               
               state.addValue(name, constant);

               return statement.execute(compound);
            }
         }
      }
      return result;
   }
}
