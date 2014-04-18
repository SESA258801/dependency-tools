#include <stdio.h>

int METH(entryPoint,main)(int argc, char** argv){
	starwars_Character commander = CALL(deathStar,getPassenger)();
	starwars_Character  allies[10]={0};
	int i;
	int j;

	if (commander != NULL) {
		printf("\n%s, the commander of the %s welcomes his prisoners from the %s. \n",
				CALL_PTR(commander,getName)(), CALL(deathStar,getName)(), CALL(milleniumFalcon,getName)() );

		i=0;
		allies[i] = CALL(milleniumFalcon,removePassenger)();
		while (allies[i] != NULL) {
			CALL_PTR(commander, talkTo)(allies[i]);
			i++;
			allies[i] = CALL(milleniumFalcon,removePassenger)();
		}
	}
	printf("\nBut they all escape !\n");
	while(i--) {
		CALL(milleniumFalcon,addPassenger)(allies[i]);
	}

	CALL(milleniumFalcon,fly)();

	printf("\nAnd congratulate themself.\n");
	for (i=0; i<10; i++)
		if (allies[i]!=0) {
			for (j=i+1; j<10; j++){
				if ((allies[j]!=0)&&(j!=i)){
					CALL_PTR(allies[j],talkTo)(allies[i]);
					CALL_PTR(allies[i],talkTo)(allies[j]);
				}
			}
		}

}
