package org.snapscript.compile.assemble;

import java.util.concurrent.Executor;

import org.snapscript.core.Context;
import org.snapscript.core.module.Path;
import org.snapscript.core.stack.ThreadStack;
import org.snapscript.parse.SyntaxNode;
import org.snapscript.tree.InstructionResolver;
import org.snapscript.tree.OperationResolver;

public class OperationAssembler implements Assembler {
   
   private final OperationTraverser traverser;
   private final OperationResolver resolver;
   private final OperationBuilder builder;
   private final Context context;

   public OperationAssembler(Context context, Executor executor) {
      this.builder = new OperationBuilder(context, executor);
      this.resolver = new InstructionResolver(context);
      this.traverser = new OperationTraverser(builder, resolver);
      this.context = context;
   }
   
   @Override
   public <T> T assemble(SyntaxNode token, Path path) throws Exception {
      ThreadStack stack = context.getStack();
      
      try {
         return (T)traverser.create(token, path);
      } finally {
         stack.clear();
      }
   }
}