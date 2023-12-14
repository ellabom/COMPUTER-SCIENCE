#include <stdio.h>

int main() {
    int x_pos=0; int y_pos=0;	// Set the initial position to (0,0)
    int quit=0;
    char move='0';

    while( !quit ) {
        printf( "current position is (%d,%d)\n", x_pos, y_pos);
        printf( "move: ");
        scanf( "%c", &move);
        switch ( move ) {
            case 'U':
            case 'u':
                y_pos = y_pos + 1;
                break;

            case 'D':
            case 'd':
                y_pos = y_pos - 1;
                break;

            case 'l':
            case 'L':
                x_pos = x_pos - 1;
                break;

            case 'r':
            case 'R':
                x_pos = x_pos + 1;
                break;

            case 'q':
            case 'Q':
                quit = 1;
                break;

            default:
                printf("Invalid input");
                break;


            // Complete the switch-case statement so that the charater inputs described in the assignment
            // specification cause the corresponding actions in the assignment description to occur.

        }
        // To clear the input buffer of extra characters entered at the time of scanf() beyond the first.
        // This is only necessary because we are using %c to read a single character.  This will cause
        // any characters entered beyond the first to be ignored.  If the user enters multiple charaters at once
        // the switch statement, above, will only ever see the first characters.  The empty loop below
        // reads the remaining characters and ignores them.
        while ((getchar()) != '\n');
    }
    printf( "final position is (%d,%d)\n", x_pos, y_pos);
}
