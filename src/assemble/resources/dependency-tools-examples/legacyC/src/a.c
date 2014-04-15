#include "b.h"
#include "c.h"

int a_bss;
int a_data = 0;
int a_text(void){
	a_bss = b_data;
	a_data = c_data;
	b_bss = c_text();
	return b_bss;
}


