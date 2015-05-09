/*
    TODO():

    - Saved Games Location
    - Getting Handle To Executable
    - Asset loading path
    - Threading (launch thread)
    - Raw Input (support for multiple keyboards)
    - sleep/time being period
    - clipcursor
    - FullScreen Support
    - WM_SETCURSOR (control cursor visibility)
    - QueryCancelAuoplay
    - WM_ACTIVATEAPP (not active)
    - Blit speed improvements
    - Hardward accelerationg (openGL , direct3d)
    - GetKeyboardLayout (international WASD)

    partial list:
*/
#include <windows.h>
#include <stdint.h>
#include <stdlib.h>
//#include "C:\Program Files (x86)\Windows Kits\8.1\Include\um\Xinput.h"
#include <C:\Programs\Project\Include\um\Xinput.h>
#include <iostream>
#include "clash.cpp"

#define internal static
#define local_persist static
#define global_variable static


typedef int8_t int8;
typedef int32_t int32;
typedef int64_t int64;
typedef int16_t int16;

typedef uint8_t uint8;
typedef uint32_t uint32;
typedef uint64_t uint64;
typedef uint16_t uint16; 

global_variable bool running;
//global_variable BITMAPINFO BitmapInfo; 
//global_variable void* BitmapMemory;
//global_variable HBITMAP BitmapHandle;
//global_variable HDC BitmapDeviceContext;
//global_variable int BitmapWidth;
//global_variable int BitmapHeight;
//global_variable int BytesPerPixel = 4;
global_variable int xoffset;
global_variable int yoffset;

struct win32_offscreen_buffer
{
    BITMAPINFO Info;
    void *Memory;
    int Width;
    int Height;
    int Pitch;
    int BytesPerPixel;
};

global_variable win32_offscreen_buffer GBackBuffer;

struct win32_dimension
{
    int Width;
    int Height;
};

#define X_INPUT_GET_STATE(name) DWORD WINAPI name(DWORD dwUserIndex, XINPUT_STATE *pState)
typedef X_INPUT_GET_STATE(x_input_get_state);
X_INPUT_GET_STATE(XInputGetStateStub){
    return (ERROR_DEVICE_NOT_CONNECTED);
}

#define X_INPUT_SET_STATE(name) DWORD WINAPI name(DWORD dwUserIndex, XINPUT_VIBRATION *pVibration)
typedef X_INPUT_SET_STATE(x_input_set_state);
X_INPUT_SET_STATE(XInputSetStateStub){
    return (ERROR_DEVICE_NOT_CONNECTED);
}

//sound
//#define DIRECT_SOUND_CREATE(name) HRESULT WINAPI name(LPCGUID pcGuidDevice, LPDIRECTSOUND *ppDS, LPUNKNOWN pUnkOuter)
//typedef DIRECT_SOUND_CREATE(direct_sound_create);

void* PlatformLoadFile(char *FileName){
   return (0);
}

global_variable x_input_get_state *XInputGetState_ = XInputGetStateStub;
global_variable x_input_set_state *XInputSetState_ = XInputSetStateStub;
#define XInputGetState XInputGetState_
#define XInputSetState XInputSetState_ 

internal void LoadXInput(void){
    //test windows 8
    HMODULE XInputLibrary = LoadLibrary("xinput1_4.dll");
    if (!XInputLibrary){
        HMODULE XInputLibrary = LoadLibrary("xinput1_3.dll");
    }

    if (XInputLibrary)
    {
        XInputGetState = (x_input_get_state *)GetProcAddress(XInputLibrary, "XInputGetState");
        if (!XInputGetState) {XInputGetState = XInputGetStateStub;}
        XInputSetState = (x_input_set_state *)GetProcAddress(XInputLibrary, "XInputSetState");
        if (!XInputGetState) {XInputSetState = XInputSetStateStub;}
    }
}

