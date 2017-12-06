package org.snapscript.core.extend;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import org.snapscript.common.Consumer;

public class URLExtension {
   
   private static final String GET = "GET";
   private static final String POST = "POST";
   private static final String PUT = "PUT";
   private static final String DELETE = "DELETE";
   private static final String HEAD = "HEAD";
   
   private final InputStreamExtension extension;
   private final byte[] empty;
   
   public URLExtension() {
      this.extension = new InputStreamExtension();
      this.empty = new byte[]{};
   }
   
   public URL get(URL target) throws IOException {
      return execute(target, empty, GET);
   }
   
   public URL head(URL target) throws IOException {
      return execute(target, empty, HEAD);
   }
   
   public URL delete(URL target) throws IOException {
      return execute(target, empty, DELETE);
   }
   
   public URL post(URL target, byte[] data) throws IOException {
      return execute(target, data, POST);
   }
   
   public URL post(URL target, String text) throws IOException {
      return execute(target, text, POST);
   }
   
   public URL post(URL target, InputStream source) throws IOException {
      return execute(target, source, POST);
   }   
   
   public URL put(URL target, byte[] data) throws IOException {
      return execute(target, data, PUT);
   }
   
   public URL put(URL target, String text) throws IOException {
      return execute(target, text, PUT);
   }
   
   public URL put(URL target, InputStream source) throws IOException {
      return execute(target, source, PUT);
   }
   
   public URL header(URL target, String name, String value) throws IOException {
      URLConnection connection = target.openConnection();
      HttpURLConnection request = (HttpURLConnection)connection;
      
      request.setRequestProperty(name, value);
      
      return target;
   }
   
   private URL execute(URL target, byte[] data, String method) throws IOException {
      URLConnection connection = target.openConnection();
      HttpURLConnection request = (HttpURLConnection)connection;
      
      if(data.length > 0) {
         request.setDoOutput(true);
         request.setRequestMethod(method);
         
         try {
            OutputStream stream = request.getOutputStream();
            
            stream.write(data);
            stream.close();
         } catch(Exception e) {
            throw new IOException("Could not execute '" + method + "' for '" + target + "'", e);
         }
      }
      request.getResponseCode(); // force write
      return target;
   }
   
   private URL execute(URL target, String source, String method) throws IOException {
      URLConnection connection = target.openConnection();
      HttpURLConnection request = (HttpURLConnection)connection;
      byte[] data = source.getBytes();
      
      if(data.length > 0) {
         request.setDoOutput(true);
         request.setRequestMethod(method);
         
         try {
            OutputStream stream = request.getOutputStream();
            
            stream.write(data);
            stream.close();
         } catch(Exception e) {
            throw new IOException("Could not execute '" + method + "' for '" + target + "'", e);
         }
      }
      request.getResponseCode(); // force write
      return target;
   }
   
   private URL execute(URL target, InputStream source, String method) throws IOException {
      URLConnection connection = target.openConnection();
      HttpURLConnection request = (HttpURLConnection)connection;
      
      request.setDoOutput(true);
      request.setRequestMethod(method);
      
      try {
         OutputStream stream = request.getOutputStream();
         
         extension.copyTo(source, stream);
         stream.close();
      } catch(Exception e) {
         throw new IOException("Could not execute '" + method + "' for '" + target + "'", e);
      }
      request.getResponseCode(); // force write
      return target;
   }
   
   public URL success(URL target, Runnable task) throws IOException {
      URLConnection connection = target.openConnection();
      HttpURLConnection request = (HttpURLConnection)connection;
      int status = request.getResponseCode();
      
      if(status  >= 200 && status < 300) {
         task.run();
      }
      return target;
   }
   
   public URL success(URL target, Consumer<URL> consumer) throws IOException {
      URLConnection connection = target.openConnection();
      HttpURLConnection request = (HttpURLConnection)connection;
      int status = request.getResponseCode();
      
      if(status  >= 200 && status < 300) {
         consumer.consume(target);
      }
      return target;
   }
   
   public URL failure(URL target, Runnable task) throws IOException {
      URLConnection connection = target.openConnection();
      HttpURLConnection request = (HttpURLConnection)connection;
      int status = request.getResponseCode();
      
      if(status  >= 200 && status < 300) {
         task.run();
      }
      return target;
   }
   
   public URL failure(URL target, Consumer<URL> consumer) throws IOException {
      URLConnection connection = target.openConnection();
      HttpURLConnection request = (HttpURLConnection)connection;
      int status = request.getResponseCode();
      
      if(status  >= 200 && status < 300) {
         consumer.consume(target);
      }
      return target;
   }
   
   public InputStream response(URL target) throws IOException {
      URLConnection connection = target.openConnection();
      HttpURLConnection request = (HttpURLConnection)connection;
     
      try {
         return request.getInputStream();
      } catch(Exception e) {
         return request.getErrorStream();
      }
   }
   
   public int status(URL target) throws IOException {
      URLConnection connection = target.openConnection();
      HttpURLConnection request = (HttpURLConnection)connection;
      
      return request.getResponseCode();
   }
}
