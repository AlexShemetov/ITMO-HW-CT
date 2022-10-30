#ifndef C_2_ALEXSHEMETOV_ERROR_MESSAGES_H
#define C_2_ALEXSHEMETOV_ERROR_MESSAGES_H

#ifndef ERRORMESSAGE_SUCCESS
	#define ERRORMESSAGE_SUCCESS ""
// The operation completed successfully
#endif

#ifndef ERRORMESSAGE_FILE_NOT_FOUND
	#define ERRORMESSAGE_FILE_NOT_FOUND "File not found"
// The system cannot find the file specified.
#endif

#ifndef ERRORMESSAGE_PATH_NOT_FOUND
	#define ERRORMESSAGE_PATH_NOT_FOUND "Path not found"
// The system cannot find the path specified.
#endif

#ifndef ERRORMESSAGE_FILE_EXISTS
	#define ERRORMESSAGE_FILE_EXISTS "File exists"
// The file exists
#endif

#ifndef ERRORMESSAGE_ALREADY_EXISTS
	#define ERRORMESSAGE_ALREADY_EXISTS "File already exists"
// Cannot create a file when that file already exists
#endif

#ifndef ERRORMESSAGE_NOT_ENOUGH_MEMORY
	#define ERRORMESSAGE_NOT_ENOUGH_MEMORY "Not enough memory"
// Not enough memory resources are available to process this command
#endif

#ifndef ERRORMESSAGE_OUTOFMEMORY
	#define ERRORMESSAGE_OUTOFMEMORY "Out of memory"
// Not enough storage is available to complete this operation
#endif

#ifndef ERRORMESSAGE_INVALID_DATA
	#define ERRORMESSAGE_INVALID_DATA "Invalid data"
// The data is invalid
#endif

#ifndef ERRORMESSAGE_INVALID_PARAMETER
	#define ERRORMESSAGE_INVALID_PARAMETER "Invalid parameter\nUssage: <currentFile> <filePNG> <filePNM>"
// The parameter (count of parameters) is incorrect
#endif

#ifndef ERRORMESSAGE_CALL_NOT_IMPLEMENTED
	#define ERRORMESSAGE_CALL_NOT_IMPLEMENTED "Call not implemented"
// This function is not supported on this system
#endif

#ifndef ERRORMESSAGE_UNKNOWN
	#define ERRORMESSAGE_UNKNOWN "Unknow error"
// Other case
#endif

#endif	  // C_2_ALEXSHEMETOV_ERROR_MESSAGES_H
