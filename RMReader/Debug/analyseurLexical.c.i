# 1 "analyseurLexical.c"
# 1 "A:\\Users\\Dheim\\Documents\\GitHub\\RMEventMonsterReader\\RMReader//"
# 1 "<built-in>"
# 1 "<command-line>"
# 1 "analyseurLexical.c"
# 1 "types.h" 1



typedef enum {
    ChgSwitch,
    ChgVariable,
    Label,
    JumpToLabel,
    ForkIf,
    ForkElse,
    ForkEnd,
    Loop,
    LoopEnd,
    LoopBreak,
    ShowPicture,
    ChangeItem,
    Void,
    Instr_Erreur,
    Ignore
} Instruction;

typedef enum {
    Egal, Different,
    Plus, Moins, Fois, Divise, Modulo,
    Inferieur, Superieur,
    InfEgal, SupEgal
} Signe;

typedef union {
    struct {
        int numero;
        Signe signe;
        int nouvelleValeur;
    } affectation;
    int numero;

    struct {
        int type;
        int numero;
        Signe comparatif;
        int valeur;
        int pointeur;
    } forkIf;

    struct {
        int numero;
        int quantite;
    } changeItem;
    char showPicture[255];

} InstructionBis;

typedef struct {
    Instruction instruction;
    InstructionBis complement;
} InstructionEnsemble;


typedef struct {
    int id;
    char nom[100];
} Entree;

typedef struct {
    int nombredEntrees;
    int capacite;
    Entree * entree;
} Dictionnaire;


typedef struct {
    int id[50];
    char nom[100];
} EntreeChar;

typedef struct {
    int nombredEntrees;
    int capacite;
    EntreeChar * entree;
} DictionnaireChar;
# 2 "analyseurLexical.c" 2
# 1 "c:\\mingw\\include\\io.h" 1 3
# 38 "c:\\mingw\\include\\io.h" 3
       
# 39 "c:\\mingw\\include\\io.h" 3
# 51 "c:\\mingw\\include\\io.h" 3
# 1 "c:\\mingw\\include\\_mingw.h" 1 3
# 55 "c:\\mingw\\include\\_mingw.h" 3
       
# 56 "c:\\mingw\\include\\_mingw.h" 3
# 66 "c:\\mingw\\include\\_mingw.h" 3
# 1 "c:\\mingw\\include\\msvcrtver.h" 1 3
# 35 "c:\\mingw\\include\\msvcrtver.h" 3
       
# 36 "c:\\mingw\\include\\msvcrtver.h" 3
# 67 "c:\\mingw\\include\\_mingw.h" 2 3






# 1 "c:\\mingw\\include\\w32api.h" 1 3
# 35 "c:\\mingw\\include\\w32api.h" 3
       
# 36 "c:\\mingw\\include\\w32api.h" 3
# 59 "c:\\mingw\\include\\w32api.h" 3
# 1 "c:\\mingw\\include\\sdkddkver.h" 1 3
# 35 "c:\\mingw\\include\\sdkddkver.h" 3
       
# 36 "c:\\mingw\\include\\sdkddkver.h" 3
# 60 "c:\\mingw\\include\\w32api.h" 2 3
# 74 "c:\\mingw\\include\\_mingw.h" 2 3
# 52 "c:\\mingw\\include\\io.h" 2 3




# 1 "c:\\mingw\\include\\sys\\types.h" 1 3
# 34 "c:\\mingw\\include\\sys\\types.h" 3
       
# 35 "c:\\mingw\\include\\sys\\types.h" 3
# 62 "c:\\mingw\\include\\sys\\types.h" 3
  
# 62 "c:\\mingw\\include\\sys\\types.h" 3
 typedef long __off32_t;




  typedef __off32_t _off_t;







  typedef _off_t off_t;
# 91 "c:\\mingw\\include\\sys\\types.h" 3
  typedef long long __off64_t;






  typedef __off64_t off64_t;
# 115 "c:\\mingw\\include\\sys\\types.h" 3
  typedef int _ssize_t;







  typedef _ssize_t ssize_t;
# 139 "c:\\mingw\\include\\sys\\types.h" 3
  typedef long __time32_t;
  typedef long long __time64_t;
# 149 "c:\\mingw\\include\\sys\\types.h" 3
   typedef __time32_t time_t;
# 174 "c:\\mingw\\include\\sys\\types.h" 3
# 1 "c:\\mingw\\lib\\gcc\\mingw32\\5.3.0\\include\\stddef.h" 1 3 4
# 149 "c:\\mingw\\lib\\gcc\\mingw32\\5.3.0\\include\\stddef.h" 3 4
typedef int ptrdiff_t;
# 216 "c:\\mingw\\lib\\gcc\\mingw32\\5.3.0\\include\\stddef.h" 3 4
typedef unsigned int size_t;
# 328 "c:\\mingw\\lib\\gcc\\mingw32\\5.3.0\\include\\stddef.h" 3 4
typedef short unsigned int wchar_t;
# 175 "c:\\mingw\\include\\sys\\types.h" 2 3
# 184 "c:\\mingw\\include\\sys\\types.h" 3
typedef unsigned int _dev_t;
# 195 "c:\\mingw\\include\\sys\\types.h" 3
typedef short _ino_t;
typedef unsigned short _mode_t;
typedef int _pid_t;
typedef int _sigset_t;
# 207 "c:\\mingw\\include\\sys\\types.h" 3
typedef _dev_t dev_t;
typedef _ino_t ino_t;
typedef _mode_t mode_t;
typedef _pid_t pid_t;
typedef _sigset_t sigset_t;


typedef long long fpos64_t;






typedef unsigned long useconds_t __attribute__((__deprecated__));
# 57 "c:\\mingw\\include\\io.h" 2 3
# 67 "c:\\mingw\\include\\io.h" 3
# 1 "c:\\mingw\\include\\stdint.h" 1 3
# 34 "c:\\mingw\\include\\stdint.h" 3
       
# 35 "c:\\mingw\\include\\stdint.h" 3
# 106 "c:\\mingw\\include\\stdint.h" 3
 typedef int __intptr_t;

typedef __intptr_t intptr_t;
# 68 "c:\\mingw\\include\\io.h" 2 3
# 104 "c:\\mingw\\include\\io.h" 3
typedef unsigned long _fsize_t;
# 174 "c:\\mingw\\include\\io.h" 3

# 184 "c:\\mingw\\include\\io.h" 3
struct _finddata_t { unsigned attrib; time_t time_create; time_t time_access; time_t time_write; _fsize_t size; char name[(260)]; };
struct _finddatai64_t { unsigned attrib; time_t time_create; time_t time_access; time_t time_write; long long size; char name[(260)]; };
# 200 "c:\\mingw\\include\\io.h" 3
 __attribute__((__cdecl__)) __attribute__((__nothrow__))
intptr_t _findfirst (const char *, struct _finddata_t *);

 __attribute__((__cdecl__)) __attribute__((__nothrow__))
int _findnext (intptr_t, struct _finddata_t *);

 __attribute__((__cdecl__)) __attribute__((__nothrow__))
intptr_t _findfirsti64 (const char *, struct _finddatai64_t *);

 __attribute__((__cdecl__)) __attribute__((__nothrow__))
