package org.snapscript.compile.assemble;

import static org.snapscript.core.Reserved.DEFAULT_PACKAGE;
import static org.snapscript.tree.Instruction.EXPRESSION;

import org.snapscript.common.Cache;
import org.snapscript.common.LeastRecentlyUsedCache;
import org.snapscript.core.Context;
import org.snapscript.core.Evaluation;
import org.snapscript.core.ExpressionEvaluator;
import org.snapscript.core.InternalStateException;
import org.snapscript.core.Model;
import org.snapscript.core.Scope;
import org.snapscript.core.ScopeMerger;
import org.snapscript.core.Value;
import org.snapscript.parse.SyntaxCompiler;
import org.snapscript.parse.SyntaxNode;
import org.snapscript.parse.SyntaxParser;

public class OperationEvaluator implements ExpressionEvaluator {
   
   private final Cache<String, Evaluation> cache;
   private final SyntaxCompiler compiler;
   private final ScopeMerger merger;
   private final Assembler assembler;
   private final int limit;
   
   public OperationEvaluator(Context context){
      this(context, 200);
   }
   
   public OperationEvaluator(Context context, int limit) {
      this.cache = new LeastRecentlyUsedCache<String, Evaluation>();
      this.assembler = new OperationAssembler(context);
      this.merger = new ScopeMerger(context);
      this.compiler = new SyntaxCompiler();
      this.limit = limit;
   }
   
   @Override
   public <T> T evaluate(Model model, String source) throws Exception{
      return evaluate(model, source, DEFAULT_PACKAGE);
   }
   
   @Override
   public <T> T evaluate(Model model, String source, String module) throws Exception{
      Scope scope = merger.merge(model, module);
      return evaluate(scope, source, module);
   }
   
   @Override
   public <T> T evaluate(Scope scope, String source) throws Exception{
      return evaluate(scope, source, DEFAULT_PACKAGE);
   }
   
   @Override
   public <T> T evaluate(Scope scope, String source, String module) throws Exception{
      Evaluation evaluation = cache.fetch(source);
      
      try {
         if(evaluation == null) {
            SyntaxParser parser = compiler.compile();
            SyntaxNode node = parser.parse(module, source, EXPRESSION.name);
            int length = source.length();
            
            evaluation = assembler.assemble(node, source);
            
            if(length < limit) {
               cache.cache(source, evaluation);
            }
         }
         Value reference = evaluation.evaluate(scope,null);
         
         return (T)reference.getValue();
      } catch(Exception e) {
         throw new InternalStateException("Could not evaluate '" + source + "'", e);
      }
   }
}
