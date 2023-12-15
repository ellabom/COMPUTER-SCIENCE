#Emmanuella Eyo     A4      11291003    eee917

.data
    strSingleForward: .asciiz "Single-precision forward order:"
    strDoubleForward: .asciiz "Double-precision forward order:"
    strSingleReverse: .asciiz "Single-precision reverse order:"
    strDoubleReverse: .asciiz "Double-precision reverse order:"

    cStart: .asciiz " (first "
    cEnd: .asciiz " fraction field bits correct)"

    piSingle: .float 3.14159265358979323846264338327950288419716939937510582097
    piDouble: .double 3.14159265358979323846264338327950288419716939937510582097

    # format of tests: | function | output | isDouble |
    tests: .word singleForward strSingleForward 0 doubleForward strDoubleForward 1 singleReverse strSingleReverse 0 doubleReverse strDoubleReverse 1 0
    testSizes: .word 10 51 100 501 1000 5001 10000 50001 100000 0

.text

## DO NOT MODIFY ABOVE THIS LINE
## (you may change testSizes during testing, but change them back to the original values for submission)

# Calculates the value of pi (single-precision) using the first k terms of Nilakantha's series.
# args: $a0 = k
# ret:  $f0 = calculated estimate of pi
singleForward:
   
    li $t0, 3
    mtc1 $t0, $f0               # f0 = sum
    cvt.s.w $f0, $f0            # convert representation 

    li $t2, 0                   # counter

    li $t1, 0
    mtc1 $t1, $f2               # f2 = n
    cvt.s.w $f2, $f2            # convert representation 

    li $t0, 1           
    mtc1 $t0, $f4               # f4 = 1(constant)
    cvt.s.w $f4, $f4            # convert representation

    li $t0, -4                  # f6 = -4 (constant)
    mtc1 $t0, $f6           
    cvt.s.w $f6, $f6            # convert representation

    li $t0, 4
    mtc1 $t0, $f8               # f8 = 4 (constant)
    cvt.s.w $f8, $f8            # convert representation

    li $t0, 2
    mtc1 $t0, $f10              # f10 = 2 (constant)
    cvt.s.w $f10, $f10          # convert representation

    li $t0, 3
    mtc1 $t0, $f12              # f12 = 3 (constant)
    cvt.s.w $f12, $f12          # convert representation

sumPi:

    bgt $t2, $a0, done          # check if counter == k

    mul.s $f1, $f10, $f2        #f1=2(n)

    add.s $f14, $f1, $f10       #f14 = 2n + 2
    add.s $f16, $f1, $f12       #f16 = 2n + 3
    add.s $f18, $f1, $f8        #f18 = 2n + 4

    mul.s $f20, $f14, $f16      #$f20 = (2n + 2)(2n + 3)
    mul.s $f20, $f18, $f20      #f20 = ((2n + 2)(2n + 3)) * (2n + 4)

    addi $t2, $t2, 1            #increment counter
    add.s $f2, $f2, $f4         #increment n

    andi $t3, $t2, 1            #check if n is even or odd
    beq $t3, 1, even

    
    div.s $f22, $f6, $f20       #(-4)/(2n + 2)(2n + 3)(2n + 4)
    add.s $f0, $f0, $f22        # add current value to $f0

    j sumPi

even:
    div.s $f22, $f8, $f20        #(4)/(2n + 2)(2n + 3)(2n + 4)
    add.s $f0, $f0, $f22    

    j sumPi

done: 
    #add.s $f0, $f0, $f12
    jr $ra

