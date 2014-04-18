#include "e.h"

int d_bss;
int d_data = 0;
int d_text(void){
	d_bss = e_data;
	return d_data;
}

