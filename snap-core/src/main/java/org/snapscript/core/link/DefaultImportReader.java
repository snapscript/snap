package org.snapscript.core.link;

import java.util.LinkedHashSet;
import java.util.Set;

import org.snapscript.common.io.PropertyReader;
import org.snapscript.core.InternalStateException;

public class DefaultImportReader extends PropertyReader<DefaultImport>{
   
   private static final String WILD = "*";
   
   public DefaultImportReader(String file) {
      super(file);
   }

   @Override
   protected DefaultImport create(String name, char[] data, int off, int length, int line) {
      Set<String> imports = createImports(data, off, length, line);
      String module = createPackage(data, off, length, line);

      if(imports.remove(WILD)) {
         return new DefaultImport(imports, module, name, true);
      }
      return new DefaultImport(imports, module, name);
   }
   
   private Set<String> createImports(char[] data, int off, int length, int line) {
      Set<String> imports = new LinkedHashSet<String>();
      int count = length + off - 1;
      
      for(int i = count; i >= 0; i--) {
         char next = data[i];
         
         if(delimiter(next)) {
            if(count > i) {
               String token = format(data, i+1, count-i);
               imports.add(token);
               count = i-1;
            }
         } 
         if(group(next)) {
            return imports;
         }
      }
      throw new InternalStateException("Error with imports from '" + file + "' at line " + line);
   }
   
   private String createPackage(char[] data, int off, int length, int line) {
      for(int i = 0; i < length; i++) {
         char next = data[off + i];
         
         if(group(next)) {
            return format(data, off, i);
         }
      }
      throw new InternalStateException("Error with package from '" + file + "' at line " + line);
   }
   
   private boolean group(char value) {
      return value == '{';
   }
   
   private boolean delimiter(char value) {
      return value == ',' || value == '{';
   }
   
   @Override
   protected boolean terminal(char value) {
      return value == '}';
   }

}