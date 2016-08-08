package org.snapscript.core.link;

public interface PackageLinker {  
   Package link(String resource, String source) throws Exception;
   Package link(String resource, String source, String grammar) throws Exception;
}
