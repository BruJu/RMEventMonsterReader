#include "tools.H"

void string_retirerSauts(char * chaine) {
    int c;
    
    c = 0;
    
    while (chaine[c]) {
        if (chaine[c] == '\r' || chaine[c] == '\n') {
            chaine[c] = 0;
            break;
        }
        
        c++;
    }
    
    if (c == 0)
        return;
    
    c--;
    
    while (chaine[c] == ' ') {
        chaine[c] = 0;
        c--;
    }
}