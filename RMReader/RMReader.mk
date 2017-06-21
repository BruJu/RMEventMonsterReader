##
## Auto Generated makefile by CodeLite IDE
## any manual changes will be erased      
##
## Debug
ProjectName            :=RMReader
ConfigurationName      :=Debug
WorkspacePath          :=A:/Users/Dheim/Documents/GitHub/RMEventMonsterReader
ProjectPath            :=A:/Users/Dheim/Documents/GitHub/RMEventMonsterReader/RMReader
IntermediateDirectory  :=./Debug
OutDir                 := $(IntermediateDirectory)
CurrentFileName        :=
CurrentFilePath        :=
CurrentFileFullPath    :=
User                   :=Dheim
Date                   :=21/06/2017
CodeLitePath           :="C:/Program Files/CodeLite"
LinkerName             :=c:/mingw/bin/g++.exe
SharedObjectLinkerName :=c:/mingw/bin/g++.exe -shared -fPIC
ObjectSuffix           :=.o
DependSuffix           :=.o.d
PreprocessSuffix       :=.i
DebugSwitch            :=-g 
IncludeSwitch          :=-I
LibrarySwitch          :=-l
OutputSwitch           :=-o 
LibraryPathSwitch      :=-L
PreprocessorSwitch     :=-D
SourceSwitch           :=-c 
OutputFile             :=$(IntermediateDirectory)/$(ProjectName)
Preprocessors          :=
ObjectSwitch           :=-o 
ArchiveOutputSwitch    := 
PreprocessOnlySwitch   :=-E
ObjectsFileList        :="RMReader.txt"
PCHCompileFlags        :=
MakeDirCommand         :=makedir
RcCmpOptions           := 
RcCompilerName         :=c:/mingw/bin/windres.exe
LinkOptions            :=  
IncludePath            :=  $(IncludeSwitch). $(IncludeSwitch). 
IncludePCH             := 
RcIncludePath          := 
Libs                   := 
ArLibs                 :=  
LibPath                := $(LibraryPathSwitch). 

##
## Common variables
## AR, CXX, CC, AS, CXXFLAGS and CFLAGS can be overriden using an environment variables
##
AR       := c:/mingw/bin/ar.exe rcu
CXX      := c:/mingw/bin/g++.exe
CC       := c:/mingw/bin/gcc.exe
CXXFLAGS :=  -g -O0 -Wall -Werror -Wextra -W $(Preprocessors)
CFLAGS   :=  -g -O0 -Wall -Werror -Wextra -W $(Preprocessors)
ASFLAGS  := 
AS       := c:/mingw/bin/as.exe


##
## User defined environment variables
##
CodeLiteDir:=C:\Program Files\CodeLite
Objects0=$(IntermediateDirectory)/main.c$(ObjectSuffix) $(IntermediateDirectory)/analyseurLexical.c$(ObjectSuffix) $(IntermediateDirectory)/testAnalyseurLexical.c$(ObjectSuffix) $(IntermediateDirectory)/configReader.c$(ObjectSuffix) $(IntermediateDirectory)/grille.c$(ObjectSuffix) $(IntermediateDirectory)/listReader.c$(ObjectSuffix) $(IntermediateDirectory)/tools.c$(ObjectSuffix) $(IntermediateDirectory)/grammairePrincip.c$(ObjectSuffix) $(IntermediateDirectory)/grammaireSousGrp.c$(ObjectSuffix) $(IntermediateDirectory)/grammaire.c$(ObjectSuffix) \
	



Objects=$(Objects0) 

##
## Main Build Targets 
##
.PHONY: all clean PreBuild PrePreBuild PostBuild MakeIntermediateDirs
all: $(OutputFile)

$(OutputFile): $(IntermediateDirectory)/.d $(Objects) 
	@$(MakeDirCommand) $(@D)
	@echo "" > $(IntermediateDirectory)/.d
	@echo $(Objects0)  > $(ObjectsFileList)
	$(LinkerName) $(OutputSwitch)$(OutputFile) @$(ObjectsFileList) $(LibPath) $(Libs) $(LinkOptions)

