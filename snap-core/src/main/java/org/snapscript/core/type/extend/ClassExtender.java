package org.snapscript.core.type.extend;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import org.snapscript.core.function.Function;
import org.snapscript.core.type.TypeLoader;

public class ClassExtender {
   
   private final ExtensionRegistry registry;
   private final AtomicBoolean done;
   
   public ClassExtender(TypeLoader loader) {
      this.registry = new ExtensionRegistry(loader);
      this.done = new AtomicBoolean();
   }
   
   public List<Function> extend(Class type){
      if(done.compareAndSet(false, true)) {
         registry.register(File.class, FileExtension.class);
         registry.register(Date.class, DateExtension.class);
         registry.register(Reader.class, ReaderExtension.class);
         registry.register(Writer.class, WriterExtension.class);
         registry.register(InputStream.class, InputStreamExtension.class);
         registry.register(OutputStream.class, OutputStreamExtension.class);
         registry.register(URLConnection.class, URLConnectionExtension.class);
         registry.register(URL.class, URLExtension.class);
      }
      return registry.extract(type);
   }

}