int _findnexti64 (intptr_t, struct _finddatai64_t *);
# 223 "c:\\mingw\\include\\io.h" 3
struct __finddata64_t { unsigned attrib; __time64_t time_create; __time64_t time_access; __time64_t time_write; long long size; char name[(260)]; };







 __attribute__((__cdecl__)) __attribute__((__nothrow__))
intptr_t _findfirst64 (const char *, struct __finddata64_t *);

 __attribute__((__cdecl__)) __attribute__((__nothrow__))
int _findnext64 (intptr_t, struct __finddata64_t *);
# 341 "c:\\mingw\\include\\io.h" 3
struct _wfinddata_t { unsigned attrib; time_t time_create; time_t time_access; time_t time_write; _fsize_t size; wchar_t name[(260)]; };
struct _wfinddatai64_t { unsigned attrib; time_t time_create; time_t time_access; time_t time_write; long long size; wchar_t name[(260)]; };







 __attribute__((__cdecl__)) __attribute__((__nothrow__))
intptr_t _wfindfirst (const wchar_t *, struct _wfinddata_t *);

 __attribute__((__cdecl__)) __attribute__((__nothrow__))
int _wfindnext (intptr_t, struct _wfinddata_t *);

 __attribute__((__cdecl__)) __attribute__((__nothrow__))
intptr_t _wfindfirsti64 (const wchar_t *, struct _wfinddatai64_t *);

 __attribute__((__cdecl__)) __attribute__((__nothrow__))
int _wfindnexti64 (intptr_t, struct _wfinddatai64_t *);







struct __wfinddata64_t { unsigned attrib; __time64_t time_create; __time64_t time_access; __time64_t time_write; long long size; wchar_t name[(260)]; };
# 377 "c:\\mingw\\include\\io.h" 3
 __attribute__((__cdecl__)) __attribute__((__nothrow__))
intptr_t _wfindfirst64 (const wchar_t *, struct __wfinddata64_t *);

 __attribute__((__cdecl__)) __attribute__((__nothrow__))
int _wfindnext64 (intptr_t, struct __wfinddata64_t *);
# 484 "c:\\mingw\\include\\io.h" 3
 __attribute__((__cdecl__)) __attribute__((__nothrow__)) int _findclose (intptr_t);





 __attribute__((__cdecl__)) __attribute__((__nothrow__)) int _chdir (const char *);
 __attribute__((__cdecl__)) __attribute__((__nothrow__)) char *_getcwd (char *, int);
 __attribute__((__cdecl__)) __attribute__((__nothrow__)) int _mkdir (const char *);
 __attribute__((__cdecl__)) __attribute__((__nothrow__)) char *_mktemp (char *);
 __attribute__((__cdecl__)) __attribute__((__nothrow__)) int _rmdir (const char *);
 __attribute__((__cdecl__)) __attribute__((__nothrow__)) int _chmod (const char *, int);


 __attribute__((__cdecl__)) __attribute__((__nothrow__)) long long _filelengthi64 (int);
 __attribute__((__cdecl__)) __attribute__((__nothrow__)) long long _lseeki64 (int, long long, int);
 __attribute__((__cdecl__)) __attribute__((__nothrow__)) long long _telli64 (int);


extern inline __attribute__((__gnu_inline__)) __off64_t lseek64 (int, __off64_t, int);
extern inline __attribute__((__gnu_inline__))
__off64_t lseek64 (int fd, __off64_t offset, int whence)
{ return _lseeki64 (fd, (long long)(offset), whence); }







 __attribute__((__cdecl__)) __attribute__((__nothrow__)) int chdir (const char *);
 __attribute__((__cdecl__)) __attribute__((__nothrow__)) char *getcwd (char *, int);
 __attribute__((__cdecl__)) __attribute__((__nothrow__)) int mkdir (const char *);
 __attribute__((__cdecl__)) __attribute__((__nothrow__)) char *mktemp (char *);
 __attribute__((__cdecl__)) __attribute__((__nothrow__)) int rmdir (const char *);
 __attribute__((__cdecl__)) __attribute__((__nothrow__)) int chmod (const char *, int);






# 551 "c:\\mingw\\include\\io.h" 3





 __attribute__((__cdecl__)) __attribute__((__nothrow__)) int _access (const char *, int);
 __attribute__((__cdecl__)) __attribute__((__nothrow__)) int _chsize (int, long);
 __attribute__((__cdecl__)) __attribute__((__nothrow__)) int _close (int);
 __attribute__((__cdecl__)) __attribute__((__nothrow__)) int _commit (int);




 __attribute__((__cdecl__)) __attribute__((__nothrow__)) int _creat (const char *, int);

 __attribute__((__cdecl__)) __attribute__((__nothrow__)) int _dup (int);
 __attribute__((__cdecl__)) __attribute__((__nothrow__)) int _dup2 (int, int);
 __attribute__((__cdecl__)) __attribute__((__nothrow__)) long _filelength (int);
 __attribute__((__cdecl__)) __attribute__((__nothrow__)) long _get_osfhandle (int);
 __attribute__((__cdecl__)) __attribute__((__nothrow__)) int _isatty (int);
# 579 "c:\\mingw\\include\\io.h" 3
 __attribute__((__cdecl__)) __attribute__((__nothrow__)) int _eof (int);





 __attribute__((__cdecl__)) __attribute__((__nothrow__)) int _locking (int, int, long);

 __attribute__((__cdecl__)) __attribute__((__nothrow__)) long _lseek (int, long, int);







 __attribute__((__cdecl__)) __attribute__((__nothrow__)) int _open (const char *, int, ...);

 __attribute__((__cdecl__)) __attribute__((__nothrow__)) int _open_osfhandle (intptr_t, int);
 __attribute__((__cdecl__)) __attribute__((__nothrow__)) int _pipe (int *, unsigned int, int);
 __attribute__((__cdecl__)) __attribute__((__nothrow__)) int _read (int, void *, unsigned int);
 __attribute__((__cdecl__)) __attribute__((__nothrow__)) int _setmode (int, int);






 __attribute__((__cdecl__)) __attribute__((__nothrow__)) int remove (const char *);
 __attribute__((__cdecl__)) __attribute__((__nothrow__)) int rename (const char *, const char *);






 __attribute__((__cdecl__)) __attribute__((__nothrow__)) int _sopen (const char *, int, int, ...);

 __attribute__((__cdecl__)) __attribute__((__nothrow__)) long _tell (int);




 __attribute__((__cdecl__)) __attribute__((__nothrow__)) int _umask (int);
 __attribute__((__cdecl__)) __attribute__((__nothrow__)) int _unlink (const char *);
 __attribute__((__cdecl__)) __attribute__((__nothrow__)) int _write (int, const void *, unsigned int);
