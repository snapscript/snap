package org.snapscript.parse;

public enum Symbol {
   IDENTIFIER(TokenType.IDENTIFIER, "identifier") {
      @Override
      public boolean read(SyntaxBuilder builder) {
         return builder.identifier();
      }
   },
   TYPE(TokenType.TYPE, "type") {
      @Override
      public boolean read(SyntaxBuilder builder) {
         return builder.type();
      }
   },   
   QUALIFIER(TokenType.QUALIFIER, "qualifier") {
      @Override
      public boolean read(SyntaxBuilder builder) {
         return builder.qualifier();
      }
   },   
   HEXIDECIMAL(TokenType.HEXIDECIMAL, "hexidecimal") {
      @Override
      public boolean read(SyntaxBuilder builder) {
         return builder.hexidecimal();
      }
   },   
   BINARY(TokenType.BINARY, "binary") {
      @Override
      public boolean read(SyntaxBuilder builder) {
         return builder.binary();
      }
   },
   DECIMAL(TokenType.DECIMAL, "decimal") {
      @Override
      public boolean read(SyntaxBuilder builder) {
         return builder.decimal();
      }
   },
   TEXT(TokenType.TEXT, "text") {
      @Override
      public boolean read(SyntaxBuilder builder) {
         return builder.text();
      }
   },
   TEMPLATE(TokenType.TEMPLATE, "template") {
      @Override
      public boolean read(SyntaxBuilder builder) {
         return builder.template();
      }
   };
   
   public final TokenType type;
   public final String name;
   
   private Symbol(TokenType type, String name) {
      this.type = type;
      this.name = name;
   }

   public abstract boolean read(SyntaxBuilder node);
}