# Calculates the value of pi (double-precision) using the first k terms of Nilakantha's series.
# args: $a0 = k
# ret:  $f0 = calculated estimate of pi
doubleForward:
    li $t0, 3
    mtc1 $t0, $f0               # f0 = sum
    cvt.s.w $f0, $f0            # convert representation 
    cvt.d.s $f0, $f0            # convert to double precision

    li $t2, 0                   # counter

    li $t1, 0
    mtc1 $t1, $f2               # f2 = n
    cvt.s.w $f2, $f2            # convert representation 
    cvt.d.s $f2, $f2            # convert to double precision 

    li $t0, 1           
    mtc1 $t0, $f4               # f4 = 1(constant)
    cvt.s.w $f4, $f4            # convert representation
    cvt.d.s $f4, $f4            # convert to double precision

    li $t0, -4                  # f6 = -4 (constant)
    mtc1 $t0, $f6           
    cvt.s.w $f6, $f6            # convert representation
    cvt.d.s $f6, $f6            # convert to  double precision

    li $t0, 4
    mtc1 $t0, $f8               # f8 = 4 (constant)
    cvt.s.w $f8, $f8            # convert representation
    cvt.d.s $f8, $f8            # convert to double precision

    li $t0, 2
    mtc1 $t0, $f10              # f10 = 2 (constant)
    cvt.s.w $f10, $f10          # convert representation
    cvt.d.s $f10, $f10          # convert to double precision

    li $t0, 3
    mtc1 $t0, $f12              # f12 = 3 (constant)
    cvt.s.w $f12, $f12          # convert representation
    cvt.d.s $f12, $f12          # convert to double precision

sumPiDouble:

    bgt $t2, $a0, end          # check if counter == k

    mul.d $f24, $f10, $f2        #f1=2(n)

    add.d $f14, $f24, $f10       #f14 = 2n + 2
    add.d $f16, $f24, $f12       #f16 = 2n + 3
    add.d $f18, $f24, $f8        #f18 = 2n + 4

    mul.d $f20, $f14, $f16      #$f20 = (2n + 2)(2n + 3)
    mul.d $f20, $f18, $f20      #f20 = ((2n + 2)(2n + 3)) * (2n + 4)

    addi $t2, $t2, 1            #increment counter
    add.d $f2, $f2, $f4         #increment n

    andi $t3, $t2, 1            #check if n is even or odd
    beq $t3, 1, evenD

    
    div.d $f22, $f6, $f20       #(-4)/(2n + 2)(2n + 3)(2n + 4)
    add.d $f0, $f0, $f22        # add current value to $f0

    j sumPiDouble

evenD:
    div.d $f22, $f8, $f20        #(4)/(2n + 2)(2n + 3)(2n + 4)
    add.d $f0, $f0, $f22    

    j sumPiDouble

end: 
    #add.d $f0, $f0, $f12
    jr $ra

# Calculates the value of pi (single-precision) using the first k terms of Nilakantha's series added in reverse order.
# args: $a0 = k
# ret:  $f0 = calculated estimate of pi
singleReverse:
    li $t2, 0                       # counter
    
    li $t0, 0 
    mtc1 $t0, $f0                   # f0 = sum
    cvt.s.w $f0, $f0                # convert representation 

    move $t0, $a0
    mtc1 $t0, $f2                   # f2  = k
    cvt.s.w $f2, $f2                # convert representation 

    li $t0, 0
    mtc1 $t0, $f4                   # f4 = n
    cvt.s.w $f4, $f4                # convert representation

    li $t0, 1
    mtc1 $t0, $f6                   # f6 = 1 = constant
    cvt.s.w $f6, $f6                # convert representation

    li $t0, 2
    mtc1 $t0, $f8                   # f8 = 2
    cvt.s.w $f8, $f8                # convert representation 

    li $t0, 4
    mtc1 $t0, $f10                  # f10 = 4
    cvt.s.w $f10, $f10              # convert representation

    li $t0, -4
    mtc1 $t0, $f12                  # f12 = -4
    cvt.s.w $f12, $f12              # convert representation

    li $t0, 3
    mtc1 $t0, $f14                  # f14 = 3
    cvt.s.w $f14, $f14              # convert representation
    