# 636 "c:\\mingw\\include\\io.h" 3
 __attribute__((__cdecl__)) __attribute__((__nothrow__)) int _waccess (const wchar_t *, int);
 __attribute__((__cdecl__)) __attribute__((__nothrow__)) int _wchmod (const wchar_t *, int);
 __attribute__((__cdecl__)) __attribute__((__nothrow__)) int _wcreat (const wchar_t *, int);
 __attribute__((__cdecl__)) __attribute__((__nothrow__)) int _wunlink (const wchar_t *);
 __attribute__((__cdecl__)) __attribute__((__nothrow__)) int _wopen (const wchar_t *, int, ...);
 __attribute__((__cdecl__)) __attribute__((__nothrow__)) int _wsopen (const wchar_t *, int, int, ...);
 __attribute__((__cdecl__)) __attribute__((__nothrow__)) wchar_t *_wmktemp (wchar_t *);
# 651 "c:\\mingw\\include\\io.h" 3
 __attribute__((__cdecl__)) __attribute__((__nothrow__)) int access (const char*, int);
 __attribute__((__cdecl__)) __attribute__((__nothrow__)) int chsize (int, long );
 __attribute__((__cdecl__)) __attribute__((__nothrow__)) int close (int);
 __attribute__((__cdecl__)) __attribute__((__nothrow__)) int creat (const char*, int);
 __attribute__((__cdecl__)) __attribute__((__nothrow__)) int dup (int);
 __attribute__((__cdecl__)) __attribute__((__nothrow__)) int dup2 (int, int);
 __attribute__((__cdecl__)) __attribute__((__nothrow__)) int eof (int);
 __attribute__((__cdecl__)) __attribute__((__nothrow__)) long filelength (int);
 __attribute__((__cdecl__)) __attribute__((__nothrow__)) int isatty (int);
 __attribute__((__cdecl__)) __attribute__((__nothrow__)) long lseek (int, long, int);
 __attribute__((__cdecl__)) __attribute__((__nothrow__)) int open (const char*, int, ...);
 __attribute__((__cdecl__)) __attribute__((__nothrow__)) int read (int, void*, unsigned int);
 __attribute__((__cdecl__)) __attribute__((__nothrow__)) int setmode (int, int);
 __attribute__((__cdecl__)) __attribute__((__nothrow__)) int sopen (const char*, int, int, ...);
 __attribute__((__cdecl__)) __attribute__((__nothrow__)) long tell (int);
 __attribute__((__cdecl__)) __attribute__((__nothrow__)) int umask (int);
 __attribute__((__cdecl__)) __attribute__((__nothrow__)) int unlink (const char*);
 __attribute__((__cdecl__)) __attribute__((__nothrow__)) int write (int, const void*, unsigned int);
# 701 "c:\\mingw\\include\\io.h" 3

# 3 "analyseurLexical.c" 2
# 1 "c:\\mingw\\include\\stdlib.h" 1 3
# 34 "c:\\mingw\\include\\stdlib.h" 3
       
# 35 "c:\\mingw\\include\\stdlib.h" 3
# 55 "c:\\mingw\\include\\stdlib.h" 3
# 1 "c:\\mingw\\lib\\gcc\\mingw32\\5.3.0\\include\\stddef.h" 1 3 4
# 56 "c:\\mingw\\include\\stdlib.h" 2 3
# 90 "c:\\mingw\\include\\stdlib.h" 3

# 99 "c:\\mingw\\include\\stdlib.h" 3
extern int _argc;
extern char **_argv;




extern __attribute__((__cdecl__)) __attribute__((__nothrow__)) int *__p___argc(void);
extern __attribute__((__cdecl__)) __attribute__((__nothrow__)) char ***__p___argv(void);
extern __attribute__((__cdecl__)) __attribute__((__nothrow__)) wchar_t ***__p___wargv(void);
# 142 "c:\\mingw\\include\\stdlib.h" 3
   extern __attribute__((__dllimport__)) int __mb_cur_max;
# 166 "c:\\mingw\\include\\stdlib.h" 3
 __attribute__((__cdecl__)) __attribute__((__nothrow__)) int *_errno(void);


 __attribute__((__cdecl__)) __attribute__((__nothrow__)) int *__doserrno(void);







extern __attribute__((__cdecl__)) __attribute__((__nothrow__)) char ***__p__environ(void);

extern __attribute__((__cdecl__)) __attribute__((__nothrow__)) wchar_t ***__p__wenviron(void);
# 202 "c:\\mingw\\include\\stdlib.h" 3
extern __attribute__((__dllimport__)) int _sys_nerr;
# 227 "c:\\mingw\\include\\stdlib.h" 3
extern __attribute__((__dllimport__)) char *_sys_errlist[];
# 238 "c:\\mingw\\include\\stdlib.h" 3
extern __attribute__((__cdecl__)) __attribute__((__nothrow__)) unsigned int *__p__osver(void);
extern __attribute__((__cdecl__)) __attribute__((__nothrow__)) unsigned int *__p__winver(void);
extern __attribute__((__cdecl__)) __attribute__((__nothrow__)) unsigned int *__p__winmajor(void);
extern __attribute__((__cdecl__)) __attribute__((__nothrow__)) unsigned int *__p__winminor(void);
# 250 "c:\\mingw\\include\\stdlib.h" 3
extern __attribute__((__dllimport__)) unsigned int _osver;
extern __attribute__((__dllimport__)) unsigned int _winver;
extern __attribute__((__dllimport__)) unsigned int _winmajor;
extern __attribute__((__dllimport__)) unsigned int _winminor;
# 289 "c:\\mingw\\include\\stdlib.h" 3
 __attribute__((__cdecl__)) __attribute__((__nothrow__)) char **__p__pgmptr(void);


 __attribute__((__cdecl__)) __attribute__((__nothrow__)) wchar_t **__p__wpgmptr(void);
# 325 "c:\\mingw\\include\\stdlib.h" 3
extern __attribute__((__dllimport__)) int _fmode;
# 335 "c:\\mingw\\include\\stdlib.h" 3
 __attribute__((__cdecl__)) __attribute__((__nothrow__)) int atoi (const char *);
 __attribute__((__cdecl__)) __attribute__((__nothrow__)) long atol (const char *);

 __attribute__((__cdecl__)) __attribute__((__nothrow__)) double strtod (const char *, char **);
 __attribute__((__cdecl__)) __attribute__((__nothrow__)) double atof (const char *);


 __attribute__((__cdecl__)) __attribute__((__nothrow__)) double _wtof (const wchar_t *);
 __attribute__((__cdecl__)) __attribute__((__nothrow__)) int _wtoi (const wchar_t *);
 __attribute__((__cdecl__)) __attribute__((__nothrow__)) long _wtol (const wchar_t *);
# 378 "c:\\mingw\\include\\stdlib.h" 3
__attribute__((__cdecl__)) __attribute__((__nothrow__))
float strtof (const char *__restrict__, char **__restrict__);

__attribute__((__cdecl__)) __attribute__((__nothrow__))
long double strtold (const char *__restrict__, char **__restrict__);


 __attribute__((__cdecl__)) __attribute__((__nothrow__)) long strtol (const char *, char **, int);
 __attribute__((__cdecl__)) __attribute__((__nothrow__)) unsigned long strtoul (const char *, char **, int);







 __attribute__((__cdecl__)) __attribute__((__nothrow__))
long wcstol (const wchar_t *, wchar_t **, int);

 __attribute__((__cdecl__)) __attribute__((__nothrow__))