/*
internal void InitDSound(HWND hWnd, int32 BufferSize)
{
    //load library
    HMODULE DSoundLibrary = LoadLibraryA("dsound.dll");

    if (DSoundLibrary)
    {
        direct_sound_create *DirectSoundCreate = (direct_sound_create *);
        GetProcAddress(DSoundLibrary, "DirectSoundCreate");
        LPDIRECTSOUND *DirectSound;
        if (DirectSoundCreate && SUCCEEDED(DirectSoundCreate(0, &DirectSound, 0))){
            WAVEFORMATEX Waveformat = {};
            Waveformat.wFormatTage = WAVE_FORMAT_PCM;
            Waveformat.nChannels = 2;
            Waveformat.wBitsPerSample = 16;
            Waveformat.nBlockAlign = (Waveformat.nChannels*Waveformat.wBitsPerSample) / 8;
            Waveformat.nAvgBytesPerSec = Waveformat.nBlockAlign*Waveformat.nSamplesPerSec;
            Waveformat.nSamplesPerSec = SamplesPerSecond;
            Waveformat.cbSize = 0;

            if (SUCCEEDED(DirectSound->SetCooperativeLevel(hWnd, DSSCL_PRIORITY))){
                BUFFERDESC BufferDescription = {};
                BufferDescription.dwSize = sizeof(BufferDescription);
                BufferDescription.dwFlags = DSBCAPS_PRIMARYBUFFER;

                LPDIRECTSOUNDBUFFER PrimaryBuffer;
                if (SUCCEEDED(DirectSound->CreateSoundBuffer(&BufferDescription, &PrimaryBuffer, 0))){
                    
                    if (SUCCEEDED(PrimaryBuffer->SetFormat(&Waveformat)){
                        //we set the format!
                    } else {
                        //diagnostics
                    }
                } else {
                    //diagnostics
                }
            } else {
                //diagnostic
            }

            BUFFERDESC BufferDescription = {};
            BufferDescription.dwSize = sizeof(BufferDescription);
            BufferDescription.dwFlags = DSBCAPS_PRIMARYBUFFER;
            BufferDescription.dwBufferBytes = BufferSize;
            BufferDescription.dwlpwfxFormat = &BufferDescription;

            LPDIRECTSOUNDBUFFER SecondaryBuffer;
            if (SUCCEEDED(DirectSound->CreateSoundBuffer(&BufferDescription, &SecondaryBuffer, 0))){


            } else {
                //diagnostic
            }


        } else {
            //diagnostic
        }


        //Get a directsound object!

        //create primary buffer

        //create secondary buffer

        //start it playing
    } else {
        //diagnostic
    }



}

*/

win32_dimension GetWindowDimension(HWND hWnd){
    win32_dimension Result;

    RECT ClientRect;
    GetClientRect(hWnd, &ClientRect);
    Result.Width = ClientRect.right - ClientRect.left;
    Result.Height = ClientRect.bottom = ClientRect.top;
}

const char achTitle      [] = "Example #1";
const char achWindowClass[] = "ExampleNumberOne";

internal void RenderGradient(win32_offscreen_buffer Buffer, int XOffset, int YOffset)
{
    int Width = Buffer.Width;
    int Height = Buffer.Height;

    uint8 *Row = (uint8 *)Buffer.Memory;
    for(int y = 0;y<Buffer.Height;y++)
    {
        uint32 *Pixel = (uint32 *)Row;
        for (int x = 0;x<Buffer.Width;x++)
        {
            //structure of the pixel in memory
            uint8 Blue = (x+ XOffset);
            uint8 Green = (y+YOffset);

            *Pixel++ = ((Green << 8) | Blue);
        }
        Row += Buffer.Pitch;
    }
}

