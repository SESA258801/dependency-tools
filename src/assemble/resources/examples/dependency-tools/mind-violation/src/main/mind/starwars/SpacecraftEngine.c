#include <stdio.h>

void METH(spacecraft,takeOff)(void)
{
	// A space-craft should be able to fly isn't it ?
	printf("\n\nThe %s is taking off !\n",ATTR(name));
}

string METH(spacecraft,getName)(void)
{
	return ATTR(name);
}
starwars_Character METH(spacecraft,getPassenger)(void){
	starwars_Character passenger = GET_MY_INTERFACE(passengers[ATTR(currentPassenger)]);
	ATTR(currentPassenger) =  ATTR(currentPassenger)++ % GET_COLLECTION_SIZE(passengers);
	return passenger;
}

starwars_Character METH(spacecraft,removePassenger)(void){
	starwars_Character passenger = 0;
	int i;
	for ( i =0 ; i<GET_COLLECTION_SIZE(passengers); i++) {
		if (IS_BOUND(passengers[i])) {
			passenger = GET_MY_INTERFACE(passengers[i]);
			printf("\n%s gets out of the %s.\n",CALL(passengers[i],getName)(), ATTR(name));
			BIND_MY_INTERFACE(passengers[i],0);
			return passenger;
		}
	}
	return passenger;
}

void METH(spacecraft,addPassenger)(starwars_Character newPassenger){
	int i;
	for ( i =0 ; i<GET_COLLECTION_SIZE(passengers); i++) {
		if (!IS_BOUND(passengers[i])) {
			BIND_MY_INTERFACE(passengers[i],newPassenger);
			printf("\n%s gets into the %s.",CALL(passengers[i],getName)(), ATTR(name));
			return;
		}
	}
}