unsigned long wcstoul (const wchar_t *, wchar_t **, int);

 __attribute__((__cdecl__)) __attribute__((__nothrow__)) double wcstod (const wchar_t *, wchar_t **);





__attribute__((__cdecl__)) __attribute__((__nothrow__))
float wcstof (const wchar_t *__restrict__, wchar_t **__restrict__);

__attribute__((__cdecl__)) __attribute__((__nothrow__))
long double wcstold (const wchar_t *__restrict__, wchar_t **__restrict__);
# 451 "c:\\mingw\\include\\stdlib.h" 3
 __attribute__((__cdecl__)) __attribute__((__nothrow__)) wchar_t *_wgetenv (const wchar_t *);
 __attribute__((__cdecl__)) __attribute__((__nothrow__)) int _wputenv (const wchar_t *);

 __attribute__((__cdecl__)) __attribute__((__nothrow__))
void _wsearchenv (const wchar_t *, const wchar_t *, wchar_t *);

 __attribute__((__cdecl__)) __attribute__((__nothrow__)) int _wsystem (const wchar_t *);

 __attribute__((__cdecl__)) __attribute__((__nothrow__))
void _wmakepath (wchar_t *, const wchar_t *, const wchar_t *, const wchar_t *,
    const wchar_t *
  );

 __attribute__((__cdecl__)) __attribute__((__nothrow__))
void _wsplitpath (const wchar_t *, wchar_t *, wchar_t *, wchar_t *, wchar_t *);

 __attribute__((__cdecl__)) __attribute__((__nothrow__))
wchar_t *_wfullpath (wchar_t *, const wchar_t *, size_t);





 __attribute__((__cdecl__)) __attribute__((__nothrow__)) size_t wcstombs (char *, const wchar_t *, size_t);
 __attribute__((__cdecl__)) __attribute__((__nothrow__)) int wctomb (char *, wchar_t);

 __attribute__((__cdecl__)) __attribute__((__nothrow__)) int mblen (const char *, size_t);
 __attribute__((__cdecl__)) __attribute__((__nothrow__)) size_t mbstowcs (wchar_t *, const char *, size_t);
 __attribute__((__cdecl__)) __attribute__((__nothrow__)) int mbtowc (wchar_t *, const char *, size_t);

 __attribute__((__cdecl__)) __attribute__((__nothrow__)) int rand (void);
 __attribute__((__cdecl__)) __attribute__((__nothrow__)) void srand (unsigned int);

 __attribute__((__cdecl__)) __attribute__((__nothrow__)) void *calloc (size_t, size_t) __attribute__((__malloc__));
 __attribute__((__cdecl__)) __attribute__((__nothrow__)) void *malloc (size_t) __attribute__((__malloc__));
 __attribute__((__cdecl__)) __attribute__((__nothrow__)) void *realloc (void *, size_t);
 __attribute__((__cdecl__)) __attribute__((__nothrow__)) void free (void *);
 __attribute__((__cdecl__)) __attribute__((__nothrow__)) void abort (void) __attribute__((__noreturn__));
 __attribute__((__cdecl__)) __attribute__((__nothrow__)) void exit (int) __attribute__((__noreturn__));



int __attribute__((__cdecl__)) __attribute__((__nothrow__)) atexit (void (*)(void));

 __attribute__((__cdecl__)) __attribute__((__nothrow__)) int system (const char *);
 __attribute__((__cdecl__)) __attribute__((__nothrow__)) char *getenv (const char *);






 __attribute__((__cdecl__)) void *bsearch
(const void *, const void *, size_t, size_t, int (*)(const void *, const void *));

 __attribute__((__cdecl__)) void qsort
(void *, size_t, size_t, int (*)(const void *, const void *));

 __attribute__((__cdecl__)) __attribute__((__nothrow__)) int abs (int) __attribute__((__const__));
 __attribute__((__cdecl__)) __attribute__((__nothrow__)) long labs (long) __attribute__((__const__));
# 519 "c:\\mingw\\include\\stdlib.h" 3
typedef struct { int quot, rem; } div_t;
typedef struct { long quot, rem; } ldiv_t;

 __attribute__((__cdecl__)) __attribute__((__nothrow__)) div_t div (int, int) __attribute__((__const__));
 __attribute__((__cdecl__)) __attribute__((__nothrow__)) ldiv_t ldiv (long, long) __attribute__((__const__));






 __attribute__((__cdecl__)) __attribute__((__nothrow__)) void _exit (int) __attribute__((__noreturn__));





 __attribute__((__cdecl__)) __attribute__((__nothrow__)) long long _atoi64 (const char *);
# 545 "c:\\mingw\\include\\stdlib.h" 3
 __attribute__((__cdecl__)) __attribute__((__nothrow__)) void _beep (unsigned int, unsigned int) __attribute__((__deprecated__));

 __attribute__((__cdecl__)) __attribute__((__nothrow__)) void _seterrormode (int) __attribute__((__deprecated__));
 __attribute__((__cdecl__)) __attribute__((__nothrow__)) void _sleep (unsigned long) __attribute__((__deprecated__));



typedef int (* _onexit_t)(void);
__attribute__((__cdecl__)) __attribute__((__nothrow__)) _onexit_t _onexit( _onexit_t );

 __attribute__((__cdecl__)) __attribute__((__nothrow__)) int _putenv (const char *);
 __attribute__((__cdecl__)) __attribute__((__nothrow__))
void _searchenv (const char *, const char *, char *);

 __attribute__((__cdecl__)) __attribute__((__nothrow__)) char *_ecvt (double, int, int *, int *);
 __attribute__((__cdecl__)) __attribute__((__nothrow__)) char *_fcvt (double, int, int *, int *);
 __attribute__((__cdecl__)) __attribute__((__nothrow__)) char *_gcvt (double, int, char *);

 __attribute__((__cdecl__)) __attribute__((__nothrow__))
void _makepath (char *, const char *, const char *, const char *, const char *);

 __attribute__((__cdecl__)) __attribute__((__nothrow__))
void _splitpath (const char *, char *, char *, char *, char *);

 __attribute__((__cdecl__)) __attribute__((__nothrow__)) char *_fullpath (char*, const char*, size_t);

 __attribute__((__cdecl__)) __attribute__((__nothrow__)) char *_itoa (int, char *, int);
 __attribute__((__cdecl__)) __attribute__((__nothrow__)) char *_ltoa (long, char *, int);
 __attribute__((__cdecl__)) __attribute__((__nothrow__)) char *_ultoa(unsigned long, char *, int);
 __attribute__((__cdecl__)) __attribute__((__nothrow__)) wchar_t *_itow (int, wchar_t *, int);
 __attribute__((__cdecl__)) __attribute__((__nothrow__)) wchar_t *_ltow (long, wchar_t *, int);
 __attribute__((__cdecl__)) __attribute__((__nothrow__)) wchar_t *_ultow (unsigned long, wchar_t *, int);


 __attribute__((__cdecl__)) __attribute__((__nothrow__)) char* _i64toa (long long, char *, int);
 __attribute__((__cdecl__)) __attribute__((__nothrow__)) char* _ui64toa (unsigned long long, char *, int);
 __attribute__((__cdecl__)) __attribute__((__nothrow__)) long long _wtoi64 (const wchar_t *);
 __attribute__((__cdecl__)) __attribute__((__nothrow__)) wchar_t* _i64tow (long long, wchar_t *, int);
 __attribute__((__cdecl__)) __attribute__((__nothrow__)) wchar_t* _ui64tow (unsigned long long, wchar_t *, int);

 __attribute__((__cdecl__)) __attribute__((__nothrow__)) unsigned int (_rotl)(unsigned int, int) __attribute__((__const__));
 __attribute__((__cdecl__)) __attribute__((__nothrow__)) unsigned int (_rotr)(unsigned int, int) __attribute__((__const__));
 __attribute__((__cdecl__)) __attribute__((__nothrow__)) unsigned long (_lrotl)(unsigned long, int) __attribute__((__const__));
 __attribute__((__cdecl__)) __attribute__((__nothrow__)) unsigned long (_lrotr)(unsigned long, int) __attribute__((__const__));

 __attribute__((__cdecl__)) __attribute__((__nothrow__)) int _set_error_mode (int);