internal void ResizeDIBSection(win32_offscreen_buffer *Buffer, int Width,int Height)
{
        //bulletproof this
        //free dibsection
        if (Buffer->Memory){
            VirtualFree(Buffer->Memory, 0, MEM_RELEASE);
        }

        Buffer->Width = Width;
        Buffer->Height = Height;
        Buffer->BytesPerPixel = 4;
        Buffer->Info.bmiHeader.biSize = sizeof(Buffer->Info.bmiHeader);
        Buffer->Info.bmiHeader.biWidth = Buffer->Width;
        Buffer->Info.bmiHeader.biHeight = -Buffer->Height;
        Buffer->Info.bmiHeader.biPlanes = 1;
        Buffer->Info.bmiHeader.biBitCount = 32;
        Buffer->Info.bmiHeader.biCompression = BI_RGB;

        int BitmapMemorySize = (Buffer->Width*Buffer->Height)*Buffer->BytesPerPixel;
        Buffer->Memory = VirtualAlloc(0, BitmapMemorySize, MEM_COMMIT, PAGE_READWRITE);

        Buffer->Pitch = Width*Buffer->BytesPerPixel;

}

internal void SetOffsets(int x, int y){
    x=xoffset;
    y=yoffset;
}

internal void Win32UpdateWindw(win32_offscreen_buffer *Buffer, HDC DeviceContext, RECT WindowRect, int X, int Y, int Width, int Height)
{
    int WindowWidth = WindowRect.right - WindowRect.left;
    int WindowHeight = WindowRect.bottom - WindowRect.top;

    //aspect ratio
       StretchDIBits(DeviceContext, 
                        /*
                        X, Y, Width, Height, 
                        X, Y, Width, Height,
                        */
                        0,0,WindowWidth,WindowHeight,
                        0,0,Buffer->Width, Buffer->Height,
                        Buffer->Memory, 
                        &Buffer->Info, DIB_RGB_COLORS, 
                        SRCCOPY); 


}

LRESULT CALLBACK WndProcedure(HWND hWnd, UINT uMsg, WPARAM wParam, LPARAM lParam);
ATOM MyRegisterClass(HINSTANCE hInstance);
BOOL InitInstance(HINSTANCE hInstance, int nCmdShow,int64 PerfCountFreq);

int WINAPI WinMain(HINSTANCE hInstance, HINSTANCE hPrevInstance, LPSTR lpCmdLine,
                   int nCmdShow)
{

    LARGE_INTEGER PerfCountFreqResult;
    QueryPerformanceFrequency(&PerfCountFreqResult);
    int64 PerfCountFreq = PerfCountFreqResult.QuadPart;

    LoadXInput();
    MyRegisterClass(hInstance);

    if(!InitInstance(hInstance, nCmdShow, PerfCountFreq))
    {
        return FALSE;
    }
    MSG msg = {0};
    
    return msg.wParam;
}

