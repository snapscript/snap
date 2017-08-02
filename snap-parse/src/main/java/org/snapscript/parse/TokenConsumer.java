package org.snapscript.parse;

public abstract class TokenConsumer implements TokenReader {

   protected LexicalAnalyzer analyzer;
   protected Token value;

   protected TokenConsumer() {
      this(null);
   }      
   
   protected TokenConsumer(LexicalAnalyzer analyzer) {
      this.analyzer = analyzer;
   }

   @Override
   public boolean literal(String text) {
      Token token = analyzer.literal(text);

      if (token != null) {
         value = token;
         return true;
      }
      return false;
   }

   @Override
   public boolean decimal() {
      Token token = analyzer.decimal();

      if (token != null) {
         value = token;
         return true;
      }
      return false;
   }
   
   @Override
   public boolean binary() {
      Token token = analyzer.binary();

      if (token != null) {
         value = token;
         return true;
      }
      return false;
   }

   @Override
   public boolean hexidecimal() {
      Token token = analyzer.hexidecimal();

      if (token != null) {
         value = token;
         return true;
      }
      return false;
   }

   @Override
   public boolean identifier() {
      Token token = analyzer.identifier();

      if (token != null) {
         value = token;
         return true;
      }
      return false;
   }
   
   @Override
   public boolean qualifier() {
      Token token = analyzer.qualifier();

      if (token != null) {
         value = token;
         return true;
      }
      return false;
   }     
   
   @Override
   public boolean type() {
      Token token = analyzer.type();

      if (token != null) {
         value = token;
         return true;
      }
      return false;
   }       

   @Override
   public boolean text() {
      Token token = analyzer.text();

      if (token != null) {
         value = token;
         return true;
      }
      return false;
   }
   
   @Override
   public boolean template() {
      Token token = analyzer.template();

      if (token != null) {
         value = token;
         return true;
      }
      return false;
   }
   
   @Override
   public boolean space() {
      Token token = analyzer.space();

      if (token != null) {
         value = token;
         return true;
      }
      return false;
   }
}