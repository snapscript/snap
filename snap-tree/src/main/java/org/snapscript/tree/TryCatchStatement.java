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

public class TryCatchStatement extends Statement {
   
   private final ParameterDeclaration declaration;
   private final ErrorCauseExtractor extractor;
   private final CompatibilityChecker checker;
   private final Statement statement;
   private final Statement handle;
   private final Statement finish;
   
   public TryCatchStatement(Statement statement, Statement finish) {
      this(statement, null, null, finish);
   }   
   
   public TryCatchStatement(Statement statement, ParameterDeclaration declaration, Statement handle) {
      this(statement, declaration, handle, null);
   }   
   
   public TryCatchStatement(Statement statement, ParameterDeclaration declaration, Statement handle, Statement finish) {
      this.extractor = new ErrorCauseExtractor();
      this.checker = new CompatibilityChecker();
      this.declaration = declaration;  
      this.statement = statement;
      this.handle = handle;
      this.finish = finish;
   }    
   
   @Override
   public Result compile(Scope scope) throws Exception {  
      if(handle != null) {
         handle.compile(scope);
      }
      if(finish != null) {
         finish.compile(scope);
      }
      return statement.compile(scope);
   }

   @Override
   public Result execute(Scope scope) throws Exception {
      Result result = handle(scope);
      
      try {
         if(result.isThrow()) {
            return handle(scope, result);            
         }   
      } finally {
         if(finish != null) {
            finish.execute(scope);
         }
      }
      return result;
   }
   
   private Result handle(Scope scope) throws Exception {
      try {
         return statement.execute(scope);
      } catch(Throwable cause) {
         return ResultType.getThrow(cause);
      }
   }

   private Result handle(Scope scope, Result result) throws Exception {
      Object data = result.getValue();
      
      if(declaration != null) {
         Parameter parameter = declaration.get(scope);
         Type type = parameter.getType();
         String name = parameter.getName();

         if(data != null) {
            Object cause = extractor.extract(scope, data);
            
            if(type != null) {
               if(!checker.compatible(scope, cause, type)) {
                  return result;
               }
            }
            Scope compound = scope.getInner();
            State state = compound.getState();
            Value constant = ValueType.getConstant(cause);
            
            state.addConstant(name, constant);
               
            if(handle != null) {
               return handle.execute(compound);
            }
         }
      }
      return result;
   }

}
