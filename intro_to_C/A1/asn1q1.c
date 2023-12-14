#include <stdio.h>

int main(){
    
    int integer;
    double realNumber;
    int integer2;

    printf("Enter an integer: ");
    scanf("%d", &integer);

    printf("Enter a decimal number: ");
    scanf("%lf", &realNumber);

    printf("Enter an integer: ");
    scanf("%d", &integer2);

    printf("Zack and Dylan had a went from house to house collecting surverys. At the end of each day they had collected about %d surveys. They were to split the total into 2 after each collection. They had walked %.2lf miles today. At the end of the month they each get $ %d for their contribution to the research company.\n", integer, realNumber, integer2);

    return 0;

}