# 628 "c:\\mingw\\include\\stdlib.h" 3
 __attribute__((__cdecl__)) __attribute__((__nothrow__)) int putenv (const char*);
 __attribute__((__cdecl__)) __attribute__((__nothrow__)) void searchenv (const char*, const char*, char*);

 __attribute__((__cdecl__)) __attribute__((__nothrow__)) char* itoa (int, char*, int);
 __attribute__((__cdecl__)) __attribute__((__nothrow__)) char* ltoa (long, char*, int);


 __attribute__((__cdecl__)) __attribute__((__nothrow__)) char* ecvt (double, int, int*, int*);
 __attribute__((__cdecl__)) __attribute__((__nothrow__)) char* fcvt (double, int, int*, int*);
 __attribute__((__cdecl__)) __attribute__((__nothrow__)) char* gcvt (double, int, char*);
# 649 "c:\\mingw\\include\\stdlib.h" 3
__attribute__((__cdecl__)) __attribute__((__nothrow__)) void _Exit(int) __attribute__((__noreturn__));






typedef struct { long long quot, rem; } lldiv_t;
__attribute__((__cdecl__)) __attribute__((__nothrow__)) lldiv_t lldiv (long long, long long) __attribute__((__const__));

__attribute__((__cdecl__)) __attribute__((__nothrow__)) long long llabs (long long);
# 670 "c:\\mingw\\include\\stdlib.h" 3
__attribute__((__cdecl__)) __attribute__((__nothrow__))
long long strtoll (const char *__restrict__, char **__restrict, int);

__attribute__((__cdecl__)) __attribute__((__nothrow__))
unsigned long long strtoull (const char *__restrict__, char **__restrict__, int);





__attribute__((__cdecl__)) __attribute__((__nothrow__)) long long atoll (const char *);
# 726 "c:\\mingw\\include\\stdlib.h" 3
__attribute__((__cdecl__)) __attribute__((__nothrow__)) __attribute__((__deprecated__)) long long wtoll (const wchar_t *);
__attribute__((__cdecl__)) __attribute__((__nothrow__)) __attribute__((__deprecated__)) char *lltoa (long long, char *, int);
__attribute__((__cdecl__)) __attribute__((__nothrow__)) __attribute__((__deprecated__)) char *ulltoa (unsigned long long , char *, int);
__attribute__((__cdecl__)) __attribute__((__nothrow__)) __attribute__((__deprecated__)) wchar_t *lltow (long long, wchar_t *, int);
__attribute__((__cdecl__)) __attribute__((__nothrow__)) __attribute__((__deprecated__)) wchar_t *ulltow (unsigned long long, wchar_t *, int);
# 766 "c:\\mingw\\include\\stdlib.h" 3
__attribute__((__cdecl__)) __attribute__((__nothrow__)) int mkstemp (char *);
__attribute__((__cdecl__)) __attribute__((__nothrow__)) int __mingw_mkstemp (int, char *);
# 808 "c:\\mingw\\include\\stdlib.h" 3
extern inline __attribute__((__gnu_inline__)) __attribute__((__always_inline__))
__attribute__((__cdecl__)) __attribute__((__nothrow__)) int mkstemp (char *__filename_template)
{ return __mingw_mkstemp( 0, __filename_template ); }
# 819 "c:\\mingw\\include\\stdlib.h" 3
__attribute__((__cdecl__)) __attribute__((__nothrow__)) char *mkdtemp (char *);
__attribute__((__cdecl__)) __attribute__((__nothrow__)) char *__mingw_mkdtemp (char *);

extern inline __attribute__((__gnu_inline__)) __attribute__((__always_inline__))
__attribute__((__cdecl__)) __attribute__((__nothrow__)) char *mkdtemp (char *__dirname_template)
{ return __mingw_mkdtemp( __dirname_template ); }






__attribute__((__cdecl__)) __attribute__((__nothrow__)) int setenv( const char *, const char *, int );
__attribute__((__cdecl__)) __attribute__((__nothrow__)) int unsetenv( const char * );

__attribute__((__cdecl__)) __attribute__((__nothrow__)) int __mingw_setenv( const char *, const char *, int );

extern inline __attribute__((__gnu_inline__)) __attribute__((__always_inline__))
__attribute__((__cdecl__)) __attribute__((__nothrow__)) int setenv( const char *__n, const char *__v, int __f )
{ return __mingw_setenv( __n, __v, __f ); }

extern inline __attribute__((__gnu_inline__)) __attribute__((__always_inline__))
__attribute__((__cdecl__)) __attribute__((__nothrow__)) int unsetenv( const char *__name )
{ return __mingw_setenv( __name, ((void *)0), 1 ); }





# 4 "analyseurLexical.c" 2
# 1 "c:\\mingw\\include\\string.h" 1 3
# 34 "c:\\mingw\\include\\string.h" 3
       
# 35 "c:\\mingw\\include\\string.h" 3
# 53 "c:\\mingw\\include\\string.h" 3
# 1 "c:\\mingw\\lib\\gcc\\mingw32\\5.3.0\\include\\stddef.h" 1 3 4
# 54 "c:\\mingw\\include\\string.h" 2 3
# 62 "c:\\mingw\\include\\string.h" 3
# 1 "c:\\mingw\\include\\strings.h" 1 3
# 33 "c:\\mingw\\include\\strings.h" 3
       
# 34 "c:\\mingw\\include\\strings.h" 3
# 59 "c:\\mingw\\include\\strings.h" 3
# 1 "c:\\mingw\\lib\\gcc\\mingw32\\5.3.0\\include\\stddef.h" 1 3 4
# 60 "c:\\mingw\\include\\strings.h" 2 3



int __attribute__((__cdecl__)) __attribute__((__nothrow__)) strcasecmp( const char *, const char * );
int __attribute__((__cdecl__)) __attribute__((__nothrow__)) strncasecmp( const char *, const char *, size_t );
# 80 "c:\\mingw\\include\\strings.h" 3
 __attribute__((__cdecl__)) __attribute__((__nothrow__)) int _stricmp( const char *, const char * );
 __attribute__((__cdecl__)) __attribute__((__nothrow__)) int _strnicmp( const char *, const char *, size_t );
