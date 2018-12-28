#ifndef INTERFACE_H
#define INTERFACE_H

#include <jni.h>

#include "map.h"
#include "overworld.h"
#include "underground.h"

extern "C" {
	JNIEXPORT jobjectArray JNICALL 
	Java_com_gueg_mario_Generator_generateMap
	(JNIEnv* env, jobject thiz, jint w, jint h, jint worldType );
}

#endif
