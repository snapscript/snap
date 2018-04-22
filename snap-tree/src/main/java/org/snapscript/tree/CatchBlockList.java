package org.snapscript.tree;

import static org.snapscript.core.result.Result.NORMAL;

import java.util.concurrent.atomic.AtomicInteger;

import org.snapscript.core.Execution;
import org.snapscript.core.Statement;
import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.convert.TypeInspector;
import org.snapscript.core.error.ErrorCauseExtractor;
import org.snapscript.core.function.Parameter;
import org.snapscript.core.result.Result;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.scope.index.Index;
import org.snapscript.core.scope.index.Local;
import org.snapscript.core.scope.index.Table;
import org.snapscript.core.type.Type;
import org.snapscript.tree.function.ParameterDeclaration;

public class CatchBlockList {
   
   private final ErrorCauseExtractor extractor;
   private final TypeInspector inspector;
   private final AtomicInteger offset;
   private final CatchBlock[] blocks;
   private final Execution[] list;
   
   public CatchBlockList(CatchBlock... blocks) {
      this.extractor = new ErrorCauseExtractor();
      this.inspector = new TypeInspector();
      this.offset = new AtomicInteger(-1);
      this.list = new Execution[blocks.length];
      this.blocks = blocks;
   }    
   
   public Result define(Scope scope) throws Exception {  
      for(int i = 0; i < blocks.length; i++){
         CatchBlock block = blocks[i];
         Statement statement = block.getStatement();
         
         if(statement != null) {
            Index index = scope.getIndex();
            int size = index.size();
            
            try {
               ParameterDeclaration declaration = block.getDeclaration();
               Parameter parameter = declaration.get(scope);
               String name = parameter.getName();
               int value = index.index(name);
               
               offset.set(value);
               statement.define(scope);
            }finally {
               index.reset(size);
            }
         }
      }
      return NORMAL;
   }
   
   public Result compile(Scope scope) throws Exception {  
      for(int i = 0; i < blocks.length; i++){
         CatchBlock block = blocks[i];
         Statement statement = block.getStatement();
         
         if(statement != null) {
            ParameterDeclaration declaration = block.getDeclaration();
            Parameter parameter = declaration.get(scope);
            Constraint constraint = parameter.getConstraint();
            String name = parameter.getName();
            Table table = scope.getTable();
            Local local = Local.getConstant(null, name, constraint);
            int index = offset.get();
            
            table.add(index, local);
   
            list[i] = statement.compile(scope, null);
         }
      }
      return NORMAL;
   }

   public Result execute(Scope scope, Result result) throws Exception {
      Object data = result.getValue();
      
      for(int i = 0; i < blocks.length; i++){
         CatchBlock block = blocks[i];
         ParameterDeclaration declaration = block.getDeclaration();
         Parameter parameter = declaration.get(scope);
         Constraint constraint = parameter.getConstraint();
         Type type = constraint.getType(scope);
         String name = parameter.getName();

         if(data != null) {
            Object cause = extractor.extract(scope, data);
            
            if(inspector.isCompatible(type, cause)) {
               Table table = scope.getTable();
               Local local = Local.getConstant(cause, name);
               int index = offset.get();
               
               table.add(index, local);

               return list[i].execute(scope);
            }
         }
      }
      return result;
   }
}