# 100 "c:\\mingw\\include\\strings.h" 3

# 63 "c:\\mingw\\include\\string.h" 2 3







 __attribute__((__cdecl__)) __attribute__((__nothrow__)) void *memchr (const void *, int, size_t) __attribute__((__pure__));
 __attribute__((__cdecl__)) __attribute__((__nothrow__)) int memcmp (const void *, const void *, size_t) __attribute__((__pure__));
 __attribute__((__cdecl__)) __attribute__((__nothrow__)) void *memcpy (void *, const void *, size_t);
 __attribute__((__cdecl__)) __attribute__((__nothrow__)) void *memmove (void *, const void *, size_t);
 __attribute__((__cdecl__)) __attribute__((__nothrow__)) void *memset (void *, int, size_t);
 __attribute__((__cdecl__)) __attribute__((__nothrow__)) char *strcat (char *, const char *);
 __attribute__((__cdecl__)) __attribute__((__nothrow__)) char *strchr (const char *, int) __attribute__((__pure__));
 __attribute__((__cdecl__)) __attribute__((__nothrow__)) int strcmp (const char *, const char *) __attribute__((__pure__));
 __attribute__((__cdecl__)) __attribute__((__nothrow__)) int strcoll (const char *, const char *);
 __attribute__((__cdecl__)) __attribute__((__nothrow__)) char *strcpy (char *, const char *);
 __attribute__((__cdecl__)) __attribute__((__nothrow__)) size_t strcspn (const char *, const char *) __attribute__((__pure__));
 __attribute__((__cdecl__)) __attribute__((__nothrow__)) char *strerror (int);

 __attribute__((__cdecl__)) __attribute__((__nothrow__)) size_t strlen (const char *) __attribute__((__pure__));
 __attribute__((__cdecl__)) __attribute__((__nothrow__)) char *strncat (char *, const char *, size_t);
 __attribute__((__cdecl__)) __attribute__((__nothrow__)) int strncmp (const char *, const char *, size_t) __attribute__((__pure__));
 __attribute__((__cdecl__)) __attribute__((__nothrow__)) char *strncpy (char *, const char *, size_t);
 __attribute__((__cdecl__)) __attribute__((__nothrow__)) char *strpbrk (const char *, const char *) __attribute__((__pure__));
 __attribute__((__cdecl__)) __attribute__((__nothrow__)) char *strrchr (const char *, int) __attribute__((__pure__));
 __attribute__((__cdecl__)) __attribute__((__nothrow__)) size_t strspn (const char *, const char *) __attribute__((__pure__));
 __attribute__((__cdecl__)) __attribute__((__nothrow__)) char *strstr (const char *, const char *) __attribute__((__pure__));
 __attribute__((__cdecl__)) __attribute__((__nothrow__)) char *strtok (char *, const char *);
 __attribute__((__cdecl__)) __attribute__((__nothrow__)) size_t strxfrm (char *, const char *, size_t);




 __attribute__((__cdecl__)) __attribute__((__nothrow__)) char *_strerror (const char *);
 __attribute__((__cdecl__)) __attribute__((__nothrow__)) void *_memccpy (void *, const void *, int, size_t);
 __attribute__((__cdecl__)) __attribute__((__nothrow__)) int _memicmp (const void *, const void *, size_t);
 __attribute__((__cdecl__)) __attribute__((__nothrow__)) char *_strdup (const char *) __attribute__((__malloc__));
 __attribute__((__cdecl__)) __attribute__((__nothrow__)) int _strcmpi (const char *, const char *);
 __attribute__((__cdecl__)) __attribute__((__nothrow__)) int _stricoll (const char *, const char *);
 __attribute__((__cdecl__)) __attribute__((__nothrow__)) char *_strlwr (char *);
 __attribute__((__cdecl__)) __attribute__((__nothrow__)) char *_strnset (char *, int, size_t);
 __attribute__((__cdecl__)) __attribute__((__nothrow__)) char *_strrev (char *);
 __attribute__((__cdecl__)) __attribute__((__nothrow__)) char *_strset (char *, int);
 __attribute__((__cdecl__)) __attribute__((__nothrow__)) char *_strupr (char *);
 __attribute__((__cdecl__)) __attribute__((__nothrow__)) void _swab (const char *, char *, size_t);
# 126 "c:\\mingw\\include\\string.h" 3
 __attribute__((__cdecl__)) __attribute__((__nothrow__)) int _strncoll(const char *, const char *, size_t);
 __attribute__((__cdecl__)) __attribute__((__nothrow__)) int _strnicoll(const char *, const char *, size_t);






 __attribute__((__cdecl__)) __attribute__((__nothrow__)) void *memccpy (void *, const void *, int, size_t);
 __attribute__((__cdecl__)) __attribute__((__nothrow__)) int memicmp (const void *, const void *, size_t);
 __attribute__((__cdecl__)) __attribute__((__nothrow__)) char *strdup (const char *) __attribute__((__malloc__));
 __attribute__((__cdecl__)) __attribute__((__nothrow__)) int strcmpi (const char *, const char *);
 __attribute__((__cdecl__)) __attribute__((__nothrow__)) int stricmp (const char *, const char *);
 __attribute__((__cdecl__)) __attribute__((__nothrow__)) int stricoll (const char *, const char *);
 __attribute__((__cdecl__)) __attribute__((__nothrow__)) char *strlwr (char *);
 __attribute__((__cdecl__)) __attribute__((__nothrow__)) int strnicmp (const char *, const char *, size_t);
 __attribute__((__cdecl__)) __attribute__((__nothrow__)) char *strnset (char *, int, size_t);
 __attribute__((__cdecl__)) __attribute__((__nothrow__)) char *strrev (char *);
 __attribute__((__cdecl__)) __attribute__((__nothrow__)) char *strset (char *, int);
 __attribute__((__cdecl__)) __attribute__((__nothrow__)) char *strupr (char *);





 __attribute__((__cdecl__)) __attribute__((__nothrow__)) void swab (const char *, char *, size_t);
# 170 "c:\\mingw\\include\\string.h" 3
# 1 "c:\\mingw\\include\\wchar.h" 1 3
# 35 "c:\\mingw\\include\\wchar.h" 3
       
