#include "d.h"

int e_bss;
int e_data = 0;
int e_text(void){
	e_bss = d_text();
	return e_data;
}

