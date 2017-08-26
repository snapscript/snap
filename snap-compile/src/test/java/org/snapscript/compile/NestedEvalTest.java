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

public class NestedEvalTest extends TestCase {
   
   private static final String SOURCE_1 =
   "import util.stream.Collectors;\n"+
   "trait Base{\n"+
    "   execute(name){\n"+
   "       Collectors.toList();\n"+
   "       eval('{:}');\n"+
   "       eval(name);\n"+
   "    }\n"+
   "}\n";
         
   private static final String SOURCE_2 =
   "import base.*;\n"+
   "class Other with Base{\n"+
   "   test(){\n"+
   "      execute('get()');\n"+
   "   }\n"+
   "   get(){\n"+
   "      \"Other.get()\";\n"+
   "   }\n"+
   "}";
   
   private static final String SOURCE_3 =
   "println(eval('new Other().test()'));\n";
         
   public void testImports() throws Exception {
      Map<String, String> sources = new HashMap<String, String>();
      sources.put("/base/Base.snap", SOURCE_1);
      sources.put("/test/Other.snap", SOURCE_2);   
      sources.put("/test.snap", SOURCE_3);  
      Store store = new MapStore(sources);
      Executor executor = new ScheduledThreadPoolExecutor(5);
      Context context = new StoreContext(store, executor);
      Compiler compiler = new ResourceCompiler(context);
      Timer.timeExecution(compiler.compile("/test.snap")); 
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
