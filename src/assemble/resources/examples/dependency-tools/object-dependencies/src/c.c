#include "e.h"

int c_bss;
int c_data = 0;
int c_text(void){
	c_bss = e_text();
	c_data = e_data;
	return c_bss;
}

