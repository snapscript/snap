package org.snapscript.core.link;

import java.util.Set;

public class DefaultImport {
   
   private final Set<String> imports;
   private final String module;
   private final String alias;
   private final boolean include;
   
   public DefaultImport(Set<String> imports, String module, String alias) {
      this(imports, module, alias, false);
   }
   
   public DefaultImport(Set<String> imports, String module, String alias, boolean include) {
      this.imports = imports;
      this.module = module;
      this.alias = alias;
      this.include = include;
   }
   
   public Set<String> getImports() {
      return imports;
   }
   
   public String getPackage(){
      return module;
   }
   
   public String getAlias(){
      return alias;
   }
   
   public boolean isInclude(){
      return include;
   }
}