MakeIntermediateDirs:
	@$(MakeDirCommand) "./Debug"


$(IntermediateDirectory)/.d:
	@$(MakeDirCommand) "./Debug"

PreBuild:


##
## Objects
##
$(IntermediateDirectory)/main.c$(ObjectSuffix): main.c $(IntermediateDirectory)/main.c$(DependSuffix)
	$(CC) $(SourceSwitch) "A:/Users/Dheim/Documents/GitHub/RMEventMonsterReader/RMReader/main.c" $(CFLAGS) $(ObjectSwitch)$(IntermediateDirectory)/main.c$(ObjectSuffix) $(IncludePath)
$(IntermediateDirectory)/main.c$(DependSuffix): main.c
	@$(CC) $(CFLAGS) $(IncludePath) -MG -MP -MT$(IntermediateDirectory)/main.c$(ObjectSuffix) -MF$(IntermediateDirectory)/main.c$(DependSuffix) -MM main.c

$(IntermediateDirectory)/main.c$(PreprocessSuffix): main.c
	$(CC) $(CFLAGS) $(IncludePath) $(PreprocessOnlySwitch) $(OutputSwitch) $(IntermediateDirectory)/main.c$(PreprocessSuffix) main.c

$(IntermediateDirectory)/analyseurLexical.c$(ObjectSuffix): analyseurLexical.c $(IntermediateDirectory)/analyseurLexical.c$(DependSuffix)
	$(CC) $(SourceSwitch) "A:/Users/Dheim/Documents/GitHub/RMEventMonsterReader/RMReader/analyseurLexical.c" $(CFLAGS) $(ObjectSwitch)$(IntermediateDirectory)/analyseurLexical.c$(ObjectSuffix) $(IncludePath)
$(IntermediateDirectory)/analyseurLexical.c$(DependSuffix): analyseurLexical.c
	@$(CC) $(CFLAGS) $(IncludePath) -MG -MP -MT$(IntermediateDirectory)/analyseurLexical.c$(ObjectSuffix) -MF$(IntermediateDirectory)/analyseurLexical.c$(DependSuffix) -MM analyseurLexical.c

$(IntermediateDirectory)/analyseurLexical.c$(PreprocessSuffix): analyseurLexical.c
	$(CC) $(CFLAGS) $(IncludePath) $(PreprocessOnlySwitch) $(OutputSwitch) $(IntermediateDirectory)/analyseurLexical.c$(PreprocessSuffix) analyseurLexical.c

$(IntermediateDirectory)/testAnalyseurLexical.c$(ObjectSuffix): testAnalyseurLexical.c $(IntermediateDirectory)/testAnalyseurLexical.c$(DependSuffix)
	$(CC) $(SourceSwitch) "A:/Users/Dheim/Documents/GitHub/RMEventMonsterReader/RMReader/testAnalyseurLexical.c" $(CFLAGS) $(ObjectSwitch)$(IntermediateDirectory)/testAnalyseurLexical.c$(ObjectSuffix) $(IncludePath)
$(IntermediateDirectory)/testAnalyseurLexical.c$(DependSuffix): testAnalyseurLexical.c
	@$(CC) $(CFLAGS) $(IncludePath) -MG -MP -MT$(IntermediateDirectory)/testAnalyseurLexical.c$(ObjectSuffix) -MF$(IntermediateDirectory)/testAnalyseurLexical.c$(DependSuffix) -MM testAnalyseurLexical.c

$(IntermediateDirectory)/testAnalyseurLexical.c$(PreprocessSuffix): testAnalyseurLexical.c
	$(CC) $(CFLAGS) $(IncludePath) $(PreprocessOnlySwitch) $(OutputSwitch) $(IntermediateDirectory)/testAnalyseurLexical.c$(PreprocessSuffix) testAnalyseurLexical.c

