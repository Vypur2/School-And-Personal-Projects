
#include <windows.h>
#include <stdint.h>
//#include "C:\Program Files (x86)\Windows Kits\8.1\Include\um\Xinput.h"
#include <C:\Programs\Project\Include\um\Xinput.h>
#include <iostream>

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
    return 0;
}

#define X_INPUT_SET_STATE(name) DWORD WINAPI name(DWORD dwUserIndex, XINPUT_VIBRATION *pVibration)
typedef X_INPUT_SET_STATE(x_input_set_state);
X_INPUT_SET_STATE(XInputSetStateStub){
    return 0;
}



global_variable x_input_get_state *XInputGetState_ = XInputGetStateStub;
global_variable x_input_set_state *XInputSetState_ = XInputSetStateStub;
#define XInputGetState XInputGetState_
#define XInputSetState XInputSetState_ 

internal void LoadXInput(void){
    HMODULE XInputLibrary = LoadLibrary("xinput1_3.dll");
    if (XInputLibrary)
    {
        XInputGetState = (x_input_get_state *)GetProcAddress(XInputLibrary, "XInputGetState");
        XInputSetState = (x_input_set_state *)GetProcAddress(XInputLibrary, "XInputSetState");
    }
}

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
BOOL InitInstance(HINSTANCE hInstance, int nCmdShow);

int WINAPI WinMain(HINSTANCE hInstance, HINSTANCE hPrevInstance, LPSTR lpCmdLine,
                   int nCmdShow)
{
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
    switch(Msg)
    {
    case WM_CLOSE:
    {
        running = false;
        PostQuitMessage(0);
        break;
    }
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
InitInstance(HINSTANCE hInstance, int nCmdShow)
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
        MSG msg;
        int xoff = 0;
        int yoff = 0;
        while (running){
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
                    std::cout << "asdf";
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
                    bool AButton = (Pad->wButtons == XINPUT_GAMEPAD_A);
                    bool BButton = (Pad->wButtons & XINPUT_GAMEPAD_B);
                    bool XButton = (Pad->wButtons & XINPUT_GAMEPAD_X);
                    bool YButton = (Pad->wButtons & XINPUT_GAMEPAD_Y);

                    if (AButton)
                    {
                        std::cout << "pressed";
                       yoff+=4;
                    }
                } else {

                    //not pluged in
                }
                
            }

            RenderGradient(GBackBuffer, xoff,yoff);
            HDC DeviceContext2 = GetDC(hWnd);
            RECT ClientRect;
            GetClientRect(hWnd,&ClientRect);
            int WindowWidth = ClientRect.right - ClientRect.left;
            int WindowHeight = ClientRect.bottom - ClientRect.top;
            Win32UpdateWindw(&GBackBuffer, DeviceContext2,ClientRect,0,0,WindowWidth,WindowHeight);
            ReleaseDC(hWnd, DeviceContext2);
            xoff++;
            yoff++;
        }
    }
    return TRUE;
}


/*

#include <windows.h>

class Simple{
public:
		LRESULT CALLBACK 
		CallWindowProc(
	    	WNDPROC lpPrevWndFunc,
	    	HWND Window,
	    	UINT Messge,
	    	WPARAM WParam,
	    	LPARAM LParam
	){
	
		LRESULT Result = 0;

		
		switch(Message){
			case WM_SIZE:
			{
				printf("%s\n", WM_SIZE);
			} break;
			case WM_DESTROY:
			{
				printf("%s\n", WM_DESTROY);
			} break;
			case WM_CLOSE:
			{
				printf("%s\n", WM_CLOSe);
			} break;
			case WM_ACTIVATEAPP:
			{
				printf("%s\n", WM_ACTIVEATEAPP);
			} break;
			default:
			{
				Result = ;
				//printf("%s\n",default);
			}
			break;
		}

		return(Result);
	}
};



int CALLBACK WinMain( 
				HINSTANCE Instance,
				HINSTANCE PrevInstance,
				LPSTR CommandLine, 
				int ShowCode)
{
	Simple *simple = new Simple();
	WNDCLASS WindowClass = {}; 
	
	WindowClass.style = CS_OWNDC | CS_HREDRAW | CS_VREDRAW;
	WindowClass.lpfnWndProc = simple->CallWindowProc;
	WindowClass.hInstance  = Instance;
	//WindowClass.hIcon;
  	WindowClass.lpszClassName - "ProjectWindowClass";


	return 0;
}

*/