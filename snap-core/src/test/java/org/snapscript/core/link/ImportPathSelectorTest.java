package org.snapscript.core.link;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashMap;
import java.util.Map;

import junit.framework.TestCase;

public class ImportPathSelectorTest extends TestCase {

   private static final String SOURCE =
   "# default imports\n"+
   "lang = java.lang {*}\n"+ // default import
   "applet = java.applet {}\n"+
   "awt = java.awt {}\n"+
   "beans = java.beans {}\n"+
   "io = java.io {*}\n"+ // default import
   "math = java.math {}\n"+
   "net = java.net {}\n"+
   "nio = java.nio {}\n"+
   "rmi = java.rmi {}\n"+
   "security = java.security {}\n"+
   "sql = java.sql {}\n"+
   "text = java.text {}\n"+
   "time = java.time {}\n"+
   "util = java.util {*}\n"; // default import
   
   public void testImportPathSelector() throws Exception {
      System.err.println(SOURCE);
      final Map<String, byte[]> resources = new HashMap<String, byte[]>();
      
      resources.put("imports.txt", SOURCE.getBytes());
      resources.put("/mario/core/Mario.snap", new byte[]{});
      resources.put("/mario/core/MarioGame.snap", new byte[]{});
      
      final ClassLoader loader = new URLClassLoader(new URL[]{}) {
         @Override
         public InputStream getResourceAsStream(String name) {
            byte[] data = resources.get(name);
            if(data != null) {
               return new ByteArrayInputStream(data);
            }
            return null;
         }
      };
      Thread.currentThread().setContextClassLoader(loader);
      
      ImportPathResolver selector = new ImportPathResolver("imports.txt");
      
      assertEquals(selector.resolvePath("mario.core.Mario").size(), 1);
      assertEquals(selector.resolvePath("mario.core.MarioGame").size(), 1);
      
      assertEquals(selector.resolvePath("java.lang.Integer").size(), 1);
      assertEquals(selector.resolvePath("java.lang.Integer").get(0), "java.lang.Integer");
      assertEquals(selector.resolvePath("lang.Integer").size(), 2);
      assertEquals(selector.resolvePath("lang.Integer").get(0), "java.lang.Integer");
      assertEquals(selector.resolvePath("lang.Integer").get(1), "lang.Integer");
      
      assertEquals(selector.resolvePath("java.lang.Boolean").size(), 1);
      assertEquals(selector.resolvePath("java.lang.Boolean").get(0), "java.lang.Boolean");
      assertEquals(selector.resolvePath("lang.Boolean").size(), 2);
      assertEquals(selector.resolvePath("lang.Boolean").get(0), "java.lang.Boolean");
      assertEquals(selector.resolvePath("lang.Boolean").get(1), "lang.Boolean");
      
      assertEquals(selector.resolvePath("String").size(), 3);
      assertEquals(selector.resolvePath("String").get(0), "java.lang.String");
      assertEquals(selector.resolvePath("String").get(1), "java.io.String");
      assertEquals(selector.resolvePath("String").get(2), "java.util.String");
   }

}
