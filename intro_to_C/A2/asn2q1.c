
#include <stdio.h>
#include <math.h>
#include <stdlib.h>

/*
 * Compute the base experience point value for a monster.
 *
 * monster_level:  The monster's level, assumed to be between 1 and 50.
 * monster_type:   The monster's type, assumed to be between 1 and 3.
 *                 1=normal, 2=elite, 3=boss
 *
 * returns: the base experience point value of the monster described by the parameters.
 */
int base_xp(int monster_level, int monster_type) {

    // This new version of base_xp() doesn't assume everything is OK,
    // rather it terminates the program gracefully if it detects invalid data.

    // If monster level is out of range ...
    if( monster_level < 1 || monster_level > 50) {
        // Print an informative error message and terminate the program with an "abnormal" (non-zero) exit status.
        printf("Monster level of %d is out of the valid range of 1 through 50.", monster_level);
        exit(1);
    }

    if(monster_type == 1) {
        // normal monster
        return (100 + 2 * monster_level);
    }
    else if(monster_type == 2) {
        // elite monster
        return (100 + pow(monster_level, 1.3));
    }
    else if(monster_type == 3) {
        // boss monster
        return (100 + pow(monster_level, 1.8));
    }
    else {
        // The monster type was an illegal value.
        // Print an informative error message and terminate the program with an "abnormal" (non-zero) exit status.
        printf("The monster type of %d was invalid, it must be 1, 2, or 3.", monster_type);
        exit(1);
    }
}

/*
 * Computes the adjusted experience point value of a monster accounting for difference in level between
 * the hero and the monster.
 *
 * monster_level:  the monster's level, assumed to be between 1 and 50.
 * hero_level:     the hero's level, assumed to be between 1 and 50.
 * base_xp:        the base experience point value of the monster (e.g. computed using base_xp())
 *
 * returns:        the adjusted experience of the monster described by the parameters
 *                 when killed by a hero of the specified hero_level.
 */
int adjusted_xp(int monster_level, int hero_level, int base_xp) {

    // This new version of adjusted_xp() doesn't assume everything is OK,
    // rather it terminates the program gracefully if it detects invalid data.

    // If monster level is out of range ...
    if( monster_level < 1 || monster_level > 50) {
        // Print an informative error message and terminate the program with an "abnormal" (non-zero) exit status.
        printf("Monster level of %d is out of the valid range of 1 through 50.", monster_level);
        exit(1);
    }

    // If hero level is out of range ...
    if( hero_level < 1 || hero_level > 50) {
        // Print an informative error message and terminate the program with an "abnormal" (non-zero) exit status.
        printf("Hero level of %d is out of the valid range of 1 through 50.", hero_level);
        exit(1);
    }
    return base_xp * pow(1.1, (monster_level - hero_level));
}


int main() {

    int start_level = 0;
    int end_level = 0;
    int monster_type = 0;
    int hero_level = 0;

    // Read all the inputs.  We don't require error checking for this program.
    // Fortunately, the new, more robust base_xp() and adjusted_xp() functions
    // do a lot of the error checking for us.

    printf("Monster starting level: ");
    scanf("%d", &start_level);
    printf("Monster ending_level: ");
    scanf("%d", &end_level);
    printf("Monster type (1=normal, 2=elite, 3=boss): ");
    scanf("%d", &monster_type);
    printf("Hero Level: ");
    scanf("%d", &hero_level);

    printf("Generating experience point table for Hero Level = %d   Monster type = %d\n", hero_level, monster_type);
    printf("%3s        %16s\n","Monster Level", "Experience Points (Adjusted)");
    for(int i = start_level; i <= end_level; i++){
        int base = base_xp(i, monster_type);
        int adjusted = adjusted_xp(i, hero_level, base);

        printf("%13d    %30d\n",i, adjusted);
    }
    return 0;
}

