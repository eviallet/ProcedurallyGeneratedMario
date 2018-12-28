#include "Interface.h"

JNIEXPORT jobjectArray JNICALL 
Java_com_gueg_mario_Generator_generateMap
(JNIEnv* env,
jobject thiz,
jint w,
jint h,
jint worldType )
{
	Map map(w, h);
	switch (worldType) {
	case 0: // OVERWORLD
		Overworld(&map).generate();
		break;
	case 1: // UNDERGROUND
		Underground(&map).generate();
		break;
	}
	
	// Conversion from int** to jint* ; source
	// https://stackoverflow.com/q/18012438
	
	// The 2D int array to return
    int **mapArray = map.toIntArray();

    // Get the int array class
    jclass intArrayClass = (*env).FindClass("[I");

    // Check if we properly got the int array class
    if (intArrayClass == NULL) {
        return NULL;
    }
	
	int length1D = h;
	int length2D = w;

    // Create the returnable 2D array
    jobjectArray returnable2DArray = (*env).NewObjectArray((jsize) length1D, intArrayClass, NULL);

    // Go through the firs dimension and add the second dimension arrays
    for (unsigned int i = 0; i < length1D; i++) {
        jintArray intArray = (*env).NewIntArray(length2D);
        (*env).SetIntArrayRegion(intArray, (jsize) 0, (jsize) length2D, (jint*) mapArray[i]);
        (*env).SetObjectArrayElement(returnable2DArray, (jsize) i, intArray);
        (*env).DeleteLocalRef(intArray);
    }

    // Return a Java consumable 2D long array
    return returnable2DArray;
}
