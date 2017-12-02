package org.snapscript.compile;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.ScheduledThreadPoolExecutor;

import junit.framework.TestCase;

import org.snapscript.common.store.Store;
import org.snapscript.core.Context;

public class WildImportTest extends TestCase {
   
   private static final String SOURCE_1 =
   "import foo.*;\n"+
   "import test.*;\n"+
   "import blah.*;\n"+
   "\n"+
   "println(new Bar(1,2));\n";
         
   private static final String SOURCE_2 =
   "class Bar{\n"+
   "   var x,y;\n"+
   "   new(x,y){\n"+
   "      this.x=x;\n"+
   "      this.y=y;\n"+
   "   }\n"+
   "   get(){\n"+
   "      \"Bar(${x},${y})\";\n"+
   "   }\n"+
   "}";
   
   private static final String SOURCE_3 =
   "module Foo{\n"+
   "   dump(){\n"+
   "      \"Foo.dump()\";\n"+
   "   }\n"+
   "}";
   
   private static final String SOURCE_4 =
   "class Blah{\n"+
   "   var x,y;\n"+
   "   new(x,y){\n"+
   "      this.x=x;\n"+
   "      this.y=y;\n"+
   "   }\n"+
   "   get(){\n"+
   "      \"Blah(${x},${y})\";\n"+
   "   }\n"+
   "}";
         
   public void testImports() throws Exception {
      Map<String, String> sources = new HashMap<String, String>();
      sources.put("/test.snap", SOURCE_1);
      sources.put("/test/Bar.snap", SOURCE_2);   
      sources.put("/foo/Foo.snap", SOURCE_3);  
      sources.put("/blah/Blah.snap", SOURCE_4);  
      Store store = new MapStore(sources);
      Executor executor = new ScheduledThreadPoolExecutor(1);
      Context context = new StoreContext(store, executor);
      Compiler compiler = new ResourceCompiler(context);
      Timer.timeExecution("testImports", compiler.compile("/test.snap")); 
   }
   
   private static class MapStore implements Store {
      
      private final Map<String, String> sources;
      
      public MapStore(Map<String, String> sources){
         this.sources = sources;
      }

      @Override
      public InputStream getInputStream(String name) {
         String source = sources.get(name);
         if(source != null) {
            try {
               byte[] data = source.getBytes("UTF-8");
               return new ByteArrayInputStream(data);
            }catch(Exception e){
               throw new IllegalStateException("Could not load " + name, e);
            }
         }
         return null;
      }

      @Override
      public OutputStream getOutputStream(String name) {
         return null;
      }
      
   }
}
