package org.snapscript.core.generate;

public enum Platform {
   ANDROID("android.os.Build", "org.snapscript.extend.android.AndroidExtender"),
   DEFAULT("java.awt.Frame", "org.snapscript.extend.normal.NormalExtender");
   
   public final String require;
   public final String type;
   
   private Platform(String require, String type) {
      this.require = require;
      this.type = type;
   }
   
   public String getType() {
      return type;
   }
   
   public static Platform resolvePlatform() {
      Platform[] types = Platform.values();
      
      for(Platform type : types) {
         try {
            Class.forName(type.require); // check if this is android
         }catch(Exception e) {
            continue;
         }
         return type;
      }
      return DEFAULT;
   }
}
