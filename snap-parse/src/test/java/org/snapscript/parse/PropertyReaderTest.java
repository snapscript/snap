package org.snapscript.parse;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Properties;

import junit.framework.TestCase;

public class PropertyReaderTest extends TestCase {
   
   private static final String SOURCE =
   "\n"+
   "a.b = 1\n"+
   "# comment\n"+
   "\n"+
   "\n"+
   "name= value\n"+
   "blah.blah=foo\n"+
   "\n";
   
   private static class Property {
      
      private final String name;
      private final String value;
      
      public Property(String name, String value){
         this.name = name;
         this.value = value;
      }
      
      public String getName(){
         return name;
      }
      
      public String getValue(){
         return value;
      }
   }
   
   public void testLineReader() throws Exception {
      ClassLoader loader = new URLClassLoader(new URL[]{}) {
         @Override
         public InputStream getResourceAsStream(String name) {
            return new ByteArrayInputStream(SOURCE.getBytes());
         }
      };
      ClassLoader original = Thread.currentThread().getContextClassLoader();
      
      try {
         Thread.currentThread().setContextClassLoader(loader);
         PropertyReader<Property> reader = new PropertyReader<Property>("file.properties") {
            public Property create(String name, String value) {
               return new Property(name, value);
            }
         };
         Properties properties = new Properties();
         for(Property property : reader){
            String name = property.getName();
            String value = property.getValue();
            
            properties.setProperty(name, value);
            assertEquals(name, name.trim());
            System.err.println("["+name + "]=" + value);
            System.err.println();
         }
         assertEquals(properties.size(), 3);
         assertEquals(properties.getProperty("a.b"), "1");
         assertEquals(properties.getProperty("name"), "value");
         assertEquals(properties.getProperty("blah.blah"), "foo");
      }finally {
         Thread.currentThread().setContextClassLoader(original);
      }
   }
}