LRESULT CALLBACK WndProcedure(HWND hWnd, UINT Msg, WPARAM wParam, LPARAM lParam)
{
    int yOffs = 0;
    int xOffs = 0;
    switch(Msg)
    {
    case WM_CLOSE:
    {
        running = false;
        PostQuitMessage(0);
        break;
    }
    case WM_SYSKEYDOWN:
    {

    } break;
    case WM_SYSKEYUP:
    {

    } break;
    case WM_KEYUP:
    {
        uint32 VKCode = wParam;
        bool WasDown = ((lParam & (1<<30)) != 0);
        bool IsDown = ((lParam & (1<<29)) == 0);
      //  if (WasDown != IsDown){
            if (VKCode == 'W'){
                
            }
            if (VKCode == 'A'){
                
            }
            if (VKCode == 'S'){
                
            }
            if (VKCode == 'D'){
                
            }
            if (VKCode == 'Q'){
                
            }
            if (VKCode == 'E'){
                
            }
            if (VKCode == VK_SPACE){
                
            }
            if (VKCode == VK_UP){
                
            }
       // }
        //add alt+f4
        bool AltKeyWasDown = ((lParam & (1 << 29)) != 0);
        if ((VKCode == VK_F4)){
            running = false;
        }
    }break;
    case WM_KEYDOWN:
    {
         uint32 VKCode = wParam;
        if (VKCode == 'W'){
            yoffset+=10;
        } 
        if (VKCode == 'S'){
            yoffset+= -10;
        }
        if (VKCode == 'A'){
            xoffset += 10;

        }
        if (VKCode == 'D'){
            xoffset += -10;
        }
    }break;
    case WM_DESTROY:
    	running = false;
        PostQuitMessage(WM_QUIT);
        break;
    case WM_PAINT:
    {
    	PAINTSTRUCT Paint;
    	HDC DeviceContext = BeginPaint(hWnd,&Paint);

    	int X = Paint.rcPaint.left;
    	int Y = Paint.rcPaint.top;
    	int Height = Paint.rcPaint.bottom - Paint.rcPaint.top;
    	int Width = Paint.rcPaint.right - Paint.rcPaint.left;

        RECT ClientRect;
        GetClientRect(hWnd, &ClientRect);

        DeviceContext = GetDC(hWnd);
    	Win32UpdateWindw(&GBackBuffer, DeviceContext,ClientRect,X,Y,Width,Height);
        ReleaseDC(hWnd, DeviceContext);
        //
        //

    	//PatBlt(DeviceContext,X,Y,Width,Height,Operation);

        /*
    	if (Operation == WHITENESS){
    		Operation = BLACKNESS;
    	}
    	 else 
    	 {
    	 	Operation = WHITENESS;
    	 }
         */
    	EndPaint(hWnd,&Paint);
    }
    case WM_SIZE:
    {
    }
    default:
        return DefWindowProc(hWnd, Msg, wParam, lParam);
    }
    return 0;
}

