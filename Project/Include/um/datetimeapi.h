 
// begin_1_0
// begin_1_1
/********************************************************************************
*                                                                               *
* datetimeapi.h -- ApiSet Contract for api-ms-win-core-datetime-l1              *  
*                                                                               *
* Copyright (c) Microsoft Corporation. All rights reserved.                     *
*                                                                               *
********************************************************************************/

#ifdef _MSC_VER
#pragma once
#endif // _MSC_VER

#ifndef _DATETIMEAPI_H_
#define _DATETIMEAPI_H_

#include <apiset.h>
#include <apisetcconv.h>
#include <minwindef.h>
#include <minwinbase.h>

/* APISET_NAME: api-ms-win-core-datetime-l1 */

#ifndef _APISET_DATETIME_VER
#ifdef _APISET_MINWIN_VERSION
#if _APISET_MINWIN_VERSION >= 0x0101
#define _APISET_DATETIME_VER 0x0101
#elif _APISET_MINWIN_VERSION == 0x0100
#define _APISET_DATETIME_VER 0x0100
#endif
#endif
#endif

#ifdef __cplusplus
extern "C" {
#endif

// end_1_0
// end_1_1

// begin_1_0

#pragma region Desktop Family

#if WINAPI_FAMILY_PARTITION(WINAPI_PARTITION_DESKTOP)

// For Windows Vista and above GetDateFormatEx is preferred
WINBASEAPI
int
WINAPI
GetDateFormatA(
    _In_ LCID Locale,
    _In_ DWORD dwFlags,
    _In_opt_ CONST SYSTEMTIME * lpDate,
    _In_opt_ LPCSTR lpFormat,
    _Out_writes_opt_(cchDate) LPSTR lpDateStr,
    _In_ int cchDate
    );

// For Windows Vista and above GetDateFormatEx is preferred
WINBASEAPI
int
WINAPI
GetDateFormatW(
    _In_ LCID Locale,
    _In_ DWORD dwFlags,
    _In_opt_ CONST SYSTEMTIME * lpDate,
    _In_opt_ LPCWSTR lpFormat,
    _Out_writes_opt_(cchDate) LPWSTR lpDateStr,
    _In_ int cchDate
    );

#ifdef UNICODE
#define GetDateFormat  GetDateFormatW
#else
#define GetDateFormat  GetDateFormatA
#endif // !UNICODE


// For Windows Vista and above GetTimeFormatEx is preferred
WINBASEAPI
int
WINAPI
GetTimeFormatA(
    _In_ LCID Locale,
    _In_ DWORD dwFlags,
    _In_opt_ CONST SYSTEMTIME * lpTime,
    _In_opt_ LPCSTR lpFormat,
    _Out_writes_opt_(cchTime) LPSTR lpTimeStr,
    _In_ int cchTime
    );

// For Windows Vista and above GetTimeFormatEx is preferred
WINBASEAPI
int
WINAPI
GetTimeFormatW(
    _In_ LCID Locale,
    _In_ DWORD dwFlags,
    _In_opt_ CONST SYSTEMTIME * lpTime,
    _In_opt_ LPCWSTR lpFormat,
    _Out_writes_opt_(cchTime) LPWSTR lpTimeStr,
    _In_ int cchTime
    );

#ifdef UNICODE
#define GetTimeFormat  GetTimeFormatW
#else
#define GetTimeFormat  GetTimeFormatA
#endif // !UNICODE

#endif /* WINAPI_FAMILY_PARTITION(WINAPI_PARTITION_DESKTOP) */
#pragma endregion

// end_1_0

// begin_1_1


#if !defined(_CONTRACT_GEN) || (_APISET_DATETIME_VER > 0x0100)

#pragma region Application Family

#if WINAPI_FAMILY_PARTITION(WINAPI_PARTITION_APP)

WINBASEAPI
int
WINAPI
GetTimeFormatEx(
    _In_opt_ LPCWSTR lpLocaleName,
    _In_ DWORD dwFlags,
    _In_opt_ CONST SYSTEMTIME * lpTime,
    _In_opt_ LPCWSTR lpFormat,
    _Out_writes_opt_(cchTime) LPWSTR lpTimeStr,
    _In_ int cchTime
    );


WINBASEAPI
int
WINAPI
GetDateFormatEx(
    _In_opt_ LPCWSTR lpLocaleName,
    _In_ DWORD dwFlags,
    _In_opt_ CONST SYSTEMTIME * lpDate,
    _In_opt_ LPCWSTR lpFormat,
    _Out_writes_opt_(cchDate) LPWSTR lpDateStr,
    _In_ int cchDate,
    _In_opt_ LPCWSTR lpCalendar
    );


#endif /* WINAPI_FAMILY_PARTITION(WINAPI_PARTITION_APP) */
#pragma endregion

#endif // !defined(_CONTRACT_GEN) || (_APISET_DATETIME_VER > 0x0100)

// end_1_1

// begin_1_0
// begin_1_1

#ifdef __cplusplus
}
#endif


#endif // DATETIMEAPI

// end_1_0
// end_1_1

