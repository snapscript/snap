package org.snapscript.core.bridge;

public enum Platform {
   ANDROID("android.os.Build", "org.snapscript.bridge.android.AndroidBuilder"),
   STANDARD("java.awt.Frame", "org.snapscript.bridge.standard.StandardBuilder");
   
   public final String verify;
   public final String type;
   
   private Platform(String verify, String type) {
      this.verify = verify;
      this.type = type;
   }
   
   public String getType() {
      return type;
   }
   
   public static Platform resolvePlatform() {
      Platform[] types = Platform.values();
      
      for(Platform type : types) {
         try {
            Class.forName(type.verify); // check if this is android
         }catch(Exception e) {
            continue;
         }
         return type;
      }
      return STANDARD;
   }
}
