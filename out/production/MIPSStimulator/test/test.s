.data
.data
value:  .word 0, 0, 0 ,0 ,0     # 0: first num ,4 : second num , 8 : operation , 12:result
msg0        :   .asciiz " please choose the operation(1~4):/n/t/t1 : +,addition /n/t/t2 : -,subtracter/n/t/t3 : * multiplication /n/t/t4 : /,division/n"
msg1        :   .asciiz "first num:"
msg2        :   .asciiz "second num:", "hh"
addFlag     :   .asciiz " + "
subFlag     :   .asciiz " - "
mulFlag     :   .asciiz " * "
divFlag     :   .asciiz " / "
equalStr    :   .asciiz " = "
newline     :   .asciiz "/n===============================/n"
byte_area:
    .byte 0b00110011, 0b11001100
    .byte 0b00110011, 0b11001100
    .byte 0b00110011, 0b11001100
    .byte 0b00110011, 0b11001100
    .byte 0b00110011, 0b11001100
    .byte 0b00110011, 0b11001100
    .byte 0b00110011, 0b11001100
    .byte 0b00110011, 0b11001100
    .byte 0b00110011, 0b11001100
    .byte 0b00110011, 0b11001100
#word_:
#    .word 13,0xf0aaffa5
string_ :
    .asciiz "fefe", "c"

.text
a:
   nor $t1, $t1, $t1
   # lw $t0, 0x77           # load address "value" into $t0
    add $t1, $t0, $t1
   # li $v0,4
   # syscall             #print "please choose the operation:"

   # li  $v0, 5
   # syscall
    lw  $v0, 8($t0)

    j _main
    lui $v0,_main

    syscall             #print "first num:"
_main:
   # li  $v0, 5
   # syscall
    sw  $v0, 0($t0)
#.data
#  word: .asciiz "a"

#.text
#  .globl main
#  .align 2

main:
#  la $a0, word
#  jal strlen

#  move $a0, $v0                   # ;move the length of the string into the arguement for the syscall
 # li $v0, 1                       # indicates that we are printing out an integer
#  syscall                         # print out the length of the string

 # li $v0, 10
#  syscall

strlen:
  addi $sp, $sp, -8
  sw $ra, 0($sp)
  sw $s0, 4($sp)

#  lbu $s0, 0($a0)
#  bnez $s0, recurse
#  li $v0, 1
  j end

recurse:
  addi $a0, $a0, 1
 # jal strlen
  addi $v0, $v0, 1

end:
  addi $sp, $sp, 8
  lw $ra, 0($sp)
  lw $s0, 4($sp)
  jr $ra