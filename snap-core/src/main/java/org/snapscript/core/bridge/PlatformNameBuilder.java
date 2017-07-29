package org.snapscript.core.bridge;

import static org.snapscript.core.Reserved.IMPORT_SNAPSCRIPT;

public class PlatformNameBuilder {
   
   private static final String DEFAULT_SUFFIX = "BridgeBuilder";
   private static final String DEFAULT_QUALIFIER = "bridge";
   
   private final String qualifier;
   private final String suffix;
   
   public PlatformNameBuilder() {
      this(DEFAULT_QUALIFIER, DEFAULT_SUFFIX);
   }
   
   public PlatformNameBuilder(String qualifier, String suffix) {
      this.qualifier = qualifier;
      this.suffix = suffix;
   }

   public String createFullName(Platform platform) {
      String module = createPackage(platform);
      String name = createClassName(platform);
      
      return module + "." + name;
   }
   
   private String createClassName(Platform platform) {
      String name = platform.name();
      String token = name.toLowerCase();
      String prefix = token.substring(1);
      char first = name.charAt(0);
      
      return first + prefix + suffix;
   }
   
   private String createPackage(Platform platform) {
      String name = platform.name();
      String token = name.toLowerCase();
      
      return IMPORT_SNAPSCRIPT + qualifier + "." + token;
   }
}
