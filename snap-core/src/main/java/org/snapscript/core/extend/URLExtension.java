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
   
   public URLExtension() {
      this.extension = new InputStreamExtension();
   }
   
   public URL header(URL target, String name, String value) throws IOException {
      URLConnection connection = target.openConnection();
      HttpURLConnection request = (HttpURLConnection)connection;
      
      request.setRequestProperty(name, value);
      
      return target;
   }
   
   public URL get(URL target) throws IOException {
      URLConnection connection = target.openConnection();
      HttpURLConnection request = (HttpURLConnection)connection;
      
      request.setDoOutput(false);
      request.setRequestMethod(GET);

      return target;
   }
   
   public URL head(URL target) throws IOException {
      URLConnection connection = target.openConnection();
      HttpURLConnection request = (HttpURLConnection)connection;
      
      request.setDoOutput(false);
      request.setRequestMethod(HEAD);

      return target;
   }
   
   public URL delete(URL target) throws IOException {
      URLConnection connection = target.openConnection();
      HttpURLConnection request = (HttpURLConnection)connection;
      
      request.setDoOutput(false);
      request.setRequestMethod(DELETE);

      return target;
   }
   
   public URL post(URL target, byte[] data) throws IOException {
      URLConnection connection = target.openConnection();
      HttpURLConnection request = (HttpURLConnection)connection;

      request.setDoOutput(true);
      request.setRequestMethod(POST);

      return write(target, data);
   }
   
   public URL post(URL target, String text) throws IOException {
      URLConnection connection = target.openConnection();
      HttpURLConnection request = (HttpURLConnection)connection;

      request.setDoOutput(true);
      request.setRequestMethod(POST);

      return write(target, text);
   }
   
   public URL post(URL target, InputStream source) throws IOException {
      URLConnection connection = target.openConnection();
      HttpURLConnection request = (HttpURLConnection)connection;
      
      request.setDoOutput(true);
      request.setRequestMethod(POST);

      return write(target, source);
   }   
   
   public URL put(URL target, byte[] data) throws IOException {
      URLConnection connection = target.openConnection();
      HttpURLConnection request = (HttpURLConnection)connection;
      
      request.setDoOutput(true);
      request.setRequestMethod(PUT);

      return write(target, data);
   }
   
   public URL put(URL target, String text) throws IOException {
      URLConnection connection = target.openConnection();
      HttpURLConnection request = (HttpURLConnection)connection;
      
      request.setDoOutput(true);
      request.setRequestMethod(PUT);

      return write(target, text);
   }
   
   public URL put(URL target, InputStream source) throws IOException {
      URLConnection connection = target.openConnection();
      HttpURLConnection request = (HttpURLConnection)connection;
      
      request.setDoOutput(true);
      request.setRequestMethod(PUT);

      return write(target, source);
   }
   
   private URL write(URL target, byte[] data) throws IOException {
      URLConnection connection = target.openConnection();
      HttpURLConnection request = (HttpURLConnection)connection;
      OutputStream stream = request.getOutputStream();
      
      stream.write(data);
      request.getResponseCode(); // force write
      
      return target;
   }
   
   private URL write(URL target, String text) throws IOException {
      URLConnection connection = target.openConnection();
      HttpURLConnection request = (HttpURLConnection)connection;
      OutputStream stream = request.getOutputStream();
      byte[] data = text.getBytes();
      
      stream.write(data);
      request.getResponseCode(); // force write
      
      return target;
   }
   
   private URL write(URL target, InputStream source) throws IOException {
      URLConnection connection = target.openConnection();
      HttpURLConnection request = (HttpURLConnection)connection;
      OutputStream stream = request.getOutputStream();
     
      extension.copyTo(source, stream);
      request.getResponseCode(); // force write
      
      return target;
   }
   
   public URL success(URL target, Runnable task) throws IOException {
      if(isSuccess(target)) {
         task.run();
      }
      return target;
   }
   
   public URL success(URL target, Consumer<URL> consumer) throws IOException {
      if(isSuccess(target)) {
         consumer.consume(target);
      }
      return target;
   }
   
   public URL failure(URL target, Runnable task) throws IOException {
      if(!isSuccess(target)) {
         task.run();
      }
      return target;
   }
   
   public URL failure(URL target, Consumer<URL> consumer) throws IOException {
      if(!isSuccess(target)) {
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
   
   public boolean isSuccess(URL target) throws IOException {
      URLConnection connection = target.openConnection();
      HttpURLConnection request = (HttpURLConnection)connection;
      int status = request.getResponseCode();
      
      return status  >= 200 && status < 300;
   }
}
