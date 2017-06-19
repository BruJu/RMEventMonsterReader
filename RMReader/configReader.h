#ifndef __H_CONFIGREADER__
#define __H_CONFIGREADER__

#include "types.h"

int BufferizedFile_nextString(BufferizedFile * file);
Grille * preparerTableur(BufferizedFile * file);
int lireUnFichier();
Grille * configReader();


#endif