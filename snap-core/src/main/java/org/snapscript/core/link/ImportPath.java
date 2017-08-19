package org.snapscript.core.link;

import java.util.Map;
import java.util.Set;

public interface ImportPath {
   Map<String, String> getAliases();
   Map<String, String> getTypes();
   Set<String> getDefaults();
}