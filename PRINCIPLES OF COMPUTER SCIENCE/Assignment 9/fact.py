# fact.py
  
example = 10
 
def factorial(x):
    """
    Purpose:
        Calculate the product of numbers 1 to x.
    Pre-conditions:
        x: a positive integer
    Post-conditions:
        none
    Return:
        an integer
    """
    total = 1
    for i in range(1,x+1):
        total *= i 
    return total

print(factorial(example))

