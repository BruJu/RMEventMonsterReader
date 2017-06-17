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
Date                   :=17/06/2017
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
CXXFLAGS :=  -g -O0 -Wall $(Preprocessors)
CFLAGS   :=  -g -O0 -Wall $(Preprocessors)
ASFLAGS  := 
AS       := c:/mingw/bin/as.exe


##
## User defined environment variables
##
CodeLiteDir:=C:\Program Files\CodeLite
Objects0=$(IntermediateDirectory)/main.c$(ObjectSuffix) $(IntermediateDirectory)/analyseurLexical.c$(ObjectSuffix) $(IntermediateDirectory)/tools.c$(ObjectSuffix) $(IntermediateDirectory)/testAnalyseurLexical.c$(ObjectSuffix) 



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

$(IntermediateDirectory)/tools.c$(ObjectSuffix): tools.c $(IntermediateDirectory)/tools.c$(DependSuffix)
	$(CC) $(SourceSwitch) "A:/Users/Dheim/Documents/GitHub/RMEventMonsterReader/RMReader/tools.c" $(CFLAGS) $(ObjectSwitch)$(IntermediateDirectory)/tools.c$(ObjectSuffix) $(IncludePath)
$(IntermediateDirectory)/tools.c$(DependSuffix): tools.c
	@$(CC) $(CFLAGS) $(IncludePath) -MG -MP -MT$(IntermediateDirectory)/tools.c$(ObjectSuffix) -MF$(IntermediateDirectory)/tools.c$(DependSuffix) -MM tools.c

$(IntermediateDirectory)/tools.c$(PreprocessSuffix): tools.c
	$(CC) $(CFLAGS) $(IncludePath) $(PreprocessOnlySwitch) $(OutputSwitch) $(IntermediateDirectory)/tools.c$(PreprocessSuffix) tools.c

$(IntermediateDirectory)/testAnalyseurLexical.c$(ObjectSuffix): testAnalyseurLexical.c $(IntermediateDirectory)/testAnalyseurLexical.c$(DependSuffix)
	$(CC) $(SourceSwitch) "A:/Users/Dheim/Documents/GitHub/RMEventMonsterReader/RMReader/testAnalyseurLexical.c" $(CFLAGS) $(ObjectSwitch)$(IntermediateDirectory)/testAnalyseurLexical.c$(ObjectSuffix) $(IncludePath)
$(IntermediateDirectory)/testAnalyseurLexical.c$(DependSuffix): testAnalyseurLexical.c
	@$(CC) $(CFLAGS) $(IncludePath) -MG -MP -MT$(IntermediateDirectory)/testAnalyseurLexical.c$(ObjectSuffix) -MF$(IntermediateDirectory)/testAnalyseurLexical.c$(DependSuffix) -MM testAnalyseurLexical.c

$(IntermediateDirectory)/testAnalyseurLexical.c$(PreprocessSuffix): testAnalyseurLexical.c
	$(CC) $(CFLAGS) $(IncludePath) $(PreprocessOnlySwitch) $(OutputSwitch) $(IntermediateDirectory)/testAnalyseurLexical.c$(PreprocessSuffix) testAnalyseurLexical.c


-include $(IntermediateDirectory)/*$(DependSuffix)
##
## Clean
##
clean:
	$(RM) -r ./Debug/