$(IntermediateDirectory)/configReader.c$(ObjectSuffix): configReader.c $(IntermediateDirectory)/configReader.c$(DependSuffix)
	$(CC) $(SourceSwitch) "A:/Users/Dheim/Documents/GitHub/RMEventMonsterReader/RMReader/configReader.c" $(CFLAGS) $(ObjectSwitch)$(IntermediateDirectory)/configReader.c$(ObjectSuffix) $(IncludePath)
$(IntermediateDirectory)/configReader.c$(DependSuffix): configReader.c
	@$(CC) $(CFLAGS) $(IncludePath) -MG -MP -MT$(IntermediateDirectory)/configReader.c$(ObjectSuffix) -MF$(IntermediateDirectory)/configReader.c$(DependSuffix) -MM configReader.c

$(IntermediateDirectory)/configReader.c$(PreprocessSuffix): configReader.c
	$(CC) $(CFLAGS) $(IncludePath) $(PreprocessOnlySwitch) $(OutputSwitch) $(IntermediateDirectory)/configReader.c$(PreprocessSuffix) configReader.c

$(IntermediateDirectory)/grille.c$(ObjectSuffix): grille.c $(IntermediateDirectory)/grille.c$(DependSuffix)
	$(CC) $(SourceSwitch) "A:/Users/Dheim/Documents/GitHub/RMEventMonsterReader/RMReader/grille.c" $(CFLAGS) $(ObjectSwitch)$(IntermediateDirectory)/grille.c$(ObjectSuffix) $(IncludePath)
$(IntermediateDirectory)/grille.c$(DependSuffix): grille.c
	@$(CC) $(CFLAGS) $(IncludePath) -MG -MP -MT$(IntermediateDirectory)/grille.c$(ObjectSuffix) -MF$(IntermediateDirectory)/grille.c$(DependSuffix) -MM grille.c

$(IntermediateDirectory)/grille.c$(PreprocessSuffix): grille.c
	$(CC) $(CFLAGS) $(IncludePath) $(PreprocessOnlySwitch) $(OutputSwitch) $(IntermediateDirectory)/grille.c$(PreprocessSuffix) grille.c

$(IntermediateDirectory)/listReader.c$(ObjectSuffix): listReader.c $(IntermediateDirectory)/listReader.c$(DependSuffix)
	$(CC) $(SourceSwitch) "A:/Users/Dheim/Documents/GitHub/RMEventMonsterReader/RMReader/listReader.c" $(CFLAGS) $(ObjectSwitch)$(IntermediateDirectory)/listReader.c$(ObjectSuffix) $(IncludePath)
$(IntermediateDirectory)/listReader.c$(DependSuffix): listReader.c
	@$(CC) $(CFLAGS) $(IncludePath) -MG -MP -MT$(IntermediateDirectory)/listReader.c$(ObjectSuffix) -MF$(IntermediateDirectory)/listReader.c$(DependSuffix) -MM listReader.c

$(IntermediateDirectory)/listReader.c$(PreprocessSuffix): listReader.c
	$(CC) $(CFLAGS) $(IncludePath) $(PreprocessOnlySwitch) $(OutputSwitch) $(IntermediateDirectory)/listReader.c$(PreprocessSuffix) listReader.c

$(IntermediateDirectory)/tools.c$(ObjectSuffix): tools.c $(IntermediateDirectory)/tools.c$(DependSuffix)
	$(CC) $(SourceSwitch) "A:/Users/Dheim/Documents/GitHub/RMEventMonsterReader/RMReader/tools.c" $(CFLAGS) $(ObjectSwitch)$(IntermediateDirectory)/tools.c$(ObjectSuffix) $(IncludePath)
$(IntermediateDirectory)/tools.c$(DependSuffix): tools.c
	@$(CC) $(CFLAGS) $(IncludePath) -MG -MP -MT$(IntermediateDirectory)/tools.c$(ObjectSuffix) -MF$(IntermediateDirectory)/tools.c$(DependSuffix) -MM tools.c

