package org.snapscript.tree.template;

import java.io.Writer;

import org.snapscript.core.convert.StringBuilder;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.scope.State;
import org.snapscript.core.variable.Value;

public class VariableSegment implements Segment {
   
   private String variable;
   private char[] source;
   private int off;
   private int length;
   
   public VariableSegment(char[] source, int off, int length) {
      this.variable = new String(source, off + 2, length - 3);
      this.source = source;
      this.length = length;
      this.off = off;         
   }
   
   @Override
   public void process(Scope scope, Writer writer) throws Exception {
      State state = scope.getState();
      Value value = state.get(variable);
      
      if(value == null) {
         writer.write(source, off, length);
      } else {
         Object token = value.getValue();
         String text = StringBuilder.create(scope, token);
         
         writer.append(text);            
      }
   }   
   
   @Override
   public String toString() {
      return variable;
   }
}