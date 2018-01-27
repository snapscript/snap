package org.snapscript.core.shell;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectStreamClass;
import java.io.Serializable;

public class SerializationBuilder implements ShellBuilder {
   
   public SerializationBuilder() {
      super();
   }

   @Override
   public Object create(Class type) {
      try {
         if(Serializable.class.isAssignableFrom(type)) {
            return createObject(type);
         }
      } catch (Exception e) {
         return null;
      }
      return null;
   }

   private Object createObject(Class type) throws Exception {
      String name = type.getName();
      ObjectStreamClass definition = ObjectStreamClass.lookup(type);
      long version = definition.getSerialVersionUID();
      byte[] data = createStream(name, version);

      return createObject(data);
   }

   private Object createObject(byte[] data) throws Exception {
      ByteArrayInputStream source = new ByteArrayInputStream(data);
      ObjectInputStream input = new ObjectInputStream(source);

      return input.readObject();
   }

   private byte[] createStream(String name, long version) throws Exception {
      ByteArrayOutputStream stream = new ByteArrayOutputStream();

      byte[] header = new byte[] {
              (byte) 0xac,
              (byte) 0xed, // STREAM_MAGIC
              (byte) 0x00,
              (byte) 0x05, // STREAM_VERSION
              (byte) 0x73, // TC_OBJECT
              (byte) 0x72, // TC_CLASSDESC
              (byte) 0x00, // only first byte for class name length
      };
      byte[] type = name.getBytes("UTF-8");

      stream.write(header, 0, header.length);
      stream.write(type.length); // second byte for class name length
      stream.write(type, 0, type.length); // class name

      byte[] midStream = new byte[] {
              (byte) ((version >>> 56) & 0xff),
              (byte) ((version >>> 48) & 0xff),
              (byte) ((version >>> 40) & 0xff),
              (byte) ((version >>> 32) & 0xff),
              (byte) ((version >>> 24) & 0xff),
              (byte) ((version >>> 16) & 0xff),
              (byte) ((version >>> 8) & 0xff),
              (byte) ((version) & 0xff),
              (byte) 0x02, // flags
              (byte) 0x00,
              (byte) 0x00, // fields : none
              (byte) 0x78, // TC_ENDBLOCKDATA
              (byte) 0x70, // NULL super class for A class
      };

      stream.write(midStream, 0, midStream.length);
      stream.flush();

      return stream.toByteArray();
   }

}

