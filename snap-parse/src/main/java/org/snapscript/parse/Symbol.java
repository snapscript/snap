package org.snapscript.parse;


public enum Symbol {
   IDENTIFIER("identifier") {
      @Override
      public boolean read(SyntaxBuilder builder) {
         return builder.identifier();
      }
   },
   TYPE("type") {
      @Override
      public boolean read(SyntaxBuilder builder) {
         return builder.type();
      }
   },   
   QUALIFIER("qualifier") {
      @Override
      public boolean read(SyntaxBuilder builder) {
         return builder.qualifier();
      }
   },   
   HEXIDECIMAL("hexidecimal") {
      @Override
      public boolean read(SyntaxBuilder builder) {
         return builder.hexidecimal();
      }
   },   
   BINARY("binary") {
      @Override
      public boolean read(SyntaxBuilder builder) {
         return builder.binary();
      }
   },
   DECIMAL("decimal") {
      @Override
      public boolean read(SyntaxBuilder builder) {
         return builder.decimal();
      }
   },
   TEXT("text") {
      @Override
      public boolean read(SyntaxBuilder builder) {
         return builder.text();
      }
   },
   TEMPLATE("template") {
      @Override
      public boolean read(SyntaxBuilder builder) {
         return builder.template();
      }
   };
   
   public final String name;
   
   private Symbol(String name) {
      this.name = name;
   }

   public abstract boolean read(SyntaxBuilder node);
}
