package org.snapscript.tree;

import org.snapscript.parse.StringToken;

public class WildQualifier implements Qualifier {

   private final StringToken[] tokens;
   private final int count;

   public WildQualifier(StringToken... tokens) {
      this.count = tokens.length;
      this.tokens = tokens;
   }
   
   @Override
   public String[] getSegments() {
      String[] segments = new String[count];

      for (int i = 0; i < count; i++) {
         StringToken token = tokens[i];
         String value = token.getValue();
         
         segments[i] = value;
      }
      return segments;
   }

   @Override
   public String getQualifier() {
      StringBuilder builder = new StringBuilder();

      for (int i = 0; i < count; i++) {
         StringToken token = tokens[i];
         String value = token.getValue();

         if (i > 0) {
            builder.append(".");
         }
         builder.append(value);
      }
      return builder.toString();
   }

   @Override
   public String getLocation() {
      StringBuilder builder = new StringBuilder();

      for (int i = 0; i < count; i++) {
         StringToken token = tokens[i];
         String value = token.getValue();

         if (i > 0) {
            builder.append(".");
         }
         builder.append(value);
      }
      return builder.toString();
   }

   @Override
   public String getTarget() {
      return null;
   }
}
