package org.snapscript.core.link;

import org.snapscript.core.Path;

public interface PackageLinker {  
   Package link(Path resource, String source) throws Exception;
   Package link(Path resource, String source, String grammar) throws Exception;
}