$(IntermediateDirectory)/tools.c$(PreprocessSuffix): tools.c
	$(CC) $(CFLAGS) $(IncludePath) $(PreprocessOnlySwitch) $(OutputSwitch) $(IntermediateDirectory)/tools.c$(PreprocessSuffix) tools.c

$(IntermediateDirectory)/grammairePrincip.c$(ObjectSuffix): grammairePrincip.c $(IntermediateDirectory)/grammairePrincip.c$(DependSuffix)
	$(CC) $(SourceSwitch) "A:/Users/Dheim/Documents/GitHub/RMEventMonsterReader/RMReader/grammairePrincip.c" $(CFLAGS) $(ObjectSwitch)$(IntermediateDirectory)/grammairePrincip.c$(ObjectSuffix) $(IncludePath)
$(IntermediateDirectory)/grammairePrincip.c$(DependSuffix): grammairePrincip.c
	@$(CC) $(CFLAGS) $(IncludePath) -MG -MP -MT$(IntermediateDirectory)/grammairePrincip.c$(ObjectSuffix) -MF$(IntermediateDirectory)/grammairePrincip.c$(DependSuffix) -MM grammairePrincip.c

$(IntermediateDirectory)/grammairePrincip.c$(PreprocessSuffix): grammairePrincip.c
	$(CC) $(CFLAGS) $(IncludePath) $(PreprocessOnlySwitch) $(OutputSwitch) $(IntermediateDirectory)/grammairePrincip.c$(PreprocessSuffix) grammairePrincip.c

$(IntermediateDirectory)/grammaireSousGrp.c$(ObjectSuffix): grammaireSousGrp.c $(IntermediateDirectory)/grammaireSousGrp.c$(DependSuffix)
	$(CC) $(SourceSwitch) "A:/Users/Dheim/Documents/GitHub/RMEventMonsterReader/RMReader/grammaireSousGrp.c" $(CFLAGS) $(ObjectSwitch)$(IntermediateDirectory)/grammaireSousGrp.c$(ObjectSuffix) $(IncludePath)
$(IntermediateDirectory)/grammaireSousGrp.c$(DependSuffix): grammaireSousGrp.c
	@$(CC) $(CFLAGS) $(IncludePath) -MG -MP -MT$(IntermediateDirectory)/grammaireSousGrp.c$(ObjectSuffix) -MF$(IntermediateDirectory)/grammaireSousGrp.c$(DependSuffix) -MM grammaireSousGrp.c

$(IntermediateDirectory)/grammaireSousGrp.c$(PreprocessSuffix): grammaireSousGrp.c
	$(CC) $(CFLAGS) $(IncludePath) $(PreprocessOnlySwitch) $(OutputSwitch) $(IntermediateDirectory)/grammaireSousGrp.c$(PreprocessSuffix) grammaireSousGrp.c

$(IntermediateDirectory)/grammaire.c$(ObjectSuffix): grammaire.c $(IntermediateDirectory)/grammaire.c$(DependSuffix)
	$(CC) $(SourceSwitch) "A:/Users/Dheim/Documents/GitHub/RMEventMonsterReader/RMReader/grammaire.c" $(CFLAGS) $(ObjectSwitch)$(IntermediateDirectory)/grammaire.c$(ObjectSuffix) $(IncludePath)
$(IntermediateDirectory)/grammaire.c$(DependSuffix): grammaire.c
	@$(CC) $(CFLAGS) $(IncludePath) -MG -MP -MT$(IntermediateDirectory)/grammaire.c$(ObjectSuffix) -MF$(IntermediateDirectory)/grammaire.c$(DependSuffix) -MM grammaire.c

$(IntermediateDirectory)/grammaire.c$(PreprocessSuffix): grammaire.c
	$(CC) $(CFLAGS) $(IncludePath) $(PreprocessOnlySwitch) $(OutputSwitch) $(IntermediateDirectory)/grammaire.c$(PreprocessSuffix) grammaire.c


-include $(IntermediateDirectory)/*$(DependSuffix)
##
## Clean
##
clean:
	$(RM) -r ./Debug/


