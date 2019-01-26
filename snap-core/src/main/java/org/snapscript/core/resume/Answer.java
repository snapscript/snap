package org.snapscript.core.resume;

public interface Answer {
   void success(Object value);
   void failure(Throwable cause);
}