#k+1 = upper bount
#so< k+1-1 = k, 
#for example, if k =3, k+1 = 4
#so k-1-n = 3 - n
sumReverse:

    bgt $t2, $a0, endR              # if counter == k , end loop

    sub.s $f16, $f2, $f4            #(k-n)

    mul.s $f18, $f8, $f16           #2(k-1-n)

    move $t0, $a0
    sub $t4, $t0, $t2               # k - n 

    add.s $f20, $f18, $f8           #2(k-1-n) + 2
    add.s $f22, $f18, $f14          #2(k-1-n) + 3
    add.s $f24, $f18, $f10          #2(k-1-n) + 4

    mul.s $f26, $f20, $f22          #f26 = (2n + 2)(2n + 3)
    mul.s $f26, $f26, $f24          #f26 = (2n + 2)(2n + 3)(2n + 4)

    addi $t2, $t2, 1                #increment counter
    add.s $f4, $f4, $f6             #increment n 

    andi $t3, $t4, 1                #check if n is even(0) or odd(1)
    beq $t3, 0, evenR               #if t3 == 0, branch


    div.s $f28, $f12, $f26          #(4)/(2n + 2)(2n + 3)(2n + 4)
    add.s $f0, $f0, $f28            #add current value to the sum

    j sumReverse

evenR:
    div.s $f28, $f10, $f26          #(-4)/(2n + 2)(2n + 3)(2n + 4)
    add.s $f0, $f0, $f28            #add current value to sum

    j sumReverse

endR:
    add.s $f0, $f0, $f14            # add 3 after summation 
    jr $ra

# Calculates the value of pi (double-precision) using the first k terms of Nilakantha's series added in reverse order.
# args: $a0 = k
# ret:  $f0 = calculated estimate of pi
doubleReverse:
    li $t2, 0                       # counter
    
    li $t0, 0 
    mtc1 $t0, $f0                   # f0 = sum
    cvt.s.w $f0, $f0                # convert representation
    cvt.d.s $f0, $f0                # convert to double precision

    move $t0, $a0
    mtc1 $t0, $f2                   # f2  = k
    cvt.s.w $f2, $f2                # convert representation 
    cvt.d.s $f2, $f2                # convert to double precision

    li $t0, 0
    mtc1 $t0, $f4                   # f4 = n
    cvt.s.w $f4, $f4                # convert representation
    cvt.d.s $f4, $f4                # convert to double precision

    li $t0, 1
    mtc1 $t0, $f6                   # f6 = 1 = constant
    cvt.s.w $f6, $f6                # convert representation
    cvt.d.s $f6, $f6                # convert to double precision

    li $t0, 2
    mtc1 $t0, $f8                   # f8 = 2
    cvt.s.w $f8, $f8                # convert representation 
    cvt.d.s $f8, $f8                # convert to double precision

    li $t0, 4
    mtc1 $t0, $f10                  # f10 = 4
    cvt.s.w $f10, $f10              # convert representation
    cvt.d.s $f10, $f10              # convert to double precision

    li $t0, -4
    mtc1 $t0, $f12                  # f12 = -4
    cvt.s.w $f12, $f12              # convert representation
    cvt.d.s $f12, $f12              # convert to double precision

    li $t0, 3
    mtc1 $t0, $f14                  # f14 = 3
    cvt.s.w $f14, $f14              # convert representation
    cvt.d.s $f14, $f14              # convert to double precision
    

#k+1 = upper bount
#so< k+1-1 = k, 
#for example, if k =3, k+1 = 4
#so k-1-n = 3 - n
sumDReverse:

    bgt $t2, $a0, endDR              # if counter == k , end loop

    sub.d $f16, $f2, $f4            #(k-n)

    mul.d $f18, $f8, $f16           #2(k-1-n)

    move $t0, $a0
    sub $t4, $t0, $t2               # k - n 

    add.d $f20, $f18, $f8           #2(k-1-n) + 2
    add.d $f22, $f18, $f14          #2(k-1-n) + 3
    add.d $f24, $f18, $f10          #2(k-1-n) + 4

    mul.d $f26, $f20, $f22          #f26 = (2n + 2)(2n + 3)
    mul.d $f26, $f26, $f24          #f26 = (2n + 2)(2n + 3)(2n + 4)

    addi $t2, $t2, 1                #increment counter
    add.d $f4, $f4, $f6             #increment n 

    andi $t3, $t4, 1                #check if n is even(0) or odd(1)
    beq $t3, 0, evenDR               #if t3 == 0, branch


    div.d $f28, $f12, $f26          #(4)/(2n + 2)(2n + 3)(2n + 4)
    add.d $f0, $f0, $f28            #add current value to the sum

    j sumDReverse

