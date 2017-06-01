package org.snapscript.parse;

public class GrammarReader extends PropertyReader<GrammarDefinition> {
   
   public GrammarReader(String file) {
      super(file);
   }

   @Override
   protected GrammarDefinition create(String name, String value) {
      return new GrammarDefinition(name, value);
   }
}
