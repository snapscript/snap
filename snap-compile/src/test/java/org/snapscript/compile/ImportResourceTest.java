package org.snapscript.compile;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import junit.framework.TestCase;

import org.snapscript.core.Context;
import org.snapscript.core.store.Store;

public class ImportResourceTest extends TestCase {
   
   private static final String SOURCE_1 =
   "import util.regex.Pattern;\n"+
   "class Foo {\n"+
   "   var x,y;\n"+
   "   new(x,y){\n"+
   "      this.x=x;\n"+
   "      this.y=y;\n"+
   "   }\n"+
   "   toString(){\n"+
   "      \"Foo(${x},${y})\";\n"+
   "   }\n"+
   "}\n"+
   "class Bar{\n"+
   "   var text;\n"+
   "   new(text){\n"+
   "      this.text=text;\n"+
   "   }\n"+
   "   toString(){\n"+
   "      \"Bar(${text})\";\n"+
   "   }\n"+
   "}\n";
         
   private static final String SOURCE_2 =
   "import example.Foo;\n"+
   "import example.Bar;\n"+
   "\n"+
   "class Demo{\n"+
   "   var foo;\n"+
   "   new(x,y){\n"+
   "      this.foo = new Foo(x,y);\n"+
   "   }\n"+
   "   get(): Foo{\n"+
   "      return foo;\n"+
   "   }\n"+
   "}";

   private static final String SOURCE_3 =
   "import demo.Demo;\n"+
   "\n"+
   "class Builder{\n"+
   "   var demo;\n"+
   "   new(text){\n"+
   "      this.demo = new Demo(text,1);\n"+
   "   }\n"+
   "   get(): Demo{\n"+
   "      return demo;\n"+
   "   }\n"+
   "}\n";
   
   private static final String SOURCE_4 =
   "import example.Bar;\n"+
   "import demo.Demo;\n"+   
   "\n"+
   "var bar = new Bar(\"test\");\n"+
   "var demo = new Demo(\"x\",33);\n"+
   "var foo = demo.get();\n"+
   "\n"+
   "println(bar);\n"+
   "println(foo);\n";     
   
   private static final String SOURCE_5 =
   "import builder.Builder;\n"+
   "\n"+
   "var example = new Builder(\"test\");\n"+
   "var demo = example.get();\n"+
   "\n"+
   "println(demo);\n";   
         
   public void testImports() throws Exception {
      Map<String, String> sources = new HashMap<String, String>();
      sources.put("/example.snap", SOURCE_1);
      sources.put("/demo.snap", SOURCE_2);
      sources.put("/builder/Builder.snap", SOURCE_3);  
      sources.put("/main.snap", SOURCE_4);  
      sources.put("/launch.snap", SOURCE_5);     
      System.err.println(SOURCE_3);
      Store store = new MapStore(sources);
      Context context = new StoreContext(store, null);
      Compiler compiler = new ResourceCompiler(context);
      Timer.timeExecution(compiler.compile("/main.snap"));
      Timer.timeExecution(compiler.compile("/launch.snap"));
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
