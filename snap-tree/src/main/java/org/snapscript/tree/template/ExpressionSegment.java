package org.snapscript.tree.template;

import java.io.Writer;

import org.snapscript.core.convert.StringBuilder;
import org.snapscript.core.module.Module;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.Context;
import org.snapscript.core.ExpressionEvaluator;

public class ExpressionSegment implements Segment {
   
   private String expression;
   private char[] source;
   private int off;
   private int length;
   
   public ExpressionSegment(char[] source, int off, int length) {
      this.expression = new String(source, off + 2, length - 3);
      this.source = source;
      this.length = length;
      this.off = off;         
   }
   
   @Override
   public void process(Scope scope, Writer writer) throws Exception {
      Module module = scope.getModule();
      String name = module.getName();
      Context context = module.getContext();
      ExpressionEvaluator evaluator = context.getEvaluator();
      Object value = evaluator.evaluate(scope, expression, name);
      
      if(value == null) {
         writer.write(source, off, length);
      } else {
         String text = StringBuilder.create(scope, value);
         
         writer.append(text);            
      }
   }   
   
   @Override
   public String toString() {
      return expression;
   }
}