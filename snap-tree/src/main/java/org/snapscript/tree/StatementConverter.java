package org.snapscript.tree;

import org.snapscript.core.Context;
import org.snapscript.core.Module;
import org.snapscript.core.Result;
import org.snapscript.core.ResultType;
import org.snapscript.core.Scope;
import org.snapscript.core.Statement;
import org.snapscript.core.Type;
import org.snapscript.core.convert.ConstraintConverter;
import org.snapscript.core.convert.ConstraintMatcher;

public class StatementConverter {
   
   private Statement converter;
   private Statement statement;
   private Type constraint;
   private boolean compile;

   public StatementConverter(Statement statement, Type constraint) {
      this(statement, constraint, true);
   }
   
   public StatementConverter(Statement statement, Type constraint, boolean compile) {
      this.constraint = constraint;
      this.statement = statement;
      this.compile = compile;
   }
   
   public Statement convert(Scope scope) throws Exception {
      if(converter == null) {
         converter = create(scope);
      }
      return converter;
   }
   
   private Statement create(Scope scope) throws Exception {
      Module module = scope.getModule();
      Context context = module.getContext();
      ConstraintMatcher matcher = context.getMatcher();
      ConstraintConverter converter = matcher.match(constraint);

      if(compile) {
         statement.compile(scope);
      }
      return new ResultConverter(converter, statement);
   }

   private static class ResultConverter extends Statement {
      
      private final ConstraintConverter converter;
      private final Statement statement;
      private final Result normal;
      
      public ResultConverter(ConstraintConverter converter, Statement statement) {
         this.normal = ResultType.getNormal();
         this.converter = converter;
         this.statement = statement;
      }
      
      public Result execute(Scope scope) throws Exception {
         Result result = statement.execute(scope);
         Object value = result.getValue();
         
         if(value != null) {
            value = converter.assign(value);
         }
         if(value != null) {
            return ResultType.getNormal(value);
         }
         return normal;
      }
   }
}
