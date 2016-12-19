package org.snapscript.core;

public enum StateType {
   COMPILE(0x00000000),
   MODEL(0x01000000),
   STACK(0x02000000),
   MODULE(0x04000000),
   ANY(0x08000000),
   SUPER(0x10000000),
   STATIC(0x20000000),
   OBJECT(0x40000000),
   CLOSURE(0x80000000);
   
   public final int mask;
   
   private StateType(int mask) {
      this.mask = mask;
   }
   
   public int getMask() {
      return mask;
   }
}
