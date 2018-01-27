package org.snapscript.core.shell;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.List;

import junit.framework.TestCase;

public class StaticSerializationTest extends TestCase {

   public static class MessageBatch implements Serializable {
      private static final long serialVersionUID = 33L;
      private List<String> batch;

      public MessageBatch(List<String> batch) {
         this.batch = batch;
      }

      public List<String> getMessages() {
         return batch;
      }

      public String toString() {
         return String.valueOf(batch);
      }
   }

   public static class SomeExample implements Serializable {
      private static final long serialVersionUID = 11L;

   }
   public static class TotallyDifferentObject implements Serializable{
      private static final long serialVersionUID = 33L;
   }
   /**
    * @tests java.io.ObjectInputStream#readObject()
    */
   public void testReadObject() throws Exception {
       String s = "HelloWorld";


       // Regression for HARMONY-91
       // dynamically create serialization byte array for the next hierarchy:
       // - class A implements Serializable
       // - class C extends A

       byte[] cName = MessageBatch.class.getName().getBytes("UTF-8");
       byte[] aName = SomeExample.class.getName().getBytes("UTF-8");

       ByteArrayOutputStream out = new ByteArrayOutputStream();

       byte[] begStream = new byte[] { (byte) 0xac, (byte) 0xed, // STREAM_MAGIC
               (byte) 0x00, (byte) 0x05, // STREAM_VERSION
               (byte) 0x73, // TC_OBJECT
               (byte) 0x72, // TC_CLASSDESC
               (byte) 0x00, // only first byte for C class name length
       };

       out.write(begStream, 0, begStream.length);
       out.write(cName.length); // second byte for C class name length
       out.write(cName, 0, cName.length); // C class name

       byte[] midStream = new byte[] { (byte) 0x00, (byte) 0x00, (byte) 0x00,
               (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
               (byte) 0x21, // serialVersionUID = 33L
               (byte) 0x02, // flags
               (byte) 0x00, (byte) 0x00, // fields : none
               (byte) 0x78, // TC_ENDBLOCKDATA
               (byte) 0x72, // Super class for C: TC_CLASSDESC for A class
               (byte) 0x00, // only first byte for A class name length
       };

       out.write(midStream, 0, midStream.length);
       out.write(aName.length); // second byte for A class name length
       out.write(aName, 0, aName.length); // A class name

       byte[] endStream = new byte[] { (byte) 0x00, (byte) 0x00, (byte) 0x00,
               (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
               (byte) 0x0b, // serialVersionUID = 11L
               (byte) 0x02, // flags
               (byte) 0x00, (byte) 0x01, // fields

               (byte) 0x4c, // field description: type L (object)
               (byte) 0x00, (byte) 0x04, // length
               // field = 'name'
               (byte) 0x6e, (byte) 0x61, (byte) 0x6d, (byte) 0x65,

               (byte) 0x74, // className1: TC_STRING
               (byte) 0x00, (byte) 0x12, // length
               //
               (byte) 0x4c, (byte) 0x6a, (byte) 0x61, (byte) 0x76,
               (byte) 0x61, (byte) 0x2f, (byte) 0x6c, (byte) 0x61,
               (byte) 0x6e, (byte) 0x67, (byte) 0x2f, (byte) 0x53,
               (byte) 0x74, (byte) 0x72, (byte) 0x69, (byte) 0x6e,
               (byte) 0x67, (byte) 0x3b,

               (byte) 0x78, // TC_ENDBLOCKDATA
               (byte) 0x70, // NULL super class for A class

               // classdata
               (byte) 0x74, // TC_STRING
               (byte) 0x00, (byte) 0x04, // length
               (byte) 0x6e, (byte) 0x61, (byte) 0x6d, (byte) 0x65, // value
       };

       out.write(endStream, 0, endStream.length);
       out.flush();

       // read created serial. form
       ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(
               out.toByteArray()));
       Object o = ois.readObject();
       assertEquals(MessageBatch.class, o.getClass());

       // Regression for HARMONY-846
       assertNull(new ObjectInputStream() {}.readObject());
   }

}
