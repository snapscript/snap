package org.snapscript.core.type.extend;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URLConnection;

import org.snapscript.common.Consumer;

public class URLConnectionExtension {
   
   private static final String GET = "GET";
   private static final String POST = "POST";
   private static final String PUT = "PUT";
   private static final String DELETE = "DELETE";
   private static final String HEAD = "HEAD";
   
   private final InputStreamExtension extension;
   private final byte[] empty;
   
   public URLConnectionExtension() {
      this.extension = new InputStreamExtension();
      this.empty = new byte[]{};
   }
   
   public URLConnection get(URLConnection connection) throws IOException {
      return execute(connection, empty, GET);
   }
   
   public URLConnection head(URLConnection connection) throws IOException {
      return execute(connection, empty, HEAD);
   }
   
   public URLConnection delete(URLConnection connection) throws IOException {
      return execute(connection, empty, DELETE);
   }
   
   public URLConnection post(URLConnection connection, byte[] data) throws IOException {
      return execute(connection, data, POST);
   }
   
   public URLConnection post(URLConnection connection, String text) throws IOException {
      return execute(connection, text, POST);
   }
   
   public URLConnection post(URLConnection connection, InputStream source) throws IOException {
      return execute(connection, source, POST);
   }   
   
   public URLConnection put(URLConnection connection, byte[] data) throws IOException {
      return execute(connection, data, PUT);
   }
   
   public URLConnection put(URLConnection connection, String text) throws IOException {
      return execute(connection, text, PUT);
   }
   
   public URLConnection put(URLConnection connection, InputStream source) throws IOException {
      return execute(connection, source, PUT);
   }
   
   public URLConnection header(URLConnection connection, String name, String value) throws IOException {
      HttpURLConnection request = (HttpURLConnection)connection;
      
      request.setRequestProperty(name, value);
      
      return connection;
   }
   
   public URLConnection success(URLConnection connection, Runnable task) throws IOException {
      HttpURLConnection request = (HttpURLConnection)connection;
      int status = request.getResponseCode();
      
      if(status >= 200 && status < 300) {
         task.run();
      }
      return connection;
   }
   
   public URLConnection success(URLConnection connection, Consumer<URLConnection> consumer) throws IOException {
      HttpURLConnection request = (HttpURLConnection)connection;
      int status = request.getResponseCode();
      
      if(status >= 200 && status < 300) {
         consumer.consume(connection);
      }
      return connection;
   }
   
   public URLConnection failure(URLConnection connection, Runnable task) throws IOException {
      HttpURLConnection request = (HttpURLConnection)connection;
      int status = request.getResponseCode();
      
      if(status < 200 && status >= 300) {
         task.run();
      }
      return connection;
   }
   
   public URLConnection failure(URLConnection connection, Consumer<URLConnection> consumer) throws IOException {
      HttpURLConnection request = (HttpURLConnection)connection;
      int status = request.getResponseCode();
      
      if(status < 200 && status >= 300) {
         consumer.consume(connection);
      }
      return connection;
   }
   
   public InputStream response(URLConnection connection) throws IOException {
      HttpURLConnection request = (HttpURLConnection)connection;
     
      try {
         return request.getInputStream();
      } catch(Exception e) {
         return request.getErrorStream();
      }
   }
   
   public int status(URLConnection connection) throws IOException {
      HttpURLConnection request = (HttpURLConnection)connection;
      
      return request.getResponseCode();
   }
   
   private URLConnection execute(URLConnection connection, byte[] data, String method) throws IOException {
      HttpURLConnection request = (HttpURLConnection)connection;
      
      if(data.length > 0) {
         request.setDoOutput(true);
         request.setRequestMethod(method);
         
         try {
            OutputStream stream = request.getOutputStream();
            
            stream.write(data);
            stream.close();
         } catch(Exception e) {
            throw new IOException("Could not execute '" + method + "' for '" + connection + "'", e);
         }
      }
      request.getResponseCode(); // force write
      return connection;
   }
   
   private URLConnection execute(URLConnection connection, String source, String method) throws IOException {
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
            throw new IOException("Could not execute '" + method + "' for '" + connection + "'", e);
         }
      }
      request.getResponseCode(); // force write
      return connection;
   }
   
   private URLConnection execute(URLConnection connection, InputStream source, String method) throws IOException {
      HttpURLConnection request = (HttpURLConnection)connection;
      
      request.setDoOutput(true);
      request.setRequestMethod(method);
      
      try {
         OutputStream stream = request.getOutputStream();
         
         extension.copyTo(source, stream);
         stream.close();
      } catch(Exception e) {
         throw new IOException("Could not execute '" + method + "' for '" + connection + "'", e);
      }
      request.getResponseCode(); // force write
      return connection;
   }
}
