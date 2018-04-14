package org.snapscript.tree.template;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import org.snapscript.core.Evaluation;
import org.snapscript.core.InternalStateException;
import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.scope.index.LocalScopeExtractor;
import org.snapscript.core.variable.Value;
import org.snapscript.parse.StringToken;

public class TextTemplate extends Evaluation {

   private LocalScopeExtractor extractor;
   private List<Segment> tokens;
   private StringToken template;
   
   public TextTemplate(StringToken template) {
      this.extractor = new LocalScopeExtractor(true, true);
      this.template = template;
   }
   
   @Override
   public Constraint compile(Scope scope, Constraint left) throws Exception {
      return Constraint.STRING;
   }

   @Override
   public Value evaluate(Scope scope, Object left) throws Exception {
      String text = template.getValue();
      Scope capture = extractor.extract(scope);
      
      if(text == null) {
         throw new InternalStateException("Text value was null");
      }
      String result = interpolate(capture, text);
   
      return Value.getTransient(result);
   }
   
   private String interpolate(Scope scope, String text) throws Exception {
      StringWriter writer = new StringWriter();
            
      if(tokens == null) {
         SegmentIterator iterator = new SegmentIterator(text);
         List<Segment> list = new ArrayList<Segment>();
         
         while(iterator.hasNext()) {
            Segment token = iterator.next();
            
            if(token != null) {
               list.add(token);  
            }
         }
         tokens = list; // atomic swap
      }
      for(Segment token : tokens) {
         token.process(scope, writer);
      }
      return writer.toString();
   }
}