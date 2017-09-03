package org.snapscript.tree.template;

import java.io.Writer;

import org.snapscript.core.Bug;
import org.snapscript.core.Scope;
import org.snapscript.core.State;
import org.snapscript.core.Value;
import org.snapscript.core.convert.StringBuilder;

public class VariableSegment extends Segment {
   
   private String variable;
   private char[] source;
   private int off;
   private int length;
   private int index;
   
   public VariableSegment(char[] source, int off, int length) {
      this.variable = new String(source, off + 2, length - 3);
      this.source = source;
      this.length = length;
      this.off = off;         
   }
   
   @Override
   public void compile(Scope scope) throws Exception {
      State state = scope.getState();
      index = state.addLocal(variable);
      System.err.println("(template) DECLARE: name="+variable+" index="+index);
   }  
   
   @Override
   public void process(Scope scope, Writer writer) throws Exception {
      State state = scope.getState();
      
      if(index != -1) {
         writer.write(source, off, length);
      } else {
         Value value = state.getLocal(index);
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