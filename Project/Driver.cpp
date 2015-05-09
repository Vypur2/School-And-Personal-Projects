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
#define _WIN32_WINNT 0x0501
#include <windows.h>
#include <string>
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

typedef float real32;
typedef double real64; 

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
global_variable game_state * GameState;

global_variable LARGE_INTEGER PerfCountFreqResult;
global_variable int RefreshRate = 60;
global_variable real32 SecondsPerFrame = 1.0f / (real32)RefreshRate;

global_variable bool IsGranular;


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


global_variable x_input_get_state *XInputGetState_ = XInputGetStateStub;
global_variable x_input_set_state *XInputSetState_ = XInputSetStateStub;
#define XInputGetState XInputGetState_
#define XInputSetState XInputSetState_ 

internal void ProcessInput(DWORD XInputButtonState, game_button_state *OldState, game_button_state *NewState, DWORD ButtonBit)
{
    NewState->EndedDown = ((XInputButtonState & ButtonBit)) == ButtonBit;
    NewState->HalfTransitionCount  =  (OldState->EndedDown  != NewState->EndedDown) ? 1:0;
}

real32 GetSecondsElapsed (LARGE_INTEGER Start, LARGE_INTEGER End)
{
    real32 Result = ((real32)(End.QuadPart - Start.QuadPart)/(real32)PerfCountFreqResult.QuadPart);
    return Result;
}

LARGE_INTEGER GetWallClock(void)
{
    LARGE_INTEGER Result;
    QueryPerformanceCounter(&Result);
    return Result;
}

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
    }}

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
void ProcessKeyboardInput(game_button_state *KeyboardState, bool Down)
{
    if (KeyboardState->EndedDown != Down){
        if (Down == true){
            KeyboardState->EndedDown = true;
        }
        ++KeyboardState->HalfTransitionCount;
    }
}
void processPendingMessages (game_controller_input *KeyboardController)
{
    MSG msg;
    while (PeekMessage(&msg,0,0,0,PM_REMOVE)){
        switch(msg.message)
        {
            case WM_SYSKEYDOWN:
            {

            } break;
            case WM_SYSKEYUP:
            {

            } break;
            case WM_KEYUP:
            {
                uint32 VKCode = (uint32)msg.wParam;
                bool WasDown = ((msg.lParam & (1<<30)) != 0);
                bool IsDown = ((msg.lParam & (1<<29)) == 0);
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
                bool AltKeyWasDown = ((msg.lParam & (1 << 29)) != 0);
                if ((VKCode == VK_F4)){
                    running = false;
                }
            }break;
            case WM_KEYDOWN:
            {
                uint32 VKCode = (uint32)msg.wParam;
                bool WasDown = ((msg.lParam & (1<<30)) != 0);
                bool IsDown = ((msg.lParam & (1<<29)) == 0);
              //  if (IsDown != WasDown){
                    if (VKCode == 'W'){

                        IsDown = true;

                        ProcessKeyboardInput(&KeyboardController->Up,IsDown);
                    } 
                    if (VKCode == 'S'){
                        IsDown = true;
                        ProcessKeyboardInput(&KeyboardController->Down,IsDown);
                    }
                    if (VKCode == 'A'){
                        IsDown = true;
                        ProcessKeyboardInput(&KeyboardController->Left,IsDown);

                    }
                    if (VKCode == 'D'){
                        IsDown = true;
                        ProcessKeyboardInput(&KeyboardController->Right,IsDown);
                    } 
                    if (VKCode == ' '){
                        IsDown = true;
                        ProcessKeyboardInput(&KeyboardController->Jump,IsDown);
                    }
                //}
            }break;
            default:
            {
                TranslateMessage(&msg);
                DispatchMessage(&msg);
            }
        }
    }
 }
struct read_results
{
    uint32 ContentSize;
    void* Contents;
};
read_results ReadEntireFile(const char* Filename)
{
    read_results Results = {};
    LARGE_INTEGER FileSize;
    void* Result = 0;
    HANDLE FileHandle = CreateFile(Filename, GENERIC_READ, FILE_SHARE_READ, 0 , OPEN_EXISTING,0,0);
    if (FileHandle != INVALID_HANDLE_VALUE)
    {
        if(GetFileSizeEx(FileHandle,&FileSize))
        {
            Result = VirtualAlloc(0, FileSize.QuadPart, MEM_RESERVE|MEM_COMMIT, PAGE_READWRITE);

            if (Result){
                DWORD BytesRead;
                if (ReadFile(FileHandle, Result, FileSize.QuadPart, &BytesRead,0)){
                    //success
                } else 
                {
                    //logging
                    FreeMemory(Result);
                    Result = 0;
                }
            } else {
                //logging
            }
        } else {
            //logging
        }
        CloseHandle(FileHandle);
    }
    Results.ContentSize = FileSize.QuadPart;
    Results.Contents = Result;
}

