package org.snapscript.compile;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import org.snapscript.common.store.Store;
import org.snapscript.core.Context;

import junit.framework.TestCase;

public class ModuleEvalTest extends TestCase {

   private static final String SOURCE_1 =
   "module Shape{\n"+
   "   class Point{\n"+
   "      var x,y;\n"+
   "      new(x,y){\n"+
   "         this.x=x;\n"+
   "         this.y=y;\n"+
   "      }\n"+
   "      toString(){\n"+
   "         \"${x},${y}\";\n"+
   "      }\n"+
   "   }\n"+
   "}\n";
   
   private static final String SOURCE_2 =
   "assert eval('new Shape.Point(1,2)', 'example').x == 1;\n";

   public void testModuleEval() throws Exception {
      Map<String, String> sources = new HashMap<String, String>();
      sources.put("/example/Shape.snap", SOURCE_1);
      sources.put("/run.snap", SOURCE_2);     
      System.err.println(SOURCE_1);
      System.err.println(SOURCE_2);
      Store store = new MapStore(sources);
      Context context = new StoreContext(store, null); 
      Compiler compiler = new ResourceCompiler(context);
      Timer.timeExecution(compiler.compile("/run.snap"));
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
               System.err.println(name);
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
