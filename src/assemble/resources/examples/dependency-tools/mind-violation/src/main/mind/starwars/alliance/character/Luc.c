#include <stdio.h>
string LucName = "Luc";

string METH(character,getName)(void){
	return LucName;
}

void  METH(character,talkTo)(starwars_Character interlocutor){
		printf("%s : The bright side of the force will rise again %s !\n", LucName, CALL_PTR(interlocutor,getName)());
}