ATOM
MyRegisterClass(HINSTANCE hInstance)
{
    WNDCLASSEX wcex = {0};

    ResizeDIBSection(&GBackBuffer, 1280, 720);
    /*HWND WindowHandle = CreateWindowExA(
                                        0,
                                        wcex.lpszClassName,
                                        "Sample Space",
                                        WS_OVERLAPPEDWINDOW|WS_VISIBLE,
                                        CW_USEDEFAULT,
                                        CW_USEDEFAULT,
                                        CW_USEDEFAULT,
                                        CW_USEDEFAULT,
                                        0,
                                        0,
                                        hInstance,
                                        0);
    {
        if(WindowHandle){
            for(;;)
            {
                MSG Message;
                BOOL MessageResult = GetMessageA(&Message,0,0,0);
                if (MessageResult > 0)
                {
                    TranslateMessage(&Message);
                    DispatchMessage(&Message);
                }
                else
                {
                    break;
                }
            }
        }
        else 
        {

        }
    }
    */

    wcex.cbSize        = sizeof(WNDCLASSEX);
    wcex.style         = CS_HREDRAW | CS_VREDRAW;
    wcex.lpfnWndProc   = WndProcedure;
    wcex.hInstance     = hInstance;
    wcex.hCursor       = LoadCursor(NULL, IDC_ARROW);
    wcex.hbrBackground = (HBRUSH)(COLOR_WINDOW + 1);
    wcex.lpszClassName = achWindowClass;

    return RegisterClassEx(&wcex);
}
BOOL
InitInstance(HINSTANCE hInstance, int nCmdShow, int64 PerfCountFreq)
{
    HWND hWnd = CreateWindow(achWindowClass,
                             achTitle,
                             WS_OVERLAPPEDWINDOW,
                             CW_USEDEFAULT,
                             0,
                             CW_USEDEFAULT,
                             0,
                             NULL,
                             NULL,
                             hInstance,
                             NULL);

    if(NULL == hWnd)
    {
        return FALSE;
    }

    ShowWindow(hWnd, nCmdShow);
    UpdateWindow(hWnd);

    if (hWnd){
        running = true;
        int xoff = xoffset;
        int yoff = yoffset;
        LARGE_INTEGER LastCounter;
        QueryPerformanceCounter(&LastCounter);
//        int64 LastCycleCount = __rdtsc();


        while (running){
           
            MSG msg;

            while (PeekMessage(&msg,0,0,0,PM_REMOVE)){
                if (msg.message == WM_QUIT){
                    running = false;
                }

                TranslateMessage(&msg);
                DispatchMessage(&msg);
            }
            //TODO Pull more frequently?
            for (DWORD ControllerIndex = 0; ControllerIndex < XUSER_MAX_COUNT;++ControllerIndex){
                XINPUT_STATE ControllerState;
                
                if (XInputGetState(ControllerIndex, &ControllerState) == ERROR_SUCCESS)
                {
                    //controller pluged in
                    XINPUT_GAMEPAD *Pad = &ControllerState.Gamepad;

                    bool UP = (Pad->wButtons & XINPUT_GAMEPAD_DPAD_UP);
                    bool Down = (Pad->wButtons & XINPUT_GAMEPAD_DPAD_DOWN);
                    bool Left = (Pad->wButtons & XINPUT_GAMEPAD_DPAD_LEFT);
                    bool Right = (Pad->wButtons & XINPUT_GAMEPAD_DPAD_RIGHT);
                    bool Start = (Pad->wButtons & XINPUT_GAMEPAD_START);
                    bool Back = (Pad->wButtons & XINPUT_GAMEPAD_BACK);
                    bool LeftShoulder = (Pad->wButtons & XINPUT_GAMEPAD_LEFT_SHOULDER);
                    bool RightShoulder = (Pad->wButtons & XINPUT_GAMEPAD_RIGHT_SHOULDER);
                    bool AButton = (Pad->wButtons & XINPUT_GAMEPAD_A);
                    bool BButton = (Pad->wButtons & XINPUT_GAMEPAD_B);
                    bool XButton = (Pad->wButtons & XINPUT_GAMEPAD_X);
                    bool YButton = (Pad->wButtons & XINPUT_GAMEPAD_Y);

                    if (AButton)
                    {
                        yoff+=2;
                    }

                } else {

                    //not pluged in
                }
                
            }
            XINPUT_VIBRATION Vibration;
            Vibration.wLeftMotorSpeed = 60000;
            Vibration.wRightMotorSpeed = 60000;
            XInputSetState(0,&Vibration);


            game_offscreen_buffer Buffer = {};
            Buffer.Memory = GBackBuffer.Memory;
            Buffer.Width = GBackBuffer.Width;
            Buffer.Height = GBackBuffer.Height;
            Buffer.Pitch = GBackBuffer.Pitch;
            RenderGradient(Buffer,xoffset,yoffset);

            
            HDC DeviceContext2 = GetDC(hWnd);
            RECT ClientRect;
            GetClientRect(hWnd,&ClientRect);
            int WindowWidth = ClientRect.right - ClientRect.left;
            int WindowHeight = ClientRect.bottom - ClientRect.top;
            Win32UpdateWindw(&GBackBuffer, DeviceContext2,ClientRect,0,0,WindowWidth,WindowHeight);
            ReleaseDC(hWnd, DeviceContext2);

            //int64 EndCycleCount = __rdtsc();

            LARGE_INTEGER EndCounter;
            QueryPerformanceCounter(&EndCounter);

            //display value here
       //     int64 CyclesElapsed = EndCycleCount - LastCycleCount;
            
            int64 CounterElapsed = EndCounter.QuadPart - LastCounter.QuadPart;
            int32 MSPerFrame = (int32)(1000*CounterElapsed / PerfCountFreq);
            int32 FramesPerSecond = 1000/MSPerFrame;
           // int32 MCPF = (int32)(CyclesElapsed / (1000*1000));
            //char Buffer[256];
            //wsprintf(Buffer, "FPS = %d\n", FramesPerSecond);
            //std::cout << Buffer;
            LastCounter = EndCounter;
         //   LastCycleCount = EndCycleCount;
        }
    }
    return TRUE;
}