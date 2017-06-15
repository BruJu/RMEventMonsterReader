.PHONY: clean All

All:
	@echo "----------Building project:[ RMReader - Debug ]----------"
	@cd "RMReader" && "$(MAKE)" -f  "RMReader.mk"
clean:
	@echo "----------Cleaning project:[ RMReader - Debug ]----------"
	@cd "RMReader" && "$(MAKE)" -f  "RMReader.mk" clean