evenDR:
    div.d $f28, $f10, $f26          #(-4)/(2n + 2)(2n + 3)(2n + 4)
    add.d $f0, $f0, $f28            #add current value to sum

    j sumDReverse

endDR:
    add.d $f0, $f0, $f14            # add 3 after summation 
    jr $ra

## DO NOT MODIFY BELOW THIS LINE

main:
    la $s0, tests           # $s0 = pointer in tests
    mainLoop:
    lw $s1, 0($s0)          # $s1 = current function pointer
    beqz $s1, terminate
    li $v0, 11
    li, $a0, 10
    syscall
    lw $a0, 4($s0)
    li $v0, 4
    syscall
    lw $s2, 8($s0)          # $s2 = isDouble
    addi $s0, $s0, 12
    la $s3, testSizes       # $s3 = pointer in test sizes
    testLoop:
    lw $s4, 0($s3)          # $s4 = current test size
    addi $s3, $s3, 4
    beqz $s4, mainLoop
    li $v0, 11
    li, $a0, 10
    syscall
    move $a0, $s4
    li $v0, 1
    syscall
    li $v0, 11
    li, $a0, 9
    syscall
    move $a0, $s4
    jalr $ra, $s1
    bnez $s2, printDouble
    li $v0, 2
    mov.s $f12, $f0
    syscall
    li $v0, 4
    la $a0, cStart
    syscall
    jal checkBitsPiSingle
    move $a0, $v0
    li $v0, 1
    syscall
    li $v0, 4
    la $a0, cEnd
    syscall
    b testLoop
    printDouble:
    li $v0, 3
    mov.d $f12, $f0
    syscall
    li $v0, 4
    la $a0, cStart
    syscall
    jal checkBitsPiDouble
    move $a0, $v0
    li $v0, 1
    syscall
    li $v0, 4
    la $a0, cEnd
    syscall
    b testLoop

    terminate:
    li $v0, 10
    syscall

# Determines how many of the fractional field bits of a given single-precision float agree with the value of pi
# args: $f0 = single-precision value to check
# ret:  $v0 = count of bits (negative value indicates sign/exponent did not agree)
checkBitsPiSingle:
    li $v0, 0               # $v0 = sum
    li $t0, 0x80000000      # $t0 = highest order bit mask
    la $t1, piSingle
    lw $t1, 0($t1)
    mfc1 $t2, $f0
    checkLoopSingle:
    xor $t3, $t1, $t2
    and $t3, $t3, $t0
    bnez $t3 checkReturnSingle
    sll $t1, $t1, 1
    sll $t2, $t2, 1
    addi $v0, $v0 1
    bne $v0, 32 checkLoopSingle
    checkReturnSingle:
    addi $v0, $v0, -9
    jr $ra

# Determines how many of the fractional field bits of a given double-precision float agree with the value of pi (double-precision)
# args: $f0 = double-precision value to check
# ret:  $v0 = count of bits (negative value indicates sign/exponent did not agree)
checkBitsPiDouble:
    li $v0, 0
    li $t0, 0x80000000
    la $t1, piDouble
    lw $t1, 4($t1)
    mfc1 $t2, $f1
    checkLoopDouble:
    xor $t3, $t1, $t2
    and $t3, $t3, $t0
    bnez $t3 checkReturnDouble
    sll $t1, $t1, 1
    sll $t2, $t2, 1
    addi $v0, $v0 1
    bne $v0, 32 checkLoopDouble
    la $t1, piDouble
    lw $t1, 0($t1)
    mfc1 $t2, $f0
    lowerCheckLoopDouble:
    xor $t3, $t1, $t2
    and $t3, $t3, $t0
    bnez $t3 checkReturnDouble
    sll $t1, $t1, 1
    sll $t2, $t2, 1
    addi $v0, $v0 1
    bne $v0, 64 lowerCheckLoopDouble
    checkReturnDouble:
    addi $v0, $v0, -12
    jr $ra