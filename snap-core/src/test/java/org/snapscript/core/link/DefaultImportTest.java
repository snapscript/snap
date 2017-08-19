package org.snapscript.core.link;

import static org.snapscript.core.Reserved.IMPORT_FILE;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import junit.framework.TestCase;

import org.snapscript.core.ContextClassLoader;

public class DefaultImportTest extends TestCase {
   
   public void testDefaultImports() throws Exception {
      DefaultImportReader reader = new DefaultImportReader(IMPORT_FILE);
      ContextClassLoader loader = new ContextClassLoader(DefaultImportTest.class);
      StringBuilder builder = new StringBuilder();

      for(DefaultImport hint : reader){
         String prefix = hint.getPackage();
         Set<String> types = hint.getImports();
         Iterator<String> iterator = types.iterator();
         
         while(iterator.hasNext()) {
            String type = iterator.next();
            Class real = loader.loadClass(prefix + type);
            int modifiers = real.getModifiers();
            
            if(!Modifier.isPublic(modifiers)){
               iterator.remove();
            }
         }
      }
      builder.append("# default imports\n");
      for(DefaultImport hint : reader){
         String alias = hint.getAlias();
         String prefix = hint.getPackage();
         Set<String> types = hint.getImports();
         
         if(!types.isEmpty()) {
            builder.append("\n");
         }
         builder.append(alias);
         builder.append(" = ");
         
         if(prefix.endsWith(".")) {
            int length = prefix.length();
            prefix = prefix.substring(0, length -1);
         } 
         builder.append(prefix);
         
         if(!types.isEmpty()) {
            builder.append(" {\n   ");
            
            List<String> list = new ArrayList<String>(types);
            Collections.sort(list);
            
            if(hint.isInclude()) {
               list.add("*");
            }
            
            for(int i = 0; i < list.size(); i++) {
               String type = list.get(i);
               
               if(i > 0) {
                  if(i % 5 == 0) {
                     builder.append(",\n   ");
                  }else {
                     builder.append(", ");
                  }
               }
               builder.append(type);
            }
         }else {
            builder.append(" {");
         }
         builder.append("\n}\n");
      }
      System.out.println(builder);
      
      for(DefaultImport hint : reader){
         String prefix = hint.getPackage();
         Set<String> types = hint.getImports();
         
         for(String type : types) {
            Class real = loader.loadClass(prefix + type);
            
            assertNotNull(real);
            assertTrue((real.getModifiers() & Modifier.PUBLIC) == Modifier.PUBLIC);
         }
      }
   }

}
