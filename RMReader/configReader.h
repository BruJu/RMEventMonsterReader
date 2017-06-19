#ifndef __H_CONFIGREADER__
#define __H_CONFIGREADER__

#include "types.h"


Grille * preparerTableur(BufferizedFile * file);

int lireUnFichier();

Grille * configReader();


#endif