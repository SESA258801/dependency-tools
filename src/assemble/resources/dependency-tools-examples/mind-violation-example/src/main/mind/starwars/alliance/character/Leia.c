#include <stdio.h>
#include "starwars/alliance/character/Han.h"

string LeiaName="Leia";
string* LeiaLoverName = &HanName;

string METH(character,getName)(void){
	return LeiaName;
}

void  METH(character,talkTo)(starwars_Character interlocutor){
	const char* interlocutorName = CALL_PTR(interlocutor,getName)();
	if (interlocutorName == *LeiaLoverName) {
		printf("%s : I love you %s !\n", LeiaName, interlocutorName);
	} else {
		printf("%s : %s !\n", LeiaName, interlocutorName);
	}
}
