SUBDIRS = $(patsubst %/,%, $(sort $(dir $(wildcard */))))

.PHONY: $(MAKECMDGOALS) $(SUBDIRS) subdir

# To handle the case of $(MAKECMDGOALS) is empty
subdir: $(SUBDIRS)

$(SUBDIRS):
	$(MAKE) -C $@

# To handle the case of $(MAKECMDGOALS) is not empty
$(MAKECMDGOALS):
	@for dir in $(SUBDIRS); do \
        $(MAKE) -C $$dir $(MAKECMDGOALS) || exit $$? ;\
    done
