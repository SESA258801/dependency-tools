#include <stdio.h>
#include "starwars/alliance/character/Luc.h"

string DarthVaderName = "DarthVader";
string* DarthVaderSonName = &LucName;

string METH(character,getName)(void){
	return DarthVaderName;
}

void  METH(character,talkTo)(starwars_Character interlocutor){
	const char* interlocutorName = CALL_PTR(interlocutor,getName)();
	if (interlocutorName == *DarthVaderSonName) {
		printf("%s : I'm your father %s !\n", DarthVaderName, interlocutorName);
	} else {
		printf("%s : Go away from me %s !\n", DarthVaderName, interlocutorName);
	}
	CALL_PTR(interlocutor,talkTo)(GET_MY_INTERFACE(character));
}
