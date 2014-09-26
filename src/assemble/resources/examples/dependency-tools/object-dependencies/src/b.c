#include "d.h"

int b_bss;
int b_data = 0;
int b_text(void){
	d_text();
	b_data = d_data;
	return b_bss;
}

