#include <stdio.h>
#include "starwars/alliance/character/Leia.h"

string HanName = "Han Solo";
string* HanLoverName = &LeiaName;

string METH(character,getName)(void){
	return HanName;
}

void  METH(character,talkTo)(starwars_Character interlocutor){
	const char* interlocutorName = CALL_PTR(interlocutor,getName)();
	if (interlocutorName == *HanLoverName) {
		printf("%s : I love you %s !\n", HanName, interlocutorName);
	} else {
		printf("%s : Hehe...\n", HanName);
	}
}
