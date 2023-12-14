#include <stdio.h>
#include <math.h>


int main(){
     
    int monster;
    int hero;

    printf("What is the monster's level? ");
    scanf("%d", &monster);
    printf("What is the hero's level? ");
    scanf("%d", &hero);

    printf("The monster is level %d\n", monster);
    printf("The hero is level %d\n", hero);

    int xp_base = 100 + 2 * monster;
    double result = pow(1.1, m-h);
    int xp_adjust = xp_base * result;

    printf("The monster's base XP value is %d\n", xp_base);
    printf("The monster's adjusted XP is %d\n", xp_adjust);

    return 0;



}