bool WriteEntireFile(const char* FileName,uint32 MemorySize, void *Memory){
    uint32 result = 0;
    HANDLE FileHandle = CreateFileA(FileName,GENERIC_WRITE,0,0,CREATE_ALWAYS,0,0);
    if (FileHandle != INVALID_HANDLE_VALUE){
        DWORD BytesWritten;
    
        if (WriteFile(FileHandle, Memory, MemorySize,&BytesWritten,0))
        {
            result = (BytesWritten == MemorySize);
        } else 
        {

        }

    CloseHandle(FileHandle);
    }
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
                        0,0,Buffer->Width, Buffer->Height,
                        0,0,Buffer->Width, Buffer->Height,
                        Buffer->Memory, 
                        &Buffer->Info, DIB_RGB_COLORS, 
                        SRCCOPY); 


}

LRESULT CALLBACK WndProcedure(HWND hWnd, UINT uMsg, WPARAM wParam, LPARAM lParam);
ATOM MyRegisterClass(HINSTANCE hInstance);
BOOL InitInstance(HINSTANCE hInstance, int nCmdShow);

int WINAPI WinMain(HINSTANCE hInstance, HINSTANCE hPrevInstance, LPSTR lpCmdLine,
                   int nCmdShow)
{
    UINT DesiredMS = 1;
    IsGranular = ((timeBeginPeriod(DesiredMS)) == TIMERR_NOERROR);
    GameState = GameStartup();
    QueryPerformanceFrequency(&PerfCountFreqResult);
    int64 PerfCountFreq = PerfCountFreqResult.QuadPart;
    //sets windows scheduler

    LoadXInput();
    MyRegisterClass(hInstance);

    if(!InitInstance(hInstance, nCmdShow))
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
InitInstance(HINSTANCE hInstance, int nCmdShow)
{
    HWND hWnd = CreateWindow(achWindowClass,
                             achTitle,
                             WS_OVERLAPPEDWINDOW,
                             CW_USEDEFAULT,
                             0,
                             1280,
                             720,
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

    POINT MouseP;
    GameState->PlayerX = 100;
    GameState->PlayerY = 100;

    if (hWnd){
        running = true;
        LARGE_INTEGER LastCounter = GetWallClock();


        int xoff = xoffset;
        int yoff = yoffset;
//        int64 LastCycleCount = __rdtsc();

        game_input Input = {};
        game_input NewInput = {};
        game_input OldInput = {};


        while (running){
            game_controller_input KeyboardController;
            game_controller_input Zero = {};
            //game_controller_input ZeroController = {};
                GetCursorPos(&MouseP);
                ScreenToClient(hWnd, &MouseP);
                NewInput.MouseX = MouseP.x;
                NewInput.MouseY = MouseP.y;
                NewInput.MouseZ = 0;

            KeyboardController = Zero;
            game_controller_input OldKeyboardController = OldInput.Controllers[0];
            game_controller_input NewKeyboardController = NewInput.Controllers[0];
            NewKeyboardController = Zero;
            /*

            NOTE(mike): this resets the new controller ended down values to the old ones, not needed since we process the next frame
            with the newkeyboard data, only useful for half transition count. 
            for(int ButtonIndex = 0; ButtonIndex < ArrayCount(KeyboardController.Buttons); ButtonIndex++)
            {
               NewKeyboardController.Buttons[ButtonIndex].EndedDown = OldKeyboardController.Buttons[ButtonIndex].EndedDown; 
            }
            */
            processPendingMessages(&NewKeyboardController);

            
            //TODO Pull more frequently?
            DWORD maxControllerCount = XUSER_MAX_COUNT;
            if (maxControllerCount > ArrayCount(Input.Controllers)){
                maxControllerCount = ArrayCount(Input.Controllers);
            }
            for (DWORD ControllerIndex = 0; ControllerIndex < XUSER_MAX_COUNT;++ControllerIndex){
                XINPUT_STATE ControllerState;

                game_controller_input *OldController = &OldInput.Controllers[ControllerIndex];
                game_controller_input *NewController = &NewInput.Controllers[ControllerIndex]; 


              //  if (XInputGetState(ControllerIndex, &ControllerState) == ERROR_SUCCESS)
                //{
                   // std::cout << "succeeded";
                    //controller pluged in
                    XINPUT_GAMEPAD *Pad = &ControllerState.Gamepad;

                    bool UP = (Pad->wButtons & XINPUT_GAMEPAD_DPAD_UP);
                    bool Down = (Pad->wButtons & XINPUT_GAMEPAD_DPAD_DOWN);
                    bool Left = (Pad->wButtons & XINPUT_GAMEPAD_DPAD_LEFT);
                    bool Right = (Pad->wButtons & XINPUT_GAMEPAD_DPAD_RIGHT);

                    if (UP){
                        std::cout << "Up = true";
                    } else {
                        ;
                    }
                    int16 StickX = Pad->sThumbLX;
                    int16 StickY = Pad->sThumbLY;

                    //DEADZONE PROCESSING HERE

                    ProcessInput(Pad->wButtons,&OldController->Down,&NewController->Down,XINPUT_GAMEPAD_A);
                    ProcessInput(Pad->wButtons,&OldController->Right,&NewController->Right,XINPUT_GAMEPAD_B);
                    ProcessInput(Pad->wButtons,&OldController->Left,&NewController->Left,XINPUT_GAMEPAD_X);
                    ProcessInput(Pad->wButtons,&OldController->Up,&NewController->Up,XINPUT_GAMEPAD_Y);

                    //bool Start = (Pad->wButtons & XINPUT_GAMEPAD_START);
                    //bool Back = (Pad->wButtons & XINPUT_GAMEPAD_BACK);
                    //bool LeftShoulder = (Pad->wButtons & XINPUT_GAMEPAD_LEFT_SHOULDER);
                    //bool RightShoulder = (Pad->wButtons & XINPUT_GAMEPAD_RIGHT_SHOULDER);
                   // bool AButton = (Pad->wButtons & XINPUT_GAMEPAD_A);
                    //bool BButton = (Pad->wButtons & XINPUT_GAMEPAD_B);
                    //bool XButton = (Pad->wButtons & XINPUT_GAMEPAD_X);
                    //bool YButton = (Pad->wButtons & XINPUT_GAMEPAD_Y);

               // } else {

                    //not pluged in
                //}
                
            }
            
            /*
            XINPUT_VIBRATION Vibration;
            Vibration.wLeftMotorSpeed = 60000;
            Vibration.wRightMotorSpeed = 60000;
            XInputSetState(0,&Vibration);
            */


            game_offscreen_buffer Buffer = {};
            Buffer.Memory = GBackBuffer.Memory;
            Buffer.Width = GBackBuffer.Width;
            Buffer.Height = GBackBuffer.Height;
            Buffer.Pitch = GBackBuffer.Pitch;

           /* 
            bool DoOnce;
            if (DoOnce == false){
                const char* Name = __FILE__;
                read_results Mem = ReadEntireFile(Name);
                
                WriteEntireFile("c:/programs/project/test.out",Mem.ContentSize,Mem.Contents);
                FreeMemory(Mem.Contents);
                std::cout << "wrote file";
                DoOnce = true;
            }

            */
            NewInput.Controllers[0] = NewKeyboardController;
            GameUpdateAndRender(GameState,Buffer,&NewInput);
           

            //int64 EndCycleCount = __rdtsc();

            LARGE_INTEGER EndCounter = GetWallClock();

            //display value here
       //     int64 CyclesElapsed = EndCycleCount - LastCycleCount;
            
            game_input temp; 
            temp = NewInput;
            NewInput = OldInput;
            OldInput = temp;

            int64 CounterElapsed = EndCounter.QuadPart - LastCounter.QuadPart;
            LARGE_INTEGER WorkCounter = GetWallClock();
            real32 SecondsElapsed = GetSecondsElapsed(LastCounter,WorkCounter);
            if (SecondsElapsed < SecondsPerFrame){
                while (SecondsElapsed < SecondsPerFrame)
                {
                    if (IsGranular){
                        DWORD SleepMS = (DWORD)(1000.0f*(SecondsPerFrame - SecondsElapsed));
                        Sleep(SleepMS);
                    }
                    LARGE_INTEGER CheckCounter;
                    SecondsElapsed = GetSecondsElapsed(LastCounter,GetWallClock());
                }

            } else 
            {
                //missing frame rate 
            }

            HDC DeviceContext2 = GetDC(hWnd);
            RECT ClientRect;
            GetClientRect(hWnd,&ClientRect);
            int WindowWidth = ClientRect.right - ClientRect.left;
            int WindowHeight = ClientRect.bottom - ClientRect.top;
            Win32UpdateWindw(&GBackBuffer, DeviceContext2,ClientRect,0,0,WindowWidth,WindowHeight);
            ReleaseDC(hWnd, DeviceContext2);

            LARGE_INTEGER FinalCounter = GetWallClock();

            real64 MSPerFrame = GetSecondsElapsed(EndCounter, FinalCounter);
            real64 FramesPerSecond = (1 / (MSPerFrame));

           // std::cout << MSPerFrame << " ms : " << FramesPerSecond << " FPS \n";
            // int32 MCPF = (int32)(CyclesElapsed / (1000*1000));
            //char Buffer2[256];
            //wsprintf(Buffer2, "ms %d\n", MSPerFrame);
            //std::cout << Buffer2;
            //LastCycleCount = EndCycleCount;
            QueryPerformanceCounter(&EndCounter);
            EndCounter = GetWallClock();
            LastCounter = EndCounter;
            //swap();

        }
    }
    return TRUE;
}