## EMMANUELLA EYO   11291003    eee917
.data
    initialPrompt: .asciiz "Would you like to encode ('e') or decode ('d')? "
    decodeTree: .word _e _t 0; _e: .word _i _a 'e'; _t: .word _n _m 't'; _i: .word _s _u 'i'; _a: .word _r _w 'a'; _n: .word _d _k 'n'; _m: .word _g _o 'm'; _s: .word _h _v 's'; _u: .word _f 0 'u'; _r: .word _l 0 'r'; _w: .word _p _j 'w'; _d: .word _b _x 'd'; _k: .word _c _y 'k'; _g: .word _z _q 'g'; _o: .word 0 0 'o'; _h: .word 0 0 'h'; _v: .word 0 0 'v'; _f: .word 0 0 'f'; _l: .word 0 0 'l'; _p: .word 0 0 'p'; _j: .word 0 0 'j'; _b: .word 0 0 'b'; _x: .word 0 0 'x'; _c: .word 0 0 'c'; _y: .word 0 0 'y'; _z: .word 0 0 'z'; _q: .word 0 0 'q';
    encodeArray: .word _ea _eb _ec _ed _ee _ef _eg _eh _ei _ej _ek _el _em _en _eo _ep _eq _er _es _et _eu _ev _ew _ex _ey _ez; _ea: .asciiz ".-"; _eb: .asciiz "-..."; _ec: .asciiz "-.-."; _ed: .asciiz "-.."; _ee: .asciiz "."; _ef: .asciiz "..-."; _eg: .asciiz "--."; _eh: .asciiz "...."; _ei: .asciiz ".."; _ej: .asciiz ".---"; _ek: .asciiz "-.-"; _el: .asciiz ".-.."; _em: .asciiz "--"; _en: .asciiz "-."; _eo: .asciiz "---"; _ep: .asciiz ".--."; _eq: .asciiz "--.-"; _er: .asciiz ".-."; _es: .asciiz "..."; _et: .asciiz "-"; _eu: .asciiz "..-"; _ev: .asciiz "...-"; _ew: .asciiz ".--"; _ex: .asciiz "-..-"; _ey: .asciiz "-.--"; _ez: .asciiz "--..";
    decodeBuffer: .space 5
    slash: .asciiz "/"
.text

# DO NOT ADD OR MODIFY ABOVE THIS LINE

main:
    li $t0, 0xFFFF0000          # $t0 = keyboard status address
    li $t1, 0xFFFF0004          # $t1 = keyboard data address
    li $t2, 0xFFFF0008          # $t2 = console status address
    li $t3, 0xFFFF000C          # $t3 = console data address
   li $t6, '\n'

loop:
    la $a0, initialPrompt       # load address for prompt
    li $v0, 4                   # syscall to print string
    syscall

inLoop:
    lw $t4, ($t0)               # check if keyboard status
    beqz $t4, inLoop            # if keyboard doesn't have a character,check again
    lw $t5, ($t1)               # if we get here, keyboard has a character, so get it 

    blt $t5, 'd', terminate     # if less than d, terminate
    bgt $t5, 'e', terminate     # if greater than e, terminate
    
    j outLoop

outLoop:
    lw $t4, ($t2)               # check if console is ready
    beqz $t4, outLoop           # if console if not ready, check again
    sw $t5, ($t3)               # if we get here, console is ready, so send character 

    j printNewLine

printNewLine:
    lw $t4, ($t2)               # check if console is ready
    beqz $t4, printNewLine      # if console if not ready, check again
    sw $t6, ($t3)               # if we get here, console is ready, so send character

    beq $t5, 'd', decodee
    beq $t5, 'e', encodee

decodee:
    jal decode

    j loop

encodee:
    jal encode

    j loop

terminate:
    li $v0, 10
    syscall

# Reads input from the console one character at a time. Each time a character
# is typed, the corresponding morse code symbols are output to the console,
# followed by a space. If enter is pressed, it is echoed to the console, and
# the procedure returns. If a character cannot be encoded, it is ignored.
encode:
    addi $sp, $sp, -4           # allocate stack memory
    sw $ra, 0($sp)
encodeLoop:
    lw $t4, ($t0)               # check if keyboard status
    beqz $t4, encodeLoop        # if keyboard doesn't have a character,check again
    lw $t5, ($t1)               # if we get here, keyboard has a character, so get it 

    beq $t5, '\n', returnEncode   # if equal to enterkey, return

    move $a0, $t5
    jal encodeChar
    lw $ra, 0($sp)
    move $s0, $v0               # move return value to t7

    li $t0, 0xFFFF0000          # $t0 = keyboard status address
    li $t1, 0xFFFF0004          # $t1 = keyboard data address
    li $t2, 0xFFFF0008          # $t2 = console status address
    li $t3, 0xFFFF000C          # $t3 = console data address
    li $t6, '\n'

    beqz $s0, encodeLoop        # if return value is 0, loop again

    j encodePrint

