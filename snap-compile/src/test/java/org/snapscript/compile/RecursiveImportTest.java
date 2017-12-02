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

public class RecursiveImportTest extends TestCase {
   
   private static final String SOURCE_1 =
   "import test.Bar;\n"+
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
   "import test2.Bar.Inner;\n"+
   "println(new Bar.Inner(22));\n";
   
   private static final String SOURCE_4 =
   "class Bar{\n"+
   "   var x,y;\n"+
   "   new(x,y){\n"+
   "      this.x=x;\n"+
   "      this.y=y;\n"+
   "   }\n"+
   "   get(){\n"+
   "      \"Bar(${x},${y})\";\n"+
   "   }\n"+
   "   class Inner{\n"+
   "     var f;\n"+
   "     new(f){\n"+
   "        this.f=f;\n"+
   "     }\n"+
   "     override toString(){\n"+
   "        \"Bar.Inner(${f})\";\n"+
   "     }\n"+
   "   }\n"+
   "}";
         
   public void testImports() throws Exception {
      Map<String, String> sources = new HashMap<String, String>();
      sources.put("/test.snap", SOURCE_1);
      sources.put("/test/Bar.snap", SOURCE_2);  
      sources.put("/test2.snap", SOURCE_3);  
      sources.put("/test2/Bar.snap", SOURCE_4);  
      Store store = new MapStore(sources);
      Executor executor = new ScheduledThreadPoolExecutor(1);
      Context context = new StoreContext(store, executor);
      Compiler compiler = new ResourceCompiler(context);
      Timer.timeExecution("testImports", compiler.compile("/test.snap"));
      Timer.timeExecution("testImports", compiler.compile("/test2.snap"));      
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