# 36 "c:\\mingw\\include\\wchar.h" 3
# 392 "c:\\mingw\\include\\wchar.h" 3
 __attribute__((__cdecl__)) __attribute__((__nothrow__)) wchar_t *wcscat (wchar_t *, const wchar_t *);
 __attribute__((__cdecl__)) __attribute__((__nothrow__)) wchar_t *wcschr (const wchar_t *, wchar_t);
 __attribute__((__cdecl__)) __attribute__((__nothrow__)) int wcscmp (const wchar_t *, const wchar_t *);
 __attribute__((__cdecl__)) __attribute__((__nothrow__)) int wcscoll (const wchar_t *, const wchar_t *);
 __attribute__((__cdecl__)) __attribute__((__nothrow__)) wchar_t *wcscpy (wchar_t *, const wchar_t *);
 __attribute__((__cdecl__)) __attribute__((__nothrow__)) size_t wcscspn (const wchar_t *, const wchar_t *);
 __attribute__((__cdecl__)) __attribute__((__nothrow__)) size_t wcslen (const wchar_t *);
 __attribute__((__cdecl__)) __attribute__((__nothrow__)) wchar_t *wcsncat (wchar_t *, const wchar_t *, size_t);
 __attribute__((__cdecl__)) __attribute__((__nothrow__)) int wcsncmp (const wchar_t *, const wchar_t *, size_t);
 __attribute__((__cdecl__)) __attribute__((__nothrow__)) wchar_t *wcsncpy (wchar_t *, const wchar_t *, size_t);
 __attribute__((__cdecl__)) __attribute__((__nothrow__)) wchar_t *wcspbrk (const wchar_t *, const wchar_t *);
 __attribute__((__cdecl__)) __attribute__((__nothrow__)) wchar_t *wcsrchr (const wchar_t *, wchar_t);
 __attribute__((__cdecl__)) __attribute__((__nothrow__)) size_t wcsspn (const wchar_t *, const wchar_t *);
 __attribute__((__cdecl__)) __attribute__((__nothrow__)) wchar_t *wcsstr (const wchar_t *, const wchar_t *);
 __attribute__((__cdecl__)) __attribute__((__nothrow__)) wchar_t *wcstok (wchar_t *, const wchar_t *);
 __attribute__((__cdecl__)) __attribute__((__nothrow__)) size_t wcsxfrm (wchar_t *, const wchar_t *, size_t);




 __attribute__((__cdecl__)) __attribute__((__nothrow__)) wchar_t *_wcsdup (const wchar_t *);
 __attribute__((__cdecl__)) __attribute__((__nothrow__)) int _wcsicmp (const wchar_t *, const wchar_t *);
 __attribute__((__cdecl__)) __attribute__((__nothrow__)) int _wcsicoll (const wchar_t *, const wchar_t *);
 __attribute__((__cdecl__)) __attribute__((__nothrow__)) wchar_t *_wcslwr (wchar_t*);
 __attribute__((__cdecl__)) __attribute__((__nothrow__)) int _wcsnicmp (const wchar_t *, const wchar_t *, size_t);
 __attribute__((__cdecl__)) __attribute__((__nothrow__)) wchar_t *_wcsnset (wchar_t *, wchar_t, size_t);
 __attribute__((__cdecl__)) __attribute__((__nothrow__)) wchar_t *_wcsrev (wchar_t *);
 __attribute__((__cdecl__)) __attribute__((__nothrow__)) wchar_t *_wcsset (wchar_t *, wchar_t);
 __attribute__((__cdecl__)) __attribute__((__nothrow__)) wchar_t *_wcsupr (wchar_t *);


 __attribute__((__cdecl__)) __attribute__((__nothrow__)) int _wcsncoll (const wchar_t *, const wchar_t *, size_t);
 __attribute__((__cdecl__)) __attribute__((__nothrow__)) int _wcsnicoll (const wchar_t *, const wchar_t *, size_t);
# 445 "c:\\mingw\\include\\wchar.h" 3
int __attribute__((__cdecl__)) __attribute__((__nothrow__)) wcscmpi (const wchar_t *, const wchar_t *);
# 457 "c:\\mingw\\include\\wchar.h" 3
 __attribute__((__cdecl__)) __attribute__((__nothrow__)) wchar_t *wcsdup (const wchar_t *);
 __attribute__((__cdecl__)) __attribute__((__nothrow__)) int wcsicmp (const wchar_t *, const wchar_t *);
 __attribute__((__cdecl__)) __attribute__((__nothrow__)) int wcsicoll (const wchar_t *, const wchar_t *);
 __attribute__((__cdecl__)) __attribute__((__nothrow__)) wchar_t *wcslwr (wchar_t *);
 __attribute__((__cdecl__)) __attribute__((__nothrow__)) int wcsnicmp (const wchar_t *, const wchar_t *, size_t);
 __attribute__((__cdecl__)) __attribute__((__nothrow__)) wchar_t *wcsnset (wchar_t *, wchar_t, size_t);
 __attribute__((__cdecl__)) __attribute__((__nothrow__)) wchar_t *wcsrev (wchar_t *);
 __attribute__((__cdecl__)) __attribute__((__nothrow__)) wchar_t *wcsset (wchar_t *, wchar_t);
 __attribute__((__cdecl__)) __attribute__((__nothrow__)) wchar_t *wcsupr (wchar_t *);
# 491 "c:\\mingw\\include\\wchar.h" 3
extern size_t __mingw_wcsnlen (const wchar_t *, size_t);


extern inline __attribute__((__gnu_inline__)) __attribute__((__always_inline__)) size_t wcsnlen (const wchar_t *__text, size_t __maxlen)
{ return __mingw_wcsnlen (__text, __maxlen); }
# 171 "c:\\mingw\\include\\string.h" 2 3
# 193 "c:\\mingw\\include\\string.h" 3
extern size_t __mingw_strnlen (const char *, size_t);


extern inline __attribute__((__gnu_inline__)) __attribute__((__always_inline__)) size_t strnlen (const char *__text, size_t __maxlen)
{ return __mingw_strnlen (__text, __maxlen); }
# 212 "c:\\mingw\\include\\string.h" 3
extern int strerror_r (int, char *, size_t);
# 227 "c:\\mingw\\include\\string.h" 3
extern inline __attribute__((__gnu_inline__)) __attribute__((__always_inline__)) int strerror_s (char *__buf, size_t __len, int __err)
{ return strerror_r (__err, __buf, __len); }





# 5 "analyseurLexical.c" 2





# 9 "analyseurLexical.c"
char yyLine[255];
Instruction yyInstruction;
InstructionBis yyInstructionBis;
# 30 "analyseurLexical.c"
char * instructionModeles[] = {
    "<> Change Switch: [_] = _",
    "<> Change Variable: [_] _ _",
    "<> Change Items: _ _ piece of item #_",
    "<> Label: _",
    "<> Jump To Label: _",
    "<> Fork Condition: If _ [_] _ _ _ |",
    "<> Loop",
    "<> Break Loop",
    "<> Show Picture: #_, _, |",
    ": End of fork",
    ": End of loop",
    ": Else ...",
    "<>",
    "<> Set Event Location|"
    "<> Change Panorama|"
};




Instruction instructionsCorrespondantes[] = {
    ChgSwitch,
    ChgVariable,
    ChangeItem,
    Label,
    JumpToLabel,
    ForkIf,
    Loop,
    LoopEnd,
    ForkElse,
    ShowPicture,
    ForkEnd,
    LoopBreak,
    Void,
    Ignore,
    Ignore

};

typedef struct {
    Instruction instruction;
    char chaine[5*50];
} InstructionsEnCoursDeLecture;


int filedescriptor = -1;
# 85 "analyseurLexical.c"
int readLine() {
    if (filedescriptor == -1) {
        return -1;
    }

    int r;
    r = _read(filedescriptor, yyLine, 255 -1);

    if (r < 0)
        return r;

    yyLine[r] = 0;

    if (r == 0)
        return 1;
    else
        return 0;
}



