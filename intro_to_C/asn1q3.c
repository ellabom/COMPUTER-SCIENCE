//NAME: EMMANUELLA EYO
//NSID: eee917
//STUDENT ID: 11291003

#include <stdio.h>
#include <math.h>


int xp_base(int monster_l, int monster_t){
    //function computes the value for base experience(xp_base) for each monster type
    //monster_l: interger parameter, from range 1 to 50 (inclusive)
    //monster_t: interger parameter, from range 1 to 3 (inclusive)
    //return: an integer for the calculated value of base experience
    int xp;
    
    if(monster_t == 1){
	xp = 100 + 2 * monster_l;
	return xp;
    }
    if(monster_t == 2){
	double power = pow(monster_l, 1.3);
	xp = 100 + power;
	return xp;
    }
    if(monster_t == 3){
	double power = pow(monster_l, 1.8);
	xp = 100 + power;
	return xp;
    }
    return 0;
}


int xp_adjust(int base_xp, int monster_l, int hero_l){
    //function calculates the value for the adjusted base type for each monster level and hero level
    //xp_adjust = xp_base * 1.1^(monster_l-hero_l)
    //base_xp: int value for base experienced calculated from xp_base
    //monster_l: interger parameter, from range 1 to 50 (inclusive)
    //monster_t: interger parameter, from range 1 to 3 (inclusive)
    //return: an integer for the calculated value of the adjusted base experience
    
    double power = pow(1.1, monster_l - hero_l);
    int adjust = base_xp * power;
    return adjust;

}


int main(){

    // declare and prompt user input for monster level
    int m_level;
    printf("What is the monster's level? ");
    scanf("%d", &m_level);


    //check for invalid inputs
    //and display error message  
    while(m_level < 1 || m_level > 50){
	printf("Error: monster level must be between 1 and 50.\n");
        printf("What is the monster's level? ");
        scanf("%d", &m_level);
    }

    // declare and prompt user input for monster's type
    int m_type;
    printf("What is the monster's type (1=normal, 2=elite, 3=boss)? ");
    scanf("%d", &m_type);


    //check for invalid inputs
    //and display error message  -
    while(m_type < 1 || m_type > 3){
	printf("Error: monster type must be 1 (normal) 2 (elite) or 3 (boss).\n");
        printf("What is the monster's type (1=normal, 2=elite, 3=boss)?  ");
        scanf("%d", &m_type);
    }

    
    // declare and prompt user input for hero level
    int h_level;
    printf("What is the hero's level? ");
    scanf("%d", &h_level);
    

    //check for invalid inputs
    //and display error message  
    while(h_level < 1 || h_level > 50){
	printf("Error: hero level must be between 1 and 50.\n");
        printf("What is the monster's level? ");
        scanf("%d", &h_level);
    }


    printf("The monster is level %d\n", m_level); 
    printf("The hero is level %d\n", h_level);

    int XP = xp_base(m_level, m_type);
    printf("The monster's base XP value is: %d\n", XP);
  
    int XP_a = xp_adjust(XP, m_level, h_level);
    printf("The monster's adjusted XP is: %d\n", XP_a);

    return 0;
}