encodePrint:

printchar:
    lb $t8, ($s0)              # load each byte in string t7

    beq $t8, '\n', returnEncode
    beqz $t8, encodeLoop        # if end of string, go back to encodeLoop
    
    lw $t4, ($t2)               # check if console is ready
    beqz $t4, printchar         # if console if not ready, check again
    sw $t8, ($t3)               # if we get here, console is ready, so send character

    addi, $s0, $s0, 1

    j printchar

returnEncode:
    addi, $sp, $sp, 4

    printNewline1:
        lw $t4, ($t2)                # check if console is ready
        beqz $t4, printNewline1      # if console if not ready, check again
        sw $t6, ($t3)                # if we get here, console is ready, so send character

    jr $ra

# Reads input from the console in morse code format. The input is placed in a
# buffer. When space is pressed, the buffer is zero-terminated and passed to
# decodeChar be decoded. The decoded character is printed to the console. If 
# enter is pressed, it is echoed to the console, and the procedure returns. 
# If the character cannot be decoded, it is ignored.
decode:
    ##decode is not working properly, it returs back to loop after enterkey is pressed
    la $a0, decodeBuffer
    move $t8, $a0               # store address of decodeBuffer

    addi $sp, $sp, -4           # allocate space on the stack
    sw $ra, 0($sp)

inLoop1:
    lw $t4, ($t0)               # check if keyboard status
    beqz $t4, inLoop1           # if keyboard doesn't have a character,check again
    lw $t5, ($t1)               # if we get here, keyboard has a character, so get it 


    beq $t5, ' ', spaceKey
    beq $t5, '\n', return         # if input is enterKey, return

    j saveBuffer
    
spaceKey:
    sb $zero, ($t8)             # add null character to decodeBuffer

    move $a0, $t8
    jal decodeChar              # pass decodeBuffer as argument for decodeChar
    lw $ra, 0($sp)

    move $s1, $v0               # move return value

    li $t0, 0xFFFF0000          # $t0 = keyboard status address
    li $t1, 0xFFFF0004          # $t1 = keyboard data address
    li $t2, 0xFFFF0008          # $t2 = console status address
    li $t3, 0xFFFF000C          # $t3 = console data address
    li $t6, '\n'

    beqz $s1, inLoop1           # if return value was 0, loop again

    j printChar

saveBuffer:
    sb $t5, ($t8)               # save byte to $s0

    addi $t8, $t8, 4

    j inLoop1

printChar:
    lw $t4, ($t2)               # check if console is ready
    beqz $t4, PrintChar         # if console if not ready, check again
    sw $s1, ($t3)               # if we get here, console is ready, so send character 

    j inLoop1


return:

    addi, $sp, $sp, 4

    printNewline2:
        lw $t4, ($t2)                # check if console is ready
        beqz $t4, printNewline2      # if console if not ready, check again
        sw $t6, ($t3)                # if we get here, console is ready, so send character

    jr $ra


# DO NOT ADD OR MODIFY BELOW THIS LINE

# Decodes a series of morse code symbols and returns a single character
# arg: $a0: address of a (zero terminated) string containing morse code symbols
# ret: $v0: the decoded character; 0 on error
decodeChar:
    lb $t0, 0($a0)
    beq $t0, '/', decodeReturnSpace
    la $t0, decodeTree
    decodeCharLoop:
    beq $t0, $0, decodeReturnNull
    lb $t1, 0($a0)
    addi $a0, $a0, 1
    beqz $t1, decodeReturnLetter
    beq $t1, '.', decodeDot
    beq $t1, '-', decodeDash
    b decodeReturnNull
    decodeDot:
    lw $t0, 0($t0)
    b decodeCharLoop
    decodeDash:
    lw $t0, 4($t0)
    b decodeCharLoop

    decodeReturnSpace:
    li $v0, 32
    jr $ra
    decodeReturnLetter:
    lw $v0, 8($t0)
    jr $ra
    decodeReturnNull:
    li $v0, 0
    jr $ra

# Gives the encoding of a character in morse code
# arg: $a0: the character to encode (lower case letters and space only)
# ret: $v0: the address of a string containing the morse code encode; 0 on error
encodeChar:
    beq $a0, ' ', encodeCharRetSlash
    blt $a0, 'a', encodeCharRetError
    bgt $a0, 'z', encodeCharRetError
    addi $a0, $a0, -97
    la $t0, encodeArray
    sll $a0, $a0, 2
    add $t0, $t0, $a0
    lw $v0, 0($t0)
    jr $ra
    encodeCharRetError:
    li $v0, 0
    jr $ra
    encodeCharRetSlash:
    la $v0, slash
    jr $ra