InstructionsEnCoursDeLecture lireLigne() {
    InstructionsEnCoursDeLecture resultat;
    int i_schema, i_chaineEnCours, i_ligneActuelle;
    int chaineActuelle;
    int schemaActuel;


    for (i_ligneActuelle = 0
        ; !yyLine[i_ligneActuelle] || yyLine[i_ligneActuelle] == ' '
        ; i_ligneActuelle ++) {
    }

    if (!yyLine[i_ligneActuelle]) {
        resultat.instruction = Instr_Erreur;
        return resultat;
    }


    for (schemaActuel = 0 ; schemaActuel != 15 ; schemaActuel ++) {
        i_schema = 0;
        i_chaineEnCours = 0;
        chaineActuelle = 0;

        while (1) {
            if (instructionModeles[schemaActuel][i_schema] == '|'
                || (instructionModeles[schemaActuel][i_schema] == 0 && yyLine[i_ligneActuelle] == 0) ) {
                resultat.instruction = instructionsCorrespondantes[schemaActuel];
                return resultat;
            }

            if (instructionModeles[schemaActuel][i_schema] == 0 || (yyLine[i_ligneActuelle] == 0 && instructionModeles[schemaActuel][i_schema] != '_')) {
                break;
            }

            if (instructionModeles[schemaActuel][i_schema] == '_') {

                if (instructionModeles[schemaActuel][i_schema+1] == yyLine[i_ligneActuelle]) {
                    resultat.chaine[(chaineActuelle++) * 50 + i_chaineEnCours] = 0;

                    i_chaineEnCours = 0;
                    i_schema ++;
                    continue;
                }


                resultat.chaine[chaineActuelle * 50 + (i_chaineEnCours++)] = yyLine[i_ligneActuelle++];
            } else {

                if (instructionModeles[schemaActuel][i_schema] != yyLine[i_ligneActuelle]) {


                    if (instructionModeles[schemaActuel][i_schema] != '<'
                        && yyLine[i_ligneActuelle] != '@')
                        break;
                }

                i_schema++;
                i_ligneActuelle++;
            }

        }
    }


    resultat.instruction = Instr_Erreur;
    return resultat;
}


InstructionEnsemble * analyserLigne(InstructionsEnCoursDeLecture iECDL) {
    InstructionEnsemble * instructionEnsemble = malloc(sizeof(InstructionEnsemble));
    if (instructionEnsemble == 
# 177 "analyseurLexical.c" 3 4
                              ((void *)0)
# 177 "analyseurLexical.c"
                                  )
        return 
# 178 "analyseurLexical.c" 3 4
              ((void *)0)
# 178 "analyseurLexical.c"
                  ;


    instructionEnsemble->instruction = iECDL.instruction;

    if (iECDL.instruction == Ignore)
        return instructionEnsemble;

    if (iECDL.instruction == ChgSwitch) {

        instructionEnsemble->complement.affectation.numero = atoi(iECDL.chaine);


        if (iECDL.chaine[1*50 + 1] == 'N') {
            instructionEnsemble->complement.affectation.nouvelleValeur = 1;
        } else {
            instructionEnsemble->complement.affectation.nouvelleValeur = 0;
        }
    } else if (iECDL.instruction == ChgVariable) {

        instructionEnsemble->complement.affectation.numero = atoi(iECDL.chaine);
# 209 "analyseurLexical.c"
        switch (iECDL.chaine[1*50]) {
        case '=':
            instructionEnsemble->complement.affectation.signe = Egal;
            break;
        case '-':
            instructionEnsemble->complement.affectation.signe = Moins;
            break;
        case '+':
            instructionEnsemble->complement.affectation.signe = Plus;
            break;
        case '*':
            instructionEnsemble->complement.affectation.signe = Fois;
            break;
        case '/':
            instructionEnsemble->complement.affectation.signe = Divise;
            break;
        case 'M':
            instructionEnsemble->complement.affectation.signe = Modulo;
            break;
        default:
            instructionEnsemble->complement.affectation.signe = Egal;
            break;
        }






        instructionEnsemble->complement.affectation.nouvelleValeur = atoi(iECDL.chaine + 2*50);
    } else if (iECDL.instruction == ChangeItem) {

        instructionEnsemble->complement.changeItem.numero = atoi(iECDL.chaine + 2*50);
        instructionEnsemble->complement.changeItem.quantite = atoi(iECDL.chaine + 1*50);

        if (iECDL.chaine[0] == 'R') {
            instructionEnsemble->complement.changeItem.quantite *= -1;
        }
    } else if (iECDL.instruction == Label || iECDL.instruction == JumpToLabel) {
        instructionEnsemble->complement.numero = atoi(iECDL.chaine);
    } else if (iECDL.instruction == ShowPicture) {

        strcpy(instructionEnsemble->complement.showPicture, iECDL.chaine + 1*50);
    } else if (iECDL.instruction == ForkIf) {
# 263 "analyseurLexical.c"
        if (iECDL.chaine[0] == 'S') {
            instructionEnsemble->complement.forkIf.type = 0;
        } else {
            instructionEnsemble->complement.forkIf.type = 1;
        }

        instructionEnsemble->complement.numero = atoi(iECDL.chaine + 1*50);


        char symboleEnCours;

        symboleEnCours = iECDL.chaine[2*50];

        if (symboleEnCours == ' ') {

            instructionEnsemble->complement.forkIf.comparatif = Different;
        } else if (symboleEnCours == '=') {

            instructionEnsemble->complement.forkIf.comparatif = Egal;
        } else if (symboleEnCours == '<') {
            symboleEnCours = iECDL.chaine[2*50 + 1];

            if (symboleEnCours == '=') {
                instructionEnsemble->complement.forkIf.comparatif = InfEgal;
            } else {
                instructionEnsemble->complement.forkIf.comparatif = Inferieur;
            }
        } else {
            symboleEnCours = iECDL.chaine[2*50 + 1];

            if (symboleEnCours == '=') {
                instructionEnsemble->complement.forkIf.comparatif = SupEgal;
            } else {
                instructionEnsemble->complement.forkIf.comparatif = Superieur;
            }
        }


        if (iECDL.chaine[3*50] == 'V') {
            instructionEnsemble->complement.forkIf.pointeur = 1;
            instructionEnsemble->complement.forkIf.valeur = atoi(iECDL.chaine + 4*50 + 1);
        } else {
            if (instructionEnsemble->complement.forkIf.type == 1) {
                instructionEnsemble->complement.forkIf.pointeur = 0;
                instructionEnsemble->complement.forkIf.valeur = atoi(iECDL.chaine + 3*50);
            } else {
                if (iECDL.chaine[3*50+1] == 'N') {
                    instructionEnsemble->complement.forkIf.valeur = 1;
                } else {
                    instructionEnsemble->complement.forkIf.valeur = 0;
                }
            }
        }
    }

    return instructionEnsemble;
}



InstructionEnsemble * initialiserLaNouvelleLigne() {
    if (readLine())
        return 0;

    InstructionsEnCoursDeLecture iECDL;
    InstructionEnsemble * instructionEnsemble;

    iECDL = lireLigne();
    instructionEnsemble = analyserLigne(iECDL);

    return instructionEnsemble;
}
