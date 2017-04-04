
package org.snapscript.parse;

public interface TextCategory {   
   short[] INDEX = {
   0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000,
   0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000,
   0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000,
   0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000,
   0x0000, 0x0000, 0x0420, 0x0000, 0x0800, 0x0000, 0x0000, 0x0420,
   0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0100, 0x0040, 0x0000,
   0x001e, 0x001e, 0x0016, 0x0016, 0x0016, 0x0016, 0x0016, 0x0016,
   0x0016, 0x0016, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000,
   0x0000, 0x1015, 0x1015, 0x1015, 0x1095, 0x1015, 0x1095, 0x1011,
   0x1011, 0x1011, 0x1011, 0x1011, 0x1091, 0x1011, 0x1011, 0x1011,
   0x1011, 0x1011, 0x1011, 0x1011, 0x1011, 0x1411, 0x1011, 0x1011,
   0x1011, 0x1011, 0x1011, 0x0000, 0x0600, 0x0000, 0x0000, 0x0010,
   0x0000, 0x0015, 0x0415, 0x0015, 0x0095, 0x0015, 0x0495, 0x0011,
   0x0011, 0x0011, 0x0011, 0x0011, 0x0091, 0x0011, 0x0411, 0x0011,
   0x0011, 0x0011, 0x0411, 0x0011, 0x0411, 0x0411, 0x0011, 0x0011,
   0x0011, 0x0011, 0x0011, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000,
   0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000,
   0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000,
   0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000,
   0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000,
   0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000,
   0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000,
   0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000,
   0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000,
   0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000,
   0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000,
   0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000,
   0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000,
   0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000,
   0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000,
   0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000,
   0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000};

   short NONE = 0x0000;
   short LETTER = 0x0001;
   short DIGIT = 0x0002;
   short HEXIDECIMAL = 0x0004;
   short BINARY = 0x0008;
   short IDENTIFIER = 0x0010;   
   short QUOTE = 0x0020;
   short PERIOD = 0x0040;
   short SUFFIX = 0x0080;
   short MINUS = 0x0100;    
   short ESCAPE = 0x0200;
   short SPECIAL = 0x0400;
   short DOLLAR = 0x0800;
   short CAPITAL = 0x1000;
}
