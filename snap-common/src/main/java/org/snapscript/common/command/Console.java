package org.snapscript.common.command;

import java.io.IOException;
import java.util.List;

public interface Console {
   List<String> readAll() throws IOException;
   String readLine() throws IOException;
}
