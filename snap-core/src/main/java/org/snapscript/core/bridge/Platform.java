package org.snapscript.core.bridge;

public enum Platform {
   ANDROID("android.os.Build"),
   STANDARD("java.awt.Frame");
   
   private final String type;
   
   private Platform(String type) {
      this.type = type;
   }
   
   public static Platform resolvePlatform() {
      Platform[] types = Platform.values();
      
      for(Platform type : types) {
         try {
            Class.forName(type.type); // check if this is android
         }catch(Exception e) {
            continue;
         }
         return type;
      }
      return STANDARD;
   }
}
