
 
kompilator=g++
standard=-std=c++14
optymalizacja=-O3
errors=-pedantic-errors
# optymalizacja=
# debug=-g
debug=
profilowanie=-pg
memory=memory

.PHONY: clean

__ : main
	./main   
	./main -i dane.txt -t drzewo.txt -o wyjscie
	
	
main : main.o              \
       debug_new.o         \
       mem_pool_base.o     \
       bool_array.o        \
       static_mem_pool.o   \
       file_line_reader.o  \
       mmap_reader_base.o  
	$(kompilator) $(standard) $(debug) $(optymalizacja) -pedantic-errors -o $@ $^ 

main.o : main.cpp
	$(kompilator) $(standard) $(debug) $(optymalizacja) $(errors) -c -o $@ $^ 
	            
# kompilacja biblioteki do kontroli uzycia pamieci

debug_new.o : $(memory)/debug_new.cpp
	$(kompilator) $(standard) $(debug) $(optymalizacja) $(errors)  -c -o $@ $^ 
	
static_mem_pool.o : $(memory)/static_mem_pool.cpp
	$(kompilator) $(standard) $(debug) $(optymalizacja) $(errors)  -c -o $@ $^ 		

mem_pool_base.o : $(memory)/mem_pool_base.cpp
	$(kompilator) $(standard) $(debug) $(optymalizacja) $(errors) -c -o $@ $^ 		

bool_array.o : $(memory)/bool_array.cpp
	$(kompilator) $(standard) $(debug) $(optymalizacja) $(errors)  -c -o $@ $^ 	

file_line_reader.o : $(memory)/file_line_reader.cpp
	$(kompilator) $(standard) $(debug) $(optymalizacja) $(errors)  -c -o $@ $^ 

mmap_reader_base.o : $(memory)/mmap_reader_base.cpp
	$(kompilator) $(standard) $(debug) $(optymalizacja) $(errors)  -c -o $@ $^ 

 
########################
		 
	
clean : 	
	for f in `ls *.o *~ ` ; do  if [ -f $$f ] ; then rm $$f  ; fi;   done;
	if [ -f main ] ; then rm main ; fi ;
	if [ -d html ] ; then rm -r html; fi ;

