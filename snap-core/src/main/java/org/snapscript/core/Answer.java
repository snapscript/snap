package org.snapscript.core;

public interface Answer {
   void success(Object value);
   void failure(Throwable